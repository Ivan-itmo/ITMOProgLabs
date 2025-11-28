package filesutility;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Класс считывания команд
 */
public class ScriptRead {
    public void readCommandsFromFile(String filePath, List<CommandFromScript> commandList, int replacePosition) {
        if (commandList == null) {
            commandList = new ArrayList<>(); // Создаем новый список, чтобы не вылезало всяких ошибок с null
        }

        List<CommandFromScript> newCommands = new ArrayList<>(); // Временный список для новых команд, нужен для того, чтобы скрипт в скрипте реализовать

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int c = determineNextId(commandList); // Определяем следующий доступный id

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue; // Пропускаем пустые строки и комментарии
                String[] parts = line.split(" ", 2); // Разделяем команду и аргумент
                String command = parts[0];
                String[] arguments = (parts.length > 1) ? parts[1].split("\\s+") : new String[0];

                // Объединяем "update id"б так как это единственная команда, где есть пробел
                if (command.equals("update") && arguments.length > 0 && arguments[0].equals("id")) {
                    command = "update id";
                    arguments = Arrays.copyOfRange(arguments, 1, arguments.length);
                }

                // Добавляем новую команду с уникальным id
                newCommands.add(new CommandFromScript(c++, command, arguments));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Ошибка: Файл не найден - " + filePath);
            System.exit(1);
            return;
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
            return;
        }

        // Если список пустой или был null, добавляем новые команды в начало
        if (commandList.isEmpty()) {
            commandList.addAll(newCommands);
        } else {
            // Замена команды на указанной позиции
            replaceCommandsAtPosition(commandList, replacePosition, newCommands);
        }

        // Перенумерация всех команд после изменения
        renumberCommands(commandList);

        // Вывод для проверки
        for (CommandFromScript entry : commandList) {
            System.out.println(entry.getIdCommand() + " " + entry.getCommand() + " : " + Arrays.toString(entry.getArguments()));
        }
    }

    // Метод для определения следующего доступного id
    private int determineNextId(List<CommandFromScript> commandList) {
        return commandList.isEmpty() ? 1 : commandList.get(commandList.size() - 1).getIdCommand() + 1;
    }

    // Метод для замены команды на указанной позиции
    private void replaceCommandsAtPosition(List<CommandFromScript> commandList, int replacePosition, List<CommandFromScript> newCommands) {
        int adjustedPosition = adjustReplacePosition(replacePosition, commandList.size());

        if (adjustedPosition < 0 || adjustedPosition >= commandList.size()) {
            System.out.println("Ошибка: Указанная позиция вне диапазона списка.");
            return;
        }

        // Удаляем команду на указанной позиции
        commandList.remove(adjustedPosition);

        // Вставляем новые команды на место удаленной
        commandList.addAll(adjustedPosition, newCommands);
    }

    // Метод для корректировки позиции замены
    private int adjustReplacePosition(int replacePosition, int listSize) {
        if (replacePosition <= 0) {
            return 0; // Если позиция <= 0, заменяем первую команду
        } else if (replacePosition > listSize) {
            return listSize - 1; // Если позиция больше размера списка, заменяем последнюю команду
        } else {
            return replacePosition - 1; // Преобразуем индексацию с 1 в индексацию с 0
        }
    }

    // Метод для перенумерации всех команд
    private void renumberCommands(List<CommandFromScript> commandList) {
        for (int i = 0; i < commandList.size(); i++) {
            CommandFromScript entry = commandList.get(i);
            commandList.set(i, new CommandFromScript(i + 1, entry.getCommand(), entry.getArguments()));
        }
    }
}