package filesutility;

import exceptions.WrongCommandArgsLength;
import utility.CommandManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Класс для чтения команд из файла и их обработки.
 * Поддерживает чтение команд из текстового файла, обработку комментариев,
 * а также вставку новых команд на указанную позицию в существующий список команд.
 * <p>
 * Команды в файле должны быть записаны по одной на строку. Пустые строки и строки,
 * начинающиеся с символа '#', игнорируются.
 * </p>
 */
public class ScriptRead {

    /**
     * Читает команды из файла и добавляет их в указанный список команд.
     * Если список команд равен null, создается новый список.
     * Поддерживает вставку новых команд на указанную позицию в списке.
     *
     * @param filePath        путь к файлу с командами.
     * @param commandManager  менеджер команд, с помощью него пополняем список команд из скрипта.
     */
    public void readCommandsFromFile(String filePath, CommandManager commandManager) {
        if (commandManager.commandList == null) {
            commandManager.commandList = new ArrayList<>();
        }

        List<CommandFromScript> newCommands = new ArrayList<>();
        List<String> parts = new ArrayList<>();
        int c = determineNextId(commandManager.commandList);
        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!line.isEmpty()){
                    line = line.trim();
                }
                if (line.startsWith("#")) continue;
                parts.add(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Ошибка: Файл не найден - " + filePath);
            System.exit(1);
            return;
        }
        String command = "";
        List<String> arguments = new ArrayList<>();
        //int f = 0;
        int countOfCommands = 0;
        for (int i = 0; i < parts.size(); i++) {
            if (countOfCommands == 0 && commandManager.getCommandArguments().containsKey(parts.get(i))) {
                command = parts.get(i);
                countOfCommands++;
            }
            else if(commandManager.getCommandArguments().containsKey(parts.get(i))){
                String[] args = new String[arguments.size()];
                for (int j = 0; j < arguments.size(); j++) {
                    args[j] = arguments.get(j);
                }
                try {
                    if (arguments.size() != commandManager.getCommandArguments().get(command)){
                        throw new WrongCommandArgsLength("Неверное количество аргументов у команды в скрипте");
                    }
                    newCommands.add(new CommandFromScript(c++, command, args));
                } catch (WrongCommandArgsLength e) {
                    System.out.println(e.getMessage());
                    System.exit(1);
                }
                command = parts.get(i);
                arguments = new ArrayList<>();
                countOfCommands++;
            }
            else {
                arguments.add(parts.get(i));
                System.out.println(parts.get(i));
            }
        }
        String[] args = new String[arguments.size()];
        for (int j = 0; j < arguments.size(); j++) {
            args[j] = arguments.get(j);
        }
        try {
            if (arguments.size() != commandManager.getCommandArguments().get(command)){
                throw new WrongCommandArgsLength("Неверное количество аргументов у команды в скрипте");
            }
            newCommands.add(new CommandFromScript(c++, command, args));
        } catch (WrongCommandArgsLength e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }

        if (commandManager.commandList.isEmpty()) {
            commandManager.commandList.addAll(newCommands);
        } else {
            replaceCommandsAtPosition(commandManager.commandList, commandManager.counterOfCommands, newCommands);
        }


        renumberCommands(commandManager.commandList);

        for (CommandFromScript entry : commandManager.commandList) {
            System.out.println(entry.getIdCommand() + " " + entry.getCommand() + " : " + Arrays.toString(entry.getArguments()));
        }
    }

    /**
     * Определяет следующий доступный идентификатор для новой команды.
     *
     * @param commandList список команд.
     * @return следующий доступный идентификатор.
     */
    private int determineNextId(List<CommandFromScript> commandList) {
        return commandList.isEmpty() ? 1 : commandList.get(commandList.size() - 1).getIdCommand() + 1;
    }

    /**
     * Заменяет команду на указанной позиции в списке новыми командами.
     *
     * @param commandList     список команд.
     * @param replacePosition позиция для замены (индексация с 1).
     * @param newCommands     новые команды для вставки.
     */
    private void replaceCommandsAtPosition(List<CommandFromScript> commandList, int replacePosition, List<CommandFromScript> newCommands) {
        int adjustedPosition = adjustReplacePosition(replacePosition, commandList.size());

        if (adjustedPosition < 0 || adjustedPosition >= commandList.size()) {
            System.out.println("Ошибка: Указанная позиция вне диапазона списка.");
            return;
        }

        commandList.remove(adjustedPosition);

        commandList.addAll(adjustedPosition, newCommands);
    }

    /**
     * Корректирует позицию замены, чтобы она находилась в пределах списка.
     *
     * @param replacePosition исходная позиция замены (индексация с 1).
     * @param listSize        размер списка команд.
     * @return скорректированная позиция (индексация с 0).
     */
    private int adjustReplacePosition(int replacePosition, int listSize) {
        if (replacePosition <= 0) {
            return 0;
        } else if (replacePosition > listSize) {
            return listSize - 1;
        } else {
            return replacePosition - 1;
        }
    }

    /**
     * Перенумеровывает команды в списке, начиная с 1.
     *
     * @param commandList список команд.
     */
    private void renumberCommands(List<CommandFromScript> commandList) {
        for (int i = 0; i < commandList.size(); i++) {
            CommandFromScript entry = commandList.get(i);
            commandList.set(i, new CommandFromScript(i + 1, entry.getCommand(), entry.getArguments()));
        }
    }
}