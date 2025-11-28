package commands;

import asks.AskerAddIfMax;
import asks.AskerAddIfMin;
import exceptions.MyNullPointerException;
import models.Coordinates;
import models.LabWork;
import models.Location;
import models.Person;
import utility.ConsoleManager;

/**
 * Команда для добавления нового элемента в коллекцию, если его значение меньше минимального элемента в коллекции.
 * Класс наследует абстрактный класс Command и реализует метод execute().
 */
public class AddIfMin extends Command {

    /**
     * Конструктор класса AddIfMin.
     *
     * @param consoleManager менеджер консоли, который используется для взаимодействия с пользователем.
     */
    public AddIfMin(ConsoleManager consoleManager) {
        super(consoleManager);
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
    public LabWork execute() {
        AskerAddIfMin asker = new AskerAddIfMin(consoleManager);
        asker.ask();
        LabWork labWork = new LabWork(asker.getId(), asker.getName(), new Coordinates(asker.getX(), asker.getY()),
                asker.getMinimalPoint(), asker.getDifficulty(), new Person(asker.getPeopleName(), asker.getHeight(),
                asker.getEyeColor(), asker.getHairColor(), asker.getCountry(),
                new Location(asker.getX1(), asker.getY1(), asker.getZ1())));
        return labWork;

    }
}