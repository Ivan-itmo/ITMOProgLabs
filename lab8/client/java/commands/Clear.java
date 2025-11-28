package commands;


import utility.CommandManager;
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
    public Clear(CommandManager commandManager, ConsoleManager consoleManager) {
        super(commandManager, consoleManager);
    }

}