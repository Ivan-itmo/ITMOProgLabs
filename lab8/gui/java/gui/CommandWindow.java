package gui;

import clientutility.ClientConnection;
import clientutility.Client;
import clientutility.ClientPassword;
import commands.ExecuteScript;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.Coordinates;
import models.*;
import utility.CommandData;
import utility.CommandManager;
import utility.ConsoleManager;

import java.io.PrintStream;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class CommandWindow {

    private final ObservableList<LabWork> labWorkList = FXCollections.observableArrayList();
    private final Canvas canvas = new Canvas(600, 600);
    private final Set<Integer> animatedLabWorkIds = new HashSet<>();
    private final Map<Integer, Double> animatedLabWorkRadii = new HashMap<>();
    private final Map<Integer, Color> animatedLabWorkColors = new HashMap<>();
    private final double scale = 5.0;

    private ResourceBundle bundle;
    private ComboBox<Locale> languageSelector;

    private Label instructionLabel;
    private TextArea inputArea;
    private Button executeButton;
    private TextArea outputArea;
    private Label languageLabel;

    private Client client;
    private ClientPassword clientPassword;
    private ClientConnection clientConnection;
    private String login;
    private String password;

    public CommandWindow(ClientConnection clientConnection, Client client, ClientPassword clientPassword,
                         CommandManager commandManager, ConsoleManager consoleManager,
                         String login, String password) {

        this.client = client;
        this.clientPassword = clientPassword;
        this.clientConnection = clientConnection;
        this.login = login;
        this.password = password;

        Stage stage = new Stage();
        bundle = ResourceBundle.getBundle("CommandWindow", new Locale("en", "UK"));
        stage.setTitle(bundle.getString("title"));

        TableView<LabWork> tableView = new TableView<>(labWorkList);
        tableView.setPrefHeight(250);
        tableView.getColumns().addAll(LabWorkTableFactory.getColumns());

        instructionLabel = new Label();
        inputArea = new TextArea();
        outputArea = new TextArea();
        executeButton = new Button();

        outputArea.setEditable(false);
        inputArea.setPrefRowCount(4);

        languageSelector = new ComboBox<>();
        languageSelector.getItems().addAll(
                new Locale("en", "UK"),
                new Locale("ru", "RU"),
                new Locale("fi", "FI"),
                new Locale("lt", "LT")
        );
        languageSelector.setValue(new Locale("en", "UK"));
        languageLabel = new Label();
        languageSelector.setOnAction(e -> {
            Locale selected = languageSelector.getValue();
            bundle = ResourceBundle.getBundle("CommandWindow", selected);
            updateTexts();
        });

        updateTexts();

        AtomicReference<Map<LabWork, String>> lastCollection = new AtomicReference<>(new HashMap<>());

        tableView.setRowFactory(tv -> {
            TableRow<LabWork> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 2) {
                    LabWork selected = row.getItem();
                    Color objectColor = animatedLabWorkColors.getOrDefault(selected.getId(), Color.GRAY);
                    Color myColor = Color.web(clientPassword.getColor());

                    if (objectColor.equals(myColor)) {
                        openEditDialog(selected, consoleManager, lastCollection);
                    } else {
                        outputArea.appendText(bundle.getString("not_your_object") + "\n");
                    }
                }
            });
            return row;
        });

        executeButton.setOnAction(e -> {
            String inputText = inputArea.getText().trim();
            if (inputText.isEmpty()) return;

            List<String> lines = Arrays.stream(inputText.split("\\R"))
                    .map(String::stripTrailing)
                    .toList();

            String commandName = lines.get(0);
            List<Object> args = lines.size() > 1 ? List.copyOf(lines.subList(1, lines.size())) : List.of();

            if ("exit".equalsIgnoreCase(commandName)) {
                Platform.exit();
                System.exit(0);
                return;
            }

            try {
                if ("execute_script".equalsIgnoreCase(commandName)) {
                    consoleManager.setArgsMode(args);
                    ExecuteScript executeScript = new ExecuteScript(commandManager, consoleManager);
                    List<List<Object>> allCommands = new ArrayList<>();
                    allCommands.addAll(executeScript.execute());
                    allCommands.addAll(consoleManager.getArgsCom());

                    for (List<Object> cmdArgs : allCommands) {
                        String cmd = cmdArgs.get(0).toString();
                        List<Object> argList = cmdArgs.size() > 1 ? cmdArgs.subList(1, cmdArgs.size()) : List.of();
                        CommandData data = new CommandData(cmd, clientPassword.getColor(), login, password, argList);
                        client.clientWork(data);
                        lastCollection.set(clientConnection.getLastCollection());
                    }
                } else {
                    consoleManager.setArgsMode(args);
                    List<Object> parsed = consoleManager.inputCommand(commandName);
                    commandName = parsed.get(0).toString();
                    List<Object> argsCom = parsed.size() > 1 ? parsed.subList(1, parsed.size()) : List.of();
                    CommandData data = new CommandData(commandName, clientPassword.getColor(), login, password, argsCom);
                    consoleManager.setIndex(0);
                    client.clientWork(data);
                    lastCollection.set(clientConnection.getLastCollection());
                }
                Platform.runLater(() -> {
                    outputArea.appendText(bundle.getString("command_sent") + "\n");
                    updateLabWorkTableAndCanvas(lastCollection.get());
                });
            } catch (Exception ex) {
                Platform.runLater(() -> outputArea.appendText(bundle.getString("error") + ": " + ex.getMessage() + "\n"));
            }
            inputArea.clear();
        });

        TextArea logArea = new TextArea();
        logArea.setEditable(false);
        logArea.setPromptText(bundle.getString("system_output"));
        System.setOut(new PrintStream(new TextAreaOutputStream(logArea), true));
        System.setErr(new PrintStream(new TextAreaOutputStream(logArea), true));

        VBox inputBox = new VBox(10, instructionLabel, inputArea, executeButton, outputArea);
        VBox.setVgrow(inputArea, Priority.ALWAYS);
        VBox.setVgrow(outputArea, Priority.ALWAYS);

        HBox topBar = new HBox(10, languageLabel, languageSelector);
        topBar.setPadding(new Insets(10));

        VBox leftPane = new VBox(10, topBar, tableView, inputBox);
        leftPane.setPadding(new Insets(10));
        VBox.setVgrow(inputBox, Priority.ALWAYS);

        VBox rightPane = new VBox(10, canvas, logArea);
        rightPane.setPadding(new Insets(10));
        VBox.setVgrow(logArea, Priority.ALWAYS);

        SplitPane splitPane = new SplitPane(leftPane, rightPane);
        splitPane.setDividerPositions(0.6);

        stage.setScene(new Scene(splitPane, 1200, 800));
        stage.show();

        Map<LabWork, String> initialCollection = clientConnection.getLastCollection();
        if (initialCollection != null && !initialCollection.isEmpty()) {
            updateLabWorkTableAndCanvas(initialCollection);
        }

        Timeline autoUpdate = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> {
            Map<LabWork, String> currentCollection = clientConnection.getLastCollection();
            if (currentCollection != null) {
                Platform.runLater(() -> updateLabWorkTableAndCanvas(currentCollection));
            }
        }));
        autoUpdate.setCycleCount(Timeline.INDEFINITE);
        autoUpdate.play();

        /*canvas.setOnMouseClicked(event -> {
            double clickX = event.getX();
            double clickY = event.getY();

            for (LabWork lw : labWorkList) {
                double x = canvas.getWidth() / 2 + lw.getCoordinates().getX() * scale;
                double y = canvas.getHeight() / 2 - lw.getCoordinates().getY() * scale;
                double r = Math.max(3, lw.getMinimalPoint());

                if (Math.hypot(clickX - x, clickY - y) <= r / 2) {
                    String colorStr = clientPassword.getColor();
                    String objectColorStr = clientConnection.getLastCollection().get(lw);
                    if (colorStr.equals(objectColorStr)) {
                        openEditDialog(lw);
                    } else {
                        outputArea.appendText("This is not your object!\n");
                    }
                    break;
                }
            }
        });*/
    }

    private void openEditDialog(LabWork lw, ConsoleManager consoleManager, AtomicReference<Map<LabWork, String>> lastCollection) {
        Dialog<LabWork> dialog = new Dialog<>();
        dialog.setTitle("Edit LabWork");
        
        TextField nameField = new TextField(lw.getName());
        TextField coordXField = new TextField(String.valueOf(lw.getCoordinates().getX()));
        TextField coordYField = new TextField(String.valueOf(lw.getCoordinates().getY()));
        TextField minimalPointField = new TextField(String.valueOf(lw.getMinimalPoint()));
        ComboBox<Difficulty> difficultyBox = new ComboBox<>(FXCollections.observableArrayList(Difficulty.values()));
        difficultyBox.setValue(lw.getDifficulty());

        Person author = lw.getAuthor();
        TextField authorNameField = new TextField(author.getName());
        TextField authorHeightField = new TextField(String.valueOf(author.getHeight()));
        ComboBox<models.Color> eyeColorBox = new ComboBox<>(FXCollections.observableArrayList(models.Color.values()));
        eyeColorBox.setValue(author.getEyeColor());
        ComboBox<models.Color> hairColorBox = new ComboBox<>(FXCollections.observableArrayList(models.Color.values()));
        hairColorBox.setValue(author.getHairColor());
        ComboBox<Country> nationalityBox = new ComboBox<>(FXCollections.observableArrayList(Country.values()));
        nationalityBox.setValue(author.getNationality());

        Location location = author.getLocation();
        TextField locXField = new TextField(String.valueOf(location.getX()));
        TextField locYField = new TextField(String.valueOf(location.getY()));
        TextField locZField = new TextField(String.valueOf(location.getZ()));

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        int row = 0;
        grid.add(new Label("Name:"), 0, row); grid.add(nameField, 1, row++);
        grid.add(new Label("Coordinates X:"), 0, row); grid.add(coordXField, 1, row++);
        grid.add(new Label("Coordinates Y:"), 0, row); grid.add(coordYField, 1, row++);
        grid.add(new Label("Minimal Point:"), 0, row); grid.add(minimalPointField, 1, row++);
        grid.add(new Label("Difficulty:"), 0, row); grid.add(difficultyBox, 1, row++);

        grid.add(new Label("Author Name:"), 0, row); grid.add(authorNameField, 1, row++);
        grid.add(new Label("Author Height:"), 0, row); grid.add(authorHeightField, 1, row++);
        grid.add(new Label("Eye Color:"), 0, row); grid.add(eyeColorBox, 1, row++);
        grid.add(new Label("Hair Color:"), 0, row); grid.add(hairColorBox, 1, row++);
        grid.add(new Label("Nationality:"), 0, row); grid.add(nationalityBox, 1, row++);

        grid.add(new Label("Location X:"), 0, row); grid.add(locXField, 1, row++);
        grid.add(new Label("Location Y:"), 0, row); grid.add(locYField, 1, row++);
        grid.add(new Label("Location Z:"), 0, row); grid.add(locZField, 1, row++);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                try {
                    lw.setName(nameField.getText());

                    Coordinates newCoords = new Coordinates(
                            Long.parseLong(coordXField.getText()),
                            Long.parseLong(coordYField.getText()));
                    lw.setCoordinates(newCoords);

                    lw.setMinimalPoint(Long.parseLong(minimalPointField.getText()));

                    lw.setDifficulty(difficultyBox.getValue());

                    author.setName(authorNameField.getText());
                    author.setHeight(Float.parseFloat(authorHeightField.getText()));
                    author.setEyeColor(eyeColorBox.getValue());
                    author.setHairColor(hairColorBox.getValue());
                    author.setNationality(nationalityBox.getValue());

                    Location newLocation = new Location(
                            Double.parseDouble(locXField.getText()),
                            Double.parseDouble(locYField.getText()),
                            Float.parseFloat(locZField.getText()));
                    author.setLocation(newLocation);

                    lw.setAuthor(author);

                    return lw;
                } catch (Exception e) {
                    outputArea.appendText("Invalid input: " + e.getMessage() + "\n");
                    return null;
                }
            }
            return null;
        });

        Optional<LabWork> result = dialog.showAndWait();
        result.ifPresent(edited -> sendUpdateCommand(edited, consoleManager, lastCollection));
    }

    private void sendUpdateCommand(LabWork lw, ConsoleManager consoleManager, AtomicReference<Map<LabWork, String>> lastCollection) {
        try {
            String commandName;
            List<Object> args = List.of(
                    String.valueOf(lw.getId()),
                    String.valueOf(lw.getName()),
                    String.valueOf(lw.getCoordinates().getX()),
                    String.valueOf(lw.getCoordinates().getY()),
                    String.valueOf(lw.getMinimalPoint()),
                    String.valueOf(lw.getDifficulty()),
                    String.valueOf(lw.getAuthor().getName()),
                    String.valueOf(lw.getAuthor().getHeight()),
                    String.valueOf(lw.getAuthor().getEyeColor()),
                    String.valueOf(lw.getAuthor().getHairColor()),
                    String.valueOf(lw.getAuthor().getNationality()),
                    String.valueOf(lw.getAuthor().getLocation().getX()),
                    String.valueOf(lw.getAuthor().getLocation().getY()),
                    String.valueOf(lw.getAuthor().getLocation().getZ())
            );
            consoleManager.setArgsMode(args);
            List<Object> parsed = consoleManager.inputCommand("update id");
            commandName = parsed.get(0).toString();
            List<Object> argsCom = parsed.size() > 1 ? parsed.subList(1, parsed.size()) : List.of();
            CommandData data = new CommandData(commandName, clientPassword.getColor(), login, password, argsCom);
            consoleManager.setIndex(0);
            client.clientWork(data);
            outputArea.appendText("Update sent.\n");
            //lastCollection.set(clientConnection.getLastCollection());
        } catch (Exception e) {
            outputArea.appendText("Error sending update: " + e.getMessage() + "\n");
        }
    }

    private void updateLabWorkTableAndCanvas(Map<LabWork, String> collection) {
        labWorkList.setAll(collection.keySet());
        drawLabWorkOnCanvas(collection);
    }

    private final Set<Integer> animatingNow = new HashSet<>();

    private void drawLabWorkOnCanvas(Map<LabWork, String> collection) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        gc.setStroke(Color.GRAY);
        gc.strokeLine(0, canvas.getHeight() / 2, canvas.getWidth(), canvas.getHeight() / 2);
        gc.strokeLine(canvas.getWidth() / 2, 0, canvas.getWidth() / 2, canvas.getHeight());

        gc.setFill(Color.BLACK);
        gc.fillText("X", canvas.getWidth() - 20, canvas.getHeight() / 2 - 5);
        gc.fillText("Y", canvas.getWidth() / 2 + 5, 15);

        Set<Integer> currentIds = collection.keySet().stream()
                .map(LabWork::getId)
                .collect(Collectors.toSet());

        Set<Integer> previousIds = new HashSet<>(animatedLabWorkRadii.keySet());
        for (Integer id : previousIds) {
            if (!currentIds.contains(id)) {
                animatedLabWorkRadii.remove(id);
                animatedLabWorkColors.remove(id);
            }
        }

        for (Map.Entry<LabWork, String> entry : collection.entrySet()) {
            LabWork lw = entry.getKey();
            String colorStr = entry.getValue();

            Color color;
            try {
                color = Color.web(colorStr);
            } catch (IllegalArgumentException e) {
                color = Color.GRAY;
            }

            double x = canvas.getWidth() / 2 + lw.getCoordinates().getX() * scale;
            double y = canvas.getHeight() / 2 - lw.getCoordinates().getY() * scale;
            double targetRadius = Math.max(3, lw.getMinimalPoint());
            Integer id = lw.getId();

            Double previousRadius = animatedLabWorkRadii.get(id);
            Color previousColor = animatedLabWorkColors.get(id);

            boolean needsAnimation = previousRadius == null || !Objects.equals(previousRadius, targetRadius)
                    || previousColor == null || !Objects.equals(previousColor, color);

            if (needsAnimation && !animatingNow.contains(id)) {
                animatingNow.add(id);
                animatedLabWorkRadii.put(id, targetRadius);
                animatedLabWorkColors.put(id, color);
                animateCircle(gc, x, y, targetRadius, color, id);
            } else if (!animatingNow.contains(id)) {
                gc.setFill(color);
                gc.fillOval(x - targetRadius / 2, y - targetRadius / 2, targetRadius, targetRadius);
            }
        }
    }

    private void animateCircle(GraphicsContext gc, double x, double y, double targetRadius, Color color, int id) {
        Timeline timeline = new Timeline();
        final int frames = 20;

        for (int i = 1; i <= frames; i++) {
            double progress = (double) i / frames;
            double radius = targetRadius * progress;

            KeyFrame keyFrame = new KeyFrame(Duration.millis(i * 20), event -> {
                gc.setFill(color);
                gc.fillOval(x - radius / 2, y - radius / 2, radius, radius);
            });

            timeline.getKeyFrames().add(keyFrame);
        }

        timeline.setCycleCount(1);
        timeline.setOnFinished(e -> animatingNow.remove(id));
        timeline.play();
    }


    private void updateTexts() {
        instructionLabel.setText(bundle.getString("instruction"));
        inputArea.setPromptText(bundle.getString("input_prompt"));
        outputArea.setPromptText(bundle.getString("output_prompt"));
        executeButton.setText(bundle.getString("execute_button"));
        languageLabel.setText(bundle.getString("language_label"));
    }
}
