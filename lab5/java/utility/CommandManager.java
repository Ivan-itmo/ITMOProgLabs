package utility;
import exceptions.MaxRecursionException;
import filesutility.CommandFromScript;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Класс манагера команд, нужен для хранения всякой информации о командах, производит действия над командами
 */
public class CommandManager {
    public List<CommandFromScript> commandList = new ArrayList<>();
    private Map<String, Integer> commandIndices = new HashMap<>(); // Индексы для каждой команды
    public int counterOfCommands = 1;
    public String getNext(String command) {
        if (commandList == null) {
            throw new IllegalStateException("commandList не инициализирован!");
        }

        // Получаем текущий индекс аргументов для команды (по умолчанию 0)
        int idx = commandIndices.getOrDefault(command, 0);

        // Ищем команду в списке команд
        for (CommandFromScript entry : commandList) {
            if (entry.getCommand().equals(command) && entry.getIdCommand() == counterOfCommands) {

                // Если команда новая - сбрасываем индекс
                if (!commandIndices.containsKey(command)) {
                    commandIndices.put(command, 0);
                }

                // Проверка на правильное количество аргументов для текущей команды
                if (commandArgumentSize.get(command) == entry.getArguments().length) {
                    // Печатаем текущий аргумент
                    System.out.println(entry.getArguments()[idx]);

                    // Увеличиваем индекс, если есть еще аргументы
                    if (idx < commandArgumentSize.get(command) - 1) {
                        commandIndices.put(command, idx + 1);
                    } else {
                        // Если все аргументы обработаны, сбрасываем индекс на 0
                        commandIndices.put(command, 0);
                    }

                    return entry.getArguments()[idx];
                } else {
                    throw new IllegalStateException("Неверное количество аргументов для команды: " + command);
                }
            }
        }
        throw new IllegalStateException("Команда не найдена: " + command);
    }





    private Map<String, String> commandsDescription = new HashMap<>();
    {
        commandsDescription.put("info", "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)");
        commandsDescription.put("show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        commandsDescription.put("add {element}", "добавить новый элемент в коллекцию");
        commandsDescription.put("update id {element}", "обновить значение элемента коллекции, id которого равен заданному");
        commandsDescription.put("remove_by_id id", "удалить элемент из коллекции по его id");
        commandsDescription.put("clear", "очистить коллекцию");
        commandsDescription.put("save", "сохранить коллекцию в файл");
        commandsDescription.put("execute_script file_name", "считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме");
        commandsDescription.put("exit", "сзавершить программу (без сохранения в файл)");
        commandsDescription.put("add_if_max {element}", "добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции");
        commandsDescription.put("add_if_min {element}", "добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции");
        commandsDescription.put("history", "вывести последние 8 команд (без их аргументов)");
        commandsDescription.put("remove_any_by_difficulty difficulty", "удалить из коллекции один элемент, значение поля difficulty которого эквивалентно заданному");
        commandsDescription.put("sum_of_minimal_point", "вывести сумму значений поля minimalPoint для всех элементов коллекции");
        commandsDescription.put("max_by_coordinates", "вывести любой объект из коллекции, значение поля coordinates которого является максимальным");
    }
    public Map<String, String> getCommands() {
        return commandsDescription;
    }

    private Map<String, Integer> commandArgumentSize = new HashMap<>();
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
        commandArgumentSize.put("save", 1);
        commandArgumentSize.put("execute_script", 1);
        commandArgumentSize.put("show", 0);
        commandArgumentSize.put("sum_of_minimal_point", 0);
        commandArgumentSize.put("update id", 14);

    }
    public Map<String, Integer> getCommandArgumentSize() {
        return commandArgumentSize;
    }


    private int recursionDepth = 0;
    private static Queue<String> queue = new ArrayBlockingQueue<>(8);
    public Queue<String> getQueue() {
        return queue;
    }
    public void put(String command){
        if (Objects.equals(command, "execute_script")) {
            recursionDepth++;
            try {
                if (recursionDepth == 5) {
                    throw new MaxRecursionException("Достигнута максимальная глубина рекурсии - 5");
                }
            }
            catch (MaxRecursionException e) {
                System.out.println(e.getMessage());
                System.exit(1);
            }
        }
        if (queue.size() == 8){
            queue.remove();
        }
        queue.add(command);
    }
}
