package utility;

//import filesutility.CommandFromScript;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Класс для управления командами и их аргументами.
 * Обеспечивает хранение, выполнение и управление командами, а также их аргументами.
 */
public class CommandManager {

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
        commandsDescription.put("exit", "завершить программу (без сохранения в файл)");
        commandsDescription.put("add_if_max {element}", "добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции");
        commandsDescription.put("add_if_min {element}", "добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции");
        commandsDescription.put("history", "вывести последние 8 команд (без их аргументов)");
        commandsDescription.put("remove_any_by_difficulty difficulty", "удалить из коллекции один элемент, значение поля difficulty которого эквивалентно заданному");
        commandsDescription.put("sum_of_minimal_point", "вывести сумму значений поля minimalPoint для всех элементов коллекции");
        commandsDescription.put("max_by_coordinates", "вывести любой объект из коллекции, значение поля coordinates которого является максимальным");
    }

    /**
     * Возвращает описание всех доступных команд.
     *
     * @return Карта, где ключ - имя команды, значение - её описание.
     */
    public Map<String, String> getCommands() {
        return commandsDescription;
    }

    private static Queue<String> queue = new ArrayBlockingQueue<>(8);

    /**
     * Возвращает очередь последних выполненных команд.
     *
     * @return Очередь из последних 8 команд.
     */
    public Queue<String> getQueue() {
        return queue;
    }

    /**
     * Добавляет команду в очередь истории команд.
     *
     * @param command Команда для добавления в историю.
     */
    public void put(String command) {
        if (queue.size() == 8) {
            queue.remove();
        }
        queue.add(command);
    }
}