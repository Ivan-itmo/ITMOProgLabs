import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import clientutility.Client;
import commands.ExecuteScript;
import utility.*;

public class Main {
    public static void main(String[] args) {
        int serverPort;

        if (args.length > 0) {
            try {
                serverPort = Integer.parseInt(args[0]); // Получаем порт из аргумента командной строки
            } catch (NumberFormatException e) {
                System.err.println("Неверный формат порта. Убедитесь, что передано число.");
                return;
            }
        } else {
            // Если порт не передан, читаем его из файла
            try (BufferedReader reader = new BufferedReader(new FileReader("/Users/ivan/Desktop/server_port.txt"))) {
                serverPort = Integer.parseInt(reader.readLine().trim());
            } catch (IOException | NumberFormatException e) {
                System.err.println("Ошибка чтения порта из файла. Убедитесь, что файл 'server_port.txt' существует.");
                return;
            }
        }

        System.out.println("Подключение к серверу на порту: " + serverPort);

        try {
            try (Scanner scanner = new Scanner(System.in)) {
                Client client = new Client(serverPort);  // Используем порт из аргументов
                CommandManager commandManager = new CommandManager();
                ConsoleManager consoleManager = new ConsoleManager(commandManager);

                System.out.println("Введите команду (или 'exit' для выхода):");
                String input = scanner.nextLine().trim();

                while (!input.equalsIgnoreCase("exit")) {
                    try {
                        List<Object> commandAndArgs;
                        if (input.equalsIgnoreCase("execute_script")) {
                            ExecuteScript executeScript = new ExecuteScript(commandManager, input, consoleManager);
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

                                CommandData commandData = new CommandData(commandName, argsCom);
                                client.clientWork(commandData);
                            }
                        } else {
                            commandAndArgs = consoleManager.execute(input);
                            if (commandAndArgs == null || commandAndArgs.isEmpty()) {
                                input = scanner.nextLine().trim();
                                continue;
                            }

                            String commandName = commandAndArgs.get(0).toString();
                            List<Object> argsCom = commandAndArgs.size() > 1
                                    ? commandAndArgs.subList(1, commandAndArgs.size())
                                    : List.of();

                            CommandData commandData = new CommandData(commandName, argsCom);
                            client.clientWork(commandData);
                        }
                    } catch (Exception e) {
                        System.out.println("Ошибка при выполнении команды: " + e.getMessage());
                    }

                    System.out.println("Введите следующую команду:");
                    input = scanner.nextLine().trim();
                }

                // Отправка команды save перед выходом
                CommandData commandData = new CommandData("save", List.of());
                client.clientWork(commandData);
                client.stop();
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