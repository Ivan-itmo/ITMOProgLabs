package commands;

import exceptions.MyNullPointerException;
import utility.CollectionManager;
import utility.CommandManager;
import serverutility.ServerCommand;
import serverutility.ServerAnswers;

import java.util.ArrayList;
import java.util.List;

/**
 * Команда для вывода всех элементов коллекции.
 * Класс наследует абстрактный класс Command и реализует метод execute().
 * Выводит все элементы коллекции в строковом представлении.
 */
public class Show extends Command {

    /**
     * Конструктор класса Show.
     *
     * @param collectionManager менеджер коллекции, с которым будет работать команда.
     * @param commandManager менеджер команд, который управляет регистрацией и выполнением команд.
     * @param command название команды, которое будет зарегистрировано в менеджере команд.
     * @param serverCommand менеджер консоли, который используется для взаимодействия с пользователем.
     */
    public Show(CollectionManager collectionManager, CommandManager commandManager, String command, List<Object> args, ServerCommand serverCommand) {
        super(collectionManager, commandManager, args, serverCommand);
        commandManager.put(command); // Регистрация команды в менеджере
    }

    /**
     * Метод execute() реализует логику команды show.
     * Выводит все элементы коллекции в строковом представлении.
     * Если коллекция пуста, выбрасывается исключение MyNullPointerException.
     */
    @Override
    public List<Object> execute() {
        try {
            if (collectionManager.collectionSize() == 0) {
                throw new MyNullPointerException("Ошибка: коллекция пуста");
            }
            return collectionManager.collectionShow();
        } catch (MyNullPointerException e) {
            List<Object> list = new ArrayList<>();
            list.add(e.getMessage());
            return list;
        }
    }
}