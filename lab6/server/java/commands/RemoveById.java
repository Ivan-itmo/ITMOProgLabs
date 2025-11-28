package commands;

//import asks.*;
import exceptions.MyNullPointerException;
import utility.CollectionManager;
import utility.CommandManager;
import serverutility.ServerCommand;
import serverutility.ServerAnswers;

import java.util.ArrayList;
import java.util.List;

/**
 * Команда для удаления элемента из коллекции по заданному идентификатору.
 * Класс наследует абстрактный класс Command и реализует метод execute().
 */
public class RemoveById extends Command {

    /**
     * Конструктор класса RemoveById.
     *
     * @param collectionManager менеджер коллекции, с которым будет работать команда.
     * @param commandManager менеджер команд, который управляет регистрацией и выполнением команд.
     * @param command название команды, которое будет зарегистрировано в менеджере команд.
     * @param serverCommand менеджер консоли, который используется для взаимодействия с пользователем.
     */
    public RemoveById(CollectionManager collectionManager, CommandManager commandManager, String command, List<Object> args, ServerCommand serverCommand) {
        super(collectionManager, commandManager, args, serverCommand);
        commandManager.put(command); // Регистрация команды в менеджере
    }

    /**
     * Метод execute() реализует логику команды remove_by_id.
     * Удаляет элемент из коллекции по заданному идентификатору.
     * Если коллекция пуста, выбрасывается исключение MyNullPointerException.
     */
    @Override
    public List<Object> execute() {
        try {
            if (collectionManager.collectionSize() == 0) {
                throw new MyNullPointerException("Ошибка: коллекция пуста");
            }
            //AskerRemoveById asker = new AskerRemoveById(consoleManager);
            //asker.ask();
            collectionManager.removebyidLabWork((Integer) args.get(0));
            List<Object> result = new ArrayList<>();
            result.add("Команда удаления по id выполнена");
            return result;
        } catch (MyNullPointerException e) {
            List<Object> list = new ArrayList<>();
            list.add(e.getMessage());
            return list;
        }
    }
}