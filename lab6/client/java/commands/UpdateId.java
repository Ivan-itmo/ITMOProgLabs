package commands;

import asks.AskerUpdateId;
import models.Coordinates;
import models.LabWork;
import models.Location;
import models.Person;

import utility.ConsoleManager;

/**
 * Команда для обновления элемента коллекции по заданному идентификатору.
 * Класс наследует абстрактный класс Command и реализует метод execute().
 */
public class UpdateId extends Command {

    /**
     * Конструктор класса UpdateId.
     *
     * @param consoleManager менеджер консоли, который используется для взаимодействия с пользователем.
     */
    public UpdateId(ConsoleManager consoleManager) {
        super(consoleManager);
    }

    /**
     * Метод execute() реализует логику команды update_id.
     * Обновляет элемент коллекции по заданному идентификатору на основе введенных пользователем данных.
     * Если коллекция пуста, выбрасывается исключение MyNullPointerException.
     */
    @Override
    public Object[] execute() {
            AskerUpdateId asker = new AskerUpdateId(consoleManager);
            asker.ask();
            Integer targetId = asker.getUpdateId();
            LabWork labWork = new LabWork(asker.getId(), asker.getName(),
                    new Coordinates(asker.getX(), asker.getY()),
                    asker.getMinimalPoint(), asker.getDifficulty(), new Person(asker.getPeopleName(), asker.getHeight(),
                    asker.getEyeColor(), asker.getHairColor(), asker.getCountry(),
                    new Location(asker.getX1(), asker.getY1(), asker.getZ1())));
            Object[] args = {targetId, labWork};
            return args;
    }
}