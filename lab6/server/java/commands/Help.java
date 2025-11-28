package commands;

import serverutility.ServerCommand;
import serverutility.ServerAnswers;
import utility.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Команда для вывода списка доступных команд и их описаний.
 * Класс наследует абстрактный класс Command и реализует метод execute().
 */
public class Help extends Command {

    /**
     * Конструктор класса Help.
     *
     * @param collectionManager менеджер коллекции, с которым будет работать команда.
     * @param commandManager менеджер команд, который управляет регистрацией и выполнением команд.
     * @param command название команды, которое будет зарегистрировано в менеджере команд.
     * @param serverCommand менеджер консоли, который используется для взаимодействия с пользователем.
     */
    public Help(CollectionManager collectionManager, CommandManager commandManager, String command, List<Object> args, ServerCommand serverCommand) {
        super(collectionManager, commandManager, args, serverCommand);
        commandManager.put(command); // Регистрация команды в менеджере
    }

    /**
     * Метод execute() реализует логику команды help.
     * Выводит список всех доступных команд и их описаний, зарегистрированных в менеджере команд.
     */
    @Override
    public List<Object> execute() {
        List<Object> list = new ArrayList<>();
        for (var entry : commandManager.getCommands().entrySet()) {
            list.add(entry.getKey() + " - " + entry.getValue());
        }
        return list;
    }
}