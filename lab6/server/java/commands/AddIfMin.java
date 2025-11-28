package commands;

//import asks.*;
import exceptions.MyNullPointerException;
import models.LabWork;
import serverutility.ServerCommand;
import serverutility.ServerAnswers;
import utility.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Команда для добавления нового элемента в коллекцию, если его значение меньше минимального элемента в коллекции.
 * Класс наследует абстрактный класс Command и реализует метод execute().
 */
public class AddIfMin extends Command {

    /**
     * Конструктор класса AddIfMin.
     *
     * @param collectionManager менеджер коллекции, с которым будет работать команда.
     * @param commandManager менеджер команд, который управляет регистрацией команд.
     * @param command название команды, которое будет зарегистрировано в менеджере команд.
     * @param serverCommand менеджер консоли, который используется для взаимодействия с пользователем.
     */
    public AddIfMin(CollectionManager collectionManager, CommandManager commandManager, String command, List<Object> args, ServerCommand serverCommand) {
        super(collectionManager, commandManager, args, serverCommand);
        commandManager.put(command);
    }

    /**
     * Метод execute() реализует логику команды добавления нового элемента в коллекцию,
     * если его значение меньше минимального элемента в коллекции.
     * Если коллекция пуста, выбрасывается исключение MyNullPointerException.
     * Использует AskerAddIfMin для запроса данных у пользователя и создает новый объект LabWork,
     * который затем сравнивается с минимальным элементом коллекции.
     * Если новый элемент меньше минимального, он добавляется в коллекцию.
     */
    @Override
    public List<Object> execute() {
        try {
            if (collectionManager.collectionSize() == 0) {
                throw new MyNullPointerException("Ошибка: коллекция пуста");
            }
            //AskerAddIfMin asker = new AskerAddIfMin(consoleManager);
            //asker.ask();
            LabWork minLabWork = (LabWork) args.get(0);
            if (minLabWork.compareTo(collectionManager.getMinElement()) < 0) {
                IdGenerator.generateId(minLabWork.getId(), minLabWork);
                collectionManager.addLabWork(minLabWork);
            }
            List<Object> result = new ArrayList<>();
            result.add("Команда вставки при минимуме выполнена");
            return result;
        } catch (MyNullPointerException e) {
            List<Object> list = new ArrayList<>();
            list.add(e.getMessage());
            return list;
        }
    }
}