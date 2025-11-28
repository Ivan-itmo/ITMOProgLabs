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
 * Команда для добавления нового элемента в коллекцию, если его значение превышает максимальный элемент в коллекции.
 * Класс наследует абстрактный класс Command и реализует метод execute().
 */
public class AddIfMax extends Command {

    /**
     * Конструктор класса AddIfMax.
     *
     * @param collectionManager менеджер коллекции, с которым будет работать команда.
     * @param commandManager менеджер команд, который управляет регистрацией команд.
     * @param command название команды, которое будет зарегистрировано в менеджере команд.
     * @param serverCommand менеджер консоли, который используется для взаимодействия с пользователем.
     */
    public AddIfMax(CollectionManager collectionManager, CommandManager commandManager, String command, List<Object> args, ServerCommand serverCommand) {
        super(collectionManager, commandManager, args, serverCommand);
        commandManager.put(command);
    }

    /**
     * Метод execute() реализует логику команды добавления нового элемента в коллекцию,
     * если его значение превышает максимальный элемент в коллекции.
     * Если коллекция пуста, выбрасывается исключение MyNullPointerException.
     * Использует AskerAddIfMax для запроса данных у пользователя и создает новый объект LabWork,
     * который затем сравнивается с максимальным элементом коллекции.
     * Если новый элемент больше максимального, он добавляется в коллекцию.
     */
    @Override
    public List<Object> execute() {
        try {
            if (collectionManager.collectionSize() == 0) {
                throw new MyNullPointerException("Ошибка: коллекция пуста");
            }
            //AskerAddIfMax asker = new AskerAddIfMax(consoleManager);
            //asker.ask();
            LabWork maxLabWork = (LabWork) args.get(0);
            if (maxLabWork.compareTo(collectionManager.getMaxElement()) > 0) {
                IdGenerator.generateId(maxLabWork.getId(), maxLabWork);
                collectionManager.addLabWork(maxLabWork);
            }
            List<Object> result = new ArrayList<>();
            result.add("Команда вставки при максимуме выполнена");
            return result;
        } catch (MyNullPointerException e) {
            List<Object> list = new ArrayList<>();
            list.add(e.getMessage());
            return list;
        }
    }
}