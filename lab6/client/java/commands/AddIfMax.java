package commands;

import asks.AskerAdd;
import asks.AskerAddIfMax;
import exceptions.MyNullPointerException;
import models.Coordinates;
import models.LabWork;
import models.Location;
import models.Person;
import utility.ConsoleManager;


/**
 * Команда для добавления нового элемента в коллекцию, если его значение превышает максимальный элемент в коллекции.
 * Класс наследует абстрактный класс Command и реализует метод execute().
 */
public class AddIfMax extends Command {

    /**
     * Конструктор класса AddIfMax.
     *
     * @param consoleManager менеджер консоли, который используется для взаимодействия с пользователем.
     */
    public AddIfMax(ConsoleManager consoleManager) {
        super(consoleManager);
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
    public LabWork execute() {
        AskerAddIfMax asker = new AskerAddIfMax(consoleManager);
            asker.ask();
            LabWork labWork = new LabWork(asker.getId(), asker.getName(), new Coordinates(asker.getX(), asker.getY()),
                    asker.getMinimalPoint(), asker.getDifficulty(), new Person(asker.getPeopleName(), asker.getHeight(),
                    asker.getEyeColor(), asker.getHairColor(), asker.getCountry(),
                    new Location(asker.getX1(), asker.getY1(), asker.getZ1())));
            return labWork;

    }
}