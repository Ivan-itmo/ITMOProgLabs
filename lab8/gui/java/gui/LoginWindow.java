package gui;

import clientutility.Client;
import clientutility.ClientConnection;
import clientutility.ClientPassword;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utility.CommandManager;
import utility.ConsoleManager;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginWindow {
    private final ClientConnection clientConnection;
    private final ClientPassword clientPassword;
    private final Client client;
    private final CommandManager commandManager;
    private final ConsoleManager consoleManager;

    private ResourceBundle bundle;
    private ComboBox<Locale> languageSelector;

    // UI элементы, нуждающиеся в локализации
    private Label instructionLabel;
    private TextField loginField;
    private PasswordField passwordField;
    private Button loginButton;
    private Label errorLabel;
    private Label languageLabel;

    public LoginWindow(ClientConnection clientConnection, ClientPassword clientPassword, Client client,
                       CommandManager commandManager, ConsoleManager consoleManager) {
        this.clientPassword = clientPassword;
        this.client = client;
        this.commandManager = commandManager;
        this.consoleManager = consoleManager;
        this.clientConnection = clientConnection;
        this.bundle = ResourceBundle.getBundle("LoginWindow", new Locale("en", "UK"));
    }

    public void show() {
        Stage stage = new Stage();
        stage.setTitle(bundle.getString("title"));

        instructionLabel = new Label();
        loginField = new TextField();
        passwordField = new PasswordField();
        loginButton = new Button();
        errorLabel = new Label();
        languageLabel = new Label();

        errorLabel.setStyle("-fx-text-fill: red;");

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
            bundle = ResourceBundle.getBundle("LoginWindow", selected);
            updateTexts();
        });

        updateTexts();

        loginButton.setOnAction(e -> {
            String login = loginField.getText().trim();
            String password = passwordField.getText().trim();

            if (login.isEmpty() || password.isEmpty()) {
                errorLabel.setText(bundle.getString("empty_error"));
                return;
            }

            try {
                clientPassword.clientPasswordWork("login", login, password);

                if (clientPassword.getColor() != null) {
                    stage.close();
                    new CommandWindow(clientConnection, client, clientPassword, commandManager, consoleManager, login, password);
                } else {
                    errorLabel.setText(bundle.getString("login_failed"));
                }
            } catch (IOException ex) {
                errorLabel.setText(bundle.getString("io_error") + ": " + ex.getMessage());
                ex.printStackTrace();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });

        HBox languageBox = new HBox(10, languageLabel, languageSelector);
        languageBox.setAlignment(Pos.CENTER);

        VBox vbox = new VBox(10, instructionLabel, loginField, passwordField, loginButton, errorLabel, languageBox);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vbox, 400, 280);
        stage.setScene(scene);
        stage.show();
    }

    private void updateTexts() {
        instructionLabel.setText(bundle.getString("instruction"));
        loginField.setPromptText(bundle.getString("login_prompt"));
        passwordField.setPromptText(bundle.getString("password_prompt"));
        loginButton.setText(bundle.getString("login_button"));
        languageLabel.setText(bundle.getString("language_label"));
    }
}