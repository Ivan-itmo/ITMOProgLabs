package commands;

import utility.ConsoleManager;

/**
 * Команда для вывода всех элементов коллекции.
 * Класс наследует абстрактный класс Command и реализует метод execute().
 * Выводит все элементы коллекции в строковом представлении.
 */
public class Show extends Command {

    /**
     * Конструктор класса Show.
     *
     * @param consoleManager менеджер консоли, который используется для взаимодействия с пользователем.
     */
    public Show(ConsoleManager consoleManager) {
        super(consoleManager);
    }


}