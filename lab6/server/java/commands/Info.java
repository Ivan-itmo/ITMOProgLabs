package commands;

import exceptions.MyNullPointerException;
import serverutility.ServerCommand;
import serverutility.ServerAnswers;
import utility.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Команда для вывода информации о коллекции.
 * Класс наследует абстрактный класс Command и реализует метод execute().
 * Выводит тип коллекции, дату инициализации и количество элементов.
 */
public class Info extends Command {

    /**
     * Конструктор класса Info.
     *
     * @param collectionManager менеджер коллекции, с которым будет работать команда.
     * @param commandManager менеджер команд, который управляет регистрацией и выполнением команд.
     * @param command название команды, которое будет зарегистрировано в менеджере команд.
     * @param serverCommand менеджер консоли, который используется для взаимодействия с пользователем.
     */
    public Info(CollectionManager collectionManager, CommandManager commandManager, String command, List<Object> args, ServerCommand serverCommand) {
        super(collectionManager, commandManager, args, serverCommand);
        commandManager.put(command); // Регистрация команды в менеджере
    }

    /**
     * Метод execute() реализует логику команды info.
     * Выводит информацию о коллекции, включая тип коллекции, дату инициализации и количество элементов.
     * Если коллекция пуста, выбрасывается исключение MyNullPointerException.
     */
    @Override
    public List<Object> execute() {
        try {
            if (collectionManager.collectionSize() == 0) {
                throw new MyNullPointerException("Ошибка: коллекция пуста");
            }
            List<Object> list = new ArrayList<>();
            list.add("Тип коллекции: LinkedHashSet" + "\n" +
                    "Дата инициализации:" + collectionManager.getCreationCollectionDate() + "\n" +
                    "Количество элементов: " + collectionManager.collectionSize());
            return list;
        } catch (MyNullPointerException e) {
            List<Object> list = new ArrayList<>();
            list.add(e.getMessage());
            return list;
        }
    }
}