package commands;

//import asks.*;
import models.LabWork;
import serverutility.ServerCommand;
import serverutility.ServerAnswers;
import utility.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Команда для добавления нового элемента в коллекцию.
 * Класс наследует абстрактный класс Command и реализует метод execute().
 */
public class Add extends Command {

    /**
     * Конструктор класса Add.
     *
     * @param collectionManager менеджер коллекции, с которым будет работать команда.
     * @param commandManager менеджер команд, который управляет регистрацией команд.
     * @param command название команды, которое будет зарегистрировано в менеджере команд.
     * @param serverCommand менеджер консоли, который используется для взаимодействия с пользователем.
     */
    public Add(CollectionManager collectionManager, CommandManager commandManager, String command, List<Object> args, ServerCommand serverCommand) {
        super(collectionManager, commandManager, args, serverCommand);
        commandManager.put(command);
    }

    /**
     * Метод execute() реализует логику команды добавления нового элемента в коллекцию.
     * Использует AskerAdd для запроса данных у пользователя и создает новый объект LabWork,
     * который затем добавляется в коллекцию.
     */
    @Override
    public List<Object> execute() {
        //AskerAdd asker = new AskerAdd(consoleManager);
        //asker.ask();
        LabWork labWork = (LabWork) args.get(0);
        IdGenerator.generateId(labWork.getId(), labWork);
        collectionManager.addLabWork(labWork);
        List<Object> list = new ArrayList<>();
        list.add("Команда добавления выполнена");
        return list;
    }
}