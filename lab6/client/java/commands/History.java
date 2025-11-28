package commands;

import utility.ConsoleManager;

/**
 * Команда для вывода истории выполненных команд.
 * Класс наследует абстрактный класс Command и реализует метод execute().
 */
public class History extends Command {

    /**
     * Конструктор класса History.
     *
     * @param consoleManager менеджер консоли, который используется для взаимодействия с пользователем.
     */
    public History(ConsoleManager consoleManager) {
        super(consoleManager);
    }
}