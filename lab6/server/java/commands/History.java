package commands;

import serverutility.ServerCommand;
import serverutility.ServerAnswers;
import utility.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Команда для вывода истории выполненных команд.
 * Класс наследует абстрактный класс Command и реализует метод execute().
 */
public class History extends Command {

    /**
     * Конструктор класса History.
     *
     * @param collectionManager менеджер коллекции, с которым будет работать команда.
     * @param commandManager менеджер команд, который управляет регистрацией и выполнением команд.
     * @param command название команды, которое будет зарегистрировано в менеджере команд.
     * @param serverCommand менеджер консоли, который используется для взаимодействия с пользователем.
     */
    public History(CollectionManager collectionManager, CommandManager commandManager, String command, List<Object> args, ServerCommand serverCommand) {
        super(collectionManager, commandManager, args, serverCommand);
        commandManager.put(command); // Регистрация команды в менеджере
    }

    /**
     * Метод execute() реализует логику команды history.
     * Выводит список последних выполненных команд, хранящихся в очереди команд.
     */
    @Override
    public List<Object> execute() {
        List<Object> list = new ArrayList<>();
        for (String cmd : commandManager.getQueue()) { // Используем геттер из CommandManager
            list.add(cmd);
        }
        return list;
    }
}