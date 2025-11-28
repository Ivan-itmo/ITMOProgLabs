package commands;

import exceptions.MyNullPointerException;
import serverutility.ServerCommand;
import utility.CollectionManager;
import utility.CommandManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Команда для вычисления суммы значений поля minimalPoint всех элементов коллекции.
 * Класс наследует абстрактный класс Command и реализует метод execute().
 */
public class SumOfMinimalPoint extends Command {

    /**
     * Конструктор класса SumOfMinimalPoint.
     *
     * @param collectionManager менеджер коллекции, с которым будет работать команда.
     * @param commandManager менеджер команд, который управляет регистрацией и выполнением команд.
     * @param command название команды, которое будет зарегистрировано в менеджере команд.
     * @param serverCommand менеджер консоли, который используется для взаимодействия с пользователем.
     */
    public SumOfMinimalPoint(CollectionManager collectionManager, CommandManager commandManager, String command, List<Object> args, ServerCommand serverCommand, String login) {
        super(collectionManager, commandManager, args, serverCommand, login);
        commandManager.put(command);
    }

    /**
     * Метод execute() реализует логику команды sum_of_minimal_point.
     * Вычисляет сумму значений поля minimalPoint всех элементов коллекции и выводит результат.
     * Если коллекция пуста, выбрасывается исключение MyNullPointerException.
     */
    @Override
    public List<Object> execute() {
        try {
            if (collectionManager.collectionSize() == 0) {
                throw new MyNullPointerException("Ошибка: коллекция пуста");
            }
            List<Object> list = new ArrayList<>();
            list.add("Сумма значений: " + collectionManager.collectionSum());
            return list;
        } catch (MyNullPointerException e) {
            List<Object> list = new ArrayList<>();
            list.add(e.getMessage());
            return list;
        }
    }
}