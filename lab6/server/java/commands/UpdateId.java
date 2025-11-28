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
 * Команда для обновления элемента коллекции по заданному идентификатору.
 * Класс наследует абстрактный класс Command и реализует метод execute().
 */
public class UpdateId extends Command {

    /**
     * Конструктор класса UpdateId.
     *
     * @param collectionManager менеджер коллекции, с которым будет работать команда.
     * @param commandManager менеджер команд, который управляет регистрацией и выполнением команд.
     * @param command название команды, которое будет зарегистрировано в менеджере команд.
     * @param serverCommand менеджер консоли, который используется для взаимодействия с пользователем.
     */
    public UpdateId(CollectionManager collectionManager, CommandManager commandManager, String command, List<Object> args, ServerCommand serverCommand) {
        super(collectionManager, commandManager, args, serverCommand);
        commandManager.put(command); // Регистрация команды в менеджере
    }

    /**
     * Метод execute() реализует логику команды update_id.
     * Обновляет элемент коллекции по заданному идентификатору на основе введенных пользователем данных.
     * Если коллекция пуста, выбрасывается исключение MyNullPointerException.
     */
    @Override
    public List<Object> execute() {
        try {
            if (collectionManager.collectionSize() == 0) {
                throw new MyNullPointerException("Ошибка: коллекция пуста");
            }
            //AskerUpdateId asker = new AskerUpdateId(consoleManager);
            //asker.ask();
            Integer targetId = (Integer) args.get(0);
            LabWork labWork = (LabWork) args.get(1);
            collectionManager.updateLabWork(targetId, labWork);
            IdGenerator.generateId(labWork.getId(), labWork);
            List<Object> result = new ArrayList<>();
            result.add("Команда обновления по id выполнена");
            return result;
        } catch (MyNullPointerException e) {
            List<Object> list = new ArrayList<>();
            list.add(e.getMessage());
            return list;
        }
    }
}