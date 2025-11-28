package client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import clientutility.Client;
import clientutility.ClientConnection;
import clientutility.ClientPassword;
import commands.ExecuteScript;
import utility.*;

public class ClientMain {

    public static void main(String[] args) throws IOException {
        int serverPort;
        try {
            serverPort = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.err.println("Неверный формат порта. Убедитесь, что передано число.");
            return;
        }

        System.out.println("Подключение к серверу на порту: " + serverPort);


        initializeClientObjects(serverPort);

            /*ClientConnection clientConnection = (ClientConnection) objects[0];
            Client client = (Client) objects[1];
            CommandManager commandManager = (CommandManager) objects[2];
            ConsoleManager consoleManager = (ConsoleManager) objects[3];
            ClientPassword clientPassword = (ClientPassword) objects[4];

            String[] credentials = handleAuthorization(clientPassword, scanner);
            runCommandLoop(client, clientPassword, commandManager, consoleManager, scanner, credentials[0], credentials[1]);

            clientConnection.stop();
            System.out.println("Работа клиента завершена");*/

    }
    static Object[] initialization = new Object[5];

    public static void initializeClientObjects(int port) throws IOException {
        ClientConnection clientConnection = new ClientConnection(port);
        Client client = new Client(clientConnection);
        CommandManager commandManager = new CommandManager();
        ConsoleManager consoleManager = new ConsoleManager(commandManager);
        ClientPassword clientPassword = new ClientPassword(clientConnection);
        initialization = new Object[]{clientConnection, client, commandManager, consoleManager, clientPassword};
    }

    public static Object[] getClientObjects() {
        return initialization;
    }

    public static String[] handleAuthorization(ClientPassword clientPassword, Scanner scanner) throws IOException, InterruptedException {
        String login = null;
        String password = null;

        while (clientPassword.getColor() == null) {
            System.out.println("Введите LOGIN/REGISTRATION");
            String instruction = scanner.nextLine().trim();
            System.out.println("Введите login");
            login = scanner.nextLine().trim();
            System.out.println("Введите password");
            password = scanner.nextLine().trim();
            clientPassword.clientPasswordWork(instruction, login, password);
        }

        return new String[]{login, password};
    }

    public static void runCommandLoop(Client client, ClientPassword clientPassword, CommandManager commandManager,
                                      ConsoleManager consoleManager, Scanner scanner, String login, String password) {
        System.out.println("Введите команду (или 'exit' для выхода):");
        String input = scanner.nextLine().trim();

        while (!input.equalsIgnoreCase("exit")) {
            try {
                List<Object> commandAndArgs;
                if (input.equalsIgnoreCase("execute_script")) {
                    ExecuteScript executeScript = new ExecuteScript(commandManager, consoleManager);
                    List<List<Object>> executeScriptCommand = executeScript.execute();
                    List<List<Object>> consoleScriptCommand = consoleManager.getArgsCom();
                    List<List<Object>> combinedList = new ArrayList<>();
                    combinedList.addAll(executeScriptCommand);
                    combinedList.addAll(consoleScriptCommand);

                    for (List<Object> commandAndArg : combinedList) {
                        String commandName = commandAndArg.get(0).toString();
                        List<Object> argsCom = commandAndArg.size() > 1
                                ? commandAndArg.subList(1, commandAndArg.size())
                                : List.of();

                        CommandData commandData = new CommandData(commandName, clientPassword.getColor(), login, password, argsCom);
                        client.clientWork(commandData);
                    }

                } else {
                    commandAndArgs = consoleManager.inputCommand(input);
                    if (commandAndArgs == null || commandAndArgs.isEmpty()) {
                        input = scanner.nextLine().trim();
                        continue;
                    }

                    String commandName = commandAndArgs.get(0).toString();
                    List<Object> argsCom = commandAndArgs.size() > 1
                            ? commandAndArgs.subList(1, commandAndArgs.size())
                            : List.of();

                    CommandData commandData = new CommandData(commandName, clientPassword.getColor(), login, password, argsCom);
                    client.clientWork(commandData);
                }
            } catch (Exception e) {
                System.out.println("Ошибка при выполнении команды: " + e.getMessage());
            }

            System.out.println("Введите следующую команду:");
            input = scanner.nextLine().trim();
        }
    }
}