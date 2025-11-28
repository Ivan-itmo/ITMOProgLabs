package gui;

import clientutility.Client;
import clientutility.ClientConnection;
import clientutility.ClientPassword;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utility.CommandManager;
import utility.ConsoleManager;

import java.util.Locale;
import java.util.ResourceBundle;

public class WelcomeWindow {
    private final ClientConnection connection;
    private final ClientPassword clientPassword;
    private final Client client;
    private final CommandManager commandManager;
    private final ConsoleManager consoleManager;

    private ResourceBundle bundle;
    private ComboBox<Locale> languageSelector;
    private Label label;
    private Button yesButton;
    private Button noButton;
    private Label languageLabel;

    public WelcomeWindow(ClientConnection clientConnection, ClientPassword clientPassword, Client client,
                         CommandManager commandManager, ConsoleManager consoleManager) {
        this.clientPassword = clientPassword;
        this.client = client;
        this.commandManager = commandManager;
        this.consoleManager = consoleManager;
        this.connection = clientConnection;
    }

    public void show() {
        Stage stage = new Stage();
        bundle = ResourceBundle.getBundle("WelcomeWindow", new Locale("en", "UK"));
        stage.setTitle(bundle.getString("title"));

        label = new Label();
        yesButton = new Button();
        noButton = new Button();
        languageLabel = new Label();

        updateTexts();

        yesButton.setOnAction(e -> {
            stage.close();
            new LoginWindow(connection, clientPassword, client, commandManager, consoleManager).show();
        });

        noButton.setOnAction(e -> {
            stage.close();
            new RegistrationWindow(connection, clientPassword, client, commandManager, consoleManager).show();
        });

        languageSelector = new ComboBox<>();
        languageSelector.getItems().addAll(
                new Locale("en", "UK"),
                new Locale("ru", "RU"),
                new Locale("fi", "FI"),
                new Locale("lt", "LT")
        );
        languageSelector.setValue(new Locale("en", "UK"));
        languageSelector.setOnAction(e -> {
            Locale selected = languageSelector.getValue();
            bundle = ResourceBundle.getBundle("WelcomeWindow", selected);
            updateTexts();
        });

        VBox vbox = new VBox(15, label, yesButton, noButton, languageLabel, languageSelector);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));
        vbox.setMinWidth(300);
        vbox.setMinHeight(250);

        Scene scene = new Scene(vbox);
        stage.setScene(scene);
        stage.show();
    }

    private void updateTexts() {
        label.setText(bundle.getString("registered_question"));
        yesButton.setText(bundle.getString("yes"));
        noButton.setText(bundle.getString("no"));
        languageLabel.setText(bundle.getString("language_label"));
    }
}