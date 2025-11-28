package commands;

import utility.ConsoleManager;

/**
 * Команда для вывода списка доступных команд и их описаний.
 * Класс наследует абстрактный класс Command и реализует метод execute().
 */
public class Help extends Command {

    /**
     * Конструктор класса Help.
     *
     * @param consoleManager менеджер консоли, который используется для взаимодействия с пользователем.
     */
    public Help(ConsoleManager consoleManager) {
        super(consoleManager);
    }

}