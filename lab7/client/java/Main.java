import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import clientutility.Client;
import clientutility.ClientConnection;
import clientutility.ClientPassword;
import commands.ExecuteScript;
import utility.*;

public class Main {
    public static void main(String[] args) {
        int serverPort;
        try {
            serverPort = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.err.println("Неверный формат порта. Убедитесь, что передано число.");
            return;
        }

        System.out.println("Подключение к серверу на порту: " + serverPort);

        try {
            try (Scanner scanner = new Scanner(System.in)) {
                ClientConnection clientConnection = new ClientConnection(serverPort);
                Client client = new Client(clientConnection);
                CommandManager commandManager = new CommandManager();
                ConsoleManager consoleManager = new ConsoleManager(commandManager);

                ClientPassword clientPassword = new ClientPassword(clientConnection);
                String login = null, password = null;
                while (clientPassword.getToken() == null) {
                    System.out.println("Введите LOGIN/REGISTRATION");
                    String instruction = scanner.nextLine().trim();
                    System.out.println("Введите login");
                    login = scanner.nextLine().trim();
                    System.out.println("Введите password");
                    password = scanner.nextLine().trim();
                    clientPassword.clientPasswordWork(instruction, login, password);
                }

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

                                CommandData commandData = new CommandData(commandName, clientPassword.getToken(), login, password, argsCom);
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

                            CommandData commandData = new CommandData(commandName, clientPassword.getToken(), login, password, argsCom);
                            client.clientWork(commandData);
                        }
                    } catch (Exception e) {
                        System.out.println("Ошибка при выполнении команды: " + e.getMessage());
                    }

                    System.out.println("Введите следующую команду:");
                    input = scanner.nextLine().trim();
                }

                clientConnection.stop();
                System.out.println("Работа клиента завершена");
            }
        } catch (NumberFormatException e) {
            System.out.println("Неверный формат порта. Укажите числовое значение.");
            System.exit(1);
        } catch (IOException e) {
            System.out.println("Ошибка в клиенте: " + e.getMessage());
            System.exit(1);
        }
    }
}