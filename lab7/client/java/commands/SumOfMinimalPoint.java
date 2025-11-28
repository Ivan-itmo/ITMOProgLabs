package commands;

import utility.CommandManager;
import utility.ConsoleManager;

/**
 * Команда для вычисления суммы значений поля minimalPoint всех элементов коллекции.
 * Класс наследует абстрактный класс Command и реализует метод execute().
 */
public class SumOfMinimalPoint extends Command {

    /**
     * Конструктор класса SumOfMinimalPoint.
     *
     * @param consoleManager менеджер консоли, который используется для взаимодействия с пользователем.
     */
    public SumOfMinimalPoint(CommandManager commandManager, ConsoleManager consoleManager) {
        super(commandManager, consoleManager);
    }
}