package utility;

import filesutility.CommandFromScript;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Класс для управления командами и их аргументами.
 * Обеспечивает хранение, выполнение и управление командами, а также их аргументами.
 */
public class CommandManager {
    public List<CommandFromScript> commandList = new ArrayList<>();
    private Map<String, Integer> commandIndices = new HashMap<>();
    public int counterOfCommands = 1;

    /**
     * Получает следующий аргумент для указанной команды.
     *
     * @param command Команда, для которой запрашивается аргумент.
     * @return Следующий аргумент команды.
     */
    public String getNext(String command) {
        if (commandList == null) {
            System.out.println("Ошибка: commandList не инициализирован!");
            System.exit(1);
        }

        int idx = commandIndices.getOrDefault(command, 0);

        for (CommandFromScript entry : commandList) {
            if (entry.getCommand().equals(command) && entry.getIdCommand() == counterOfCommands) {
                if (!commandIndices.containsKey(command)) {
                    commandIndices.put(command, 0);
                }

                if (commandArgumentSize.get(command) == entry.getArguments().length) {
                    System.out.println(entry.getArguments()[idx]);

                    if (idx < commandArgumentSize.get(command) - 1) {
                        commandIndices.put(command, idx + 1);
                    } else {
                        commandIndices.put(command, 0);
                    }

                    return entry.getArguments()[idx];
                } else {
                    System.out.println("Ошибка: Неверное количество аргументов для команды: " + command);
                    System.exit(1);
                }
            }
        }
        System.out.println("Ошибка: Команда не найдена: " + command);
        System.exit(1);
        return null;
    }


    private final Map<String, Integer> commandArgumentSize = new HashMap<>();
    {
        commandArgumentSize.put("add", 13);
        commandArgumentSize.put("add_if_max", 13);
        commandArgumentSize.put("add_if_min", 13);
        commandArgumentSize.put("clear", 0);
        commandArgumentSize.put("help", 0);
        commandArgumentSize.put("history", 0);
        commandArgumentSize.put("info", 0);
        commandArgumentSize.put("max_by_coordinates", 0);
        commandArgumentSize.put("remove_any_by_difficulty", 1);
        commandArgumentSize.put("remove_by_id", 1);
        commandArgumentSize.put("execute_script", 1);
        commandArgumentSize.put("show", 0);
        commandArgumentSize.put("sum_of_minimal_point", 0);
        commandArgumentSize.put("update id", 14);
    }
    public Map<String, Integer> getCommandArguments() {
        return commandArgumentSize;
    }

    public List<String> listFilePath = new ArrayList<>();
}