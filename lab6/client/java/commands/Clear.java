package commands;


import utility.ConsoleManager;

/**
 * Команда для очистки коллекции.
 * Класс наследует абстрактный класс Command и реализует метод execute().
 */
public class Clear extends Command {

    /**
     * Конструктор класса Clear.
     *
     * @param consoleManager менеджер консоли, который используется для взаимодействия с пользователем.
     */
    public Clear(ConsoleManager consoleManager) {
        super(consoleManager);
    }

}