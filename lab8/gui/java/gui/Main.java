package gui;

import clientutility.Client;
import clientutility.ClientConnection;
import clientutility.ClientPassword;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import utility.CommandManager;
import utility.ConsoleManager;

import static client.ClientMain.getClientObjects;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Запускаем клиента в отдельном потоке, чтобы не блокировать UI
        new Thread(() -> {
            try {
                client.ClientMain.main(new String[]{"12345"});

                Object[] initialization = getClientObjects();
                ClientConnection clientConnection = (ClientConnection) initialization[0];
                Client client = (Client) initialization[1];
                CommandManager commandManager = (CommandManager) initialization[2];
                ConsoleManager consoleManager = (ConsoleManager) initialization[3];
                ClientPassword clientPassword = (ClientPassword) initialization[4];

                // UI запускаем в JavaFX потоке
                Platform.runLater(() -> {
                    WelcomeWindow welcomeWindow = new WelcomeWindow(
                            clientConnection,
                            clientPassword,
                            client,
                            commandManager,
                            consoleManager
                    );
                    welcomeWindow.show();
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void main(String[] args) {
        launch(args); // Запускаем JavaFX
    }
}