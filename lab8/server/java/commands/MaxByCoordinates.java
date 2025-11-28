package commands;

import exceptions.MyNullPointerException;
import models.Coordinates;
import models.LabWork;
import serverutility.ServerCommand;
import utility.CollectionManager;
import utility.CommandManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Команда для поиска и вывода элементов коллекции с максимальными координатами.
 * Класс наследует абстрактный класс Command и реализует метод execute().
 */
public class MaxByCoordinates extends Command {

    /**
     * Конструктор класса MaxByCoordinates.
     *
     * @param collectionManager менеджер коллекции, с которым будет работать команда.
     * @param commandManager менеджер команд, который управляет регистрацией и выполнением команд.
     * @param command название команды, которое будет зарегистрировано в менеджере команд.
     * @param serverCommand менеджер консоли, который используется для взаимодействия с пользователем.
     */
    public MaxByCoordinates(CollectionManager collectionManager, CommandManager commandManager, String command, List<Object> args, ServerCommand serverCommand, String login) {
        super(collectionManager, commandManager, args, serverCommand, login);
        commandManager.put(command);
    }

    /**
     * Метод execute() реализует логику команды max_by_coordinates.
     * Находит и выводит элементы коллекции, у которых координаты являются максимальными.
     * Если коллекция пуста, выбрасывается исключение MyNullPointerException.
     */
    @Override
    public List<Object> execute() {
        try {
            if (collectionManager.collectionSize() == 0) {
                throw new MyNullPointerException("Ошибка: коллекция пуста");
            }
            Coordinates maxCoordinates = collectionManager.getMaxCoordinates();
            List<Object> list = new ArrayList<>();
            for (LabWork labWork : collectionManager.getCollection()) {
                if (labWork.getCoordinates().compareTo(maxCoordinates) == 0) {
                    list.add(labWork);
                }
            }
            return list;

        } catch (MyNullPointerException e) {
            List<Object> list = new ArrayList<>();
            list.add(e.getMessage());
            return list;
        }
    }
}