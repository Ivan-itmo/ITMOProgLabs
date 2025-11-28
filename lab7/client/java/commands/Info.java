package commands;

import utility.CommandManager;
import utility.ConsoleManager;

/**
 * Команда для вывода информации о коллекции.
 * Класс наследует абстрактный класс Command и реализует метод execute().
 * Выводит тип коллекции, дату инициализации и количество элементов.
 */
public class Info extends Command {

    /**
     * Конструктор класса Info.
     *
     * @param consoleManager менеджер консоли, который используется для взаимодействия с пользователем.
     */
    public Info(CommandManager commandManager, ConsoleManager consoleManager) {
        super(commandManager, consoleManager);
    }

}