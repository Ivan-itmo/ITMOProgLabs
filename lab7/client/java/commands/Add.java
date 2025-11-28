package commands;

import asks.AskerValidatorArgsAdd;
import models.Coordinates;
import models.LabWork;
import models.Location;
import models.Person;
import utility.CommandManager;
import utility.ConsoleManager;

/**
 * Команда для добавления нового элемента в коллекцию.
 * Класс наследует абстрактный класс Command и реализует метод execute().
 */
public class Add extends Command {

    /**
     * Конструктор класса Add.
     *
     * @param consoleManager менеджер консоли, который используется для взаимодействия с пользователем.
     */
    public Add(CommandManager commandManager, ConsoleManager consoleManager) {
        super(commandManager, consoleManager);
    }

    /**
     * Метод execute() реализует логику команды добавления нового элемента в коллекцию.
     * Использует AskerAdd для запроса данных у пользователя и создает новый объект LabWork,
     * который затем добавляется в коллекцию.
     */
    @Override
    public LabWork execute() {
        AskerValidatorArgsAdd asker = new AskerValidatorArgsAdd(consoleManager);
        asker.ask();
        return new LabWork(0, asker.getName(), new Coordinates(asker.getX(), asker.getY()),
                asker.getMinimalPoint(), asker.getDifficulty(), new Person(asker.getPeopleName(), asker.getHeight(),
                asker.getEyeColor(), asker.getHairColor(), asker.getCountry(),
                new Location(asker.getX1(), asker.getY1(), asker.getZ1())));
    }
}