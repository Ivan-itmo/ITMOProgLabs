package commands;

import utility.ConsoleManager;

/**
 * Команда для поиска и вывода элементов коллекции с максимальными координатами.
 * Класс наследует абстрактный класс Command и реализует метод execute().
 */
public class MaxByCoordinates extends Command {

    /**
     * Конструктор класса MaxByCoordinates.
     *
     * @param consoleManager менеджер консоли, который используется для взаимодействия с пользователем.
     */
    public MaxByCoordinates(ConsoleManager consoleManager) {
        super(consoleManager);
    }


}