package commands;

import utility.CommandManager;
import utility.ConsoleManager;

/**
 * Абстрактный класс, представляющий команду.
 * Класс служит базовым классом для всех команд и определяет общую структуру и поведение команд.
 */
public abstract class Command {


    /** Менеджер консоли, который используется для взаимодействия с пользователем. */
    public ConsoleManager consoleManager;
    public CommandManager commandManager;

    /**
     * Конструктор класса Command.
     *
     * @param consoleManager менеджер консоли, который используется для взаимодействия с пользователем.
     */
    public Command(CommandManager commandManager, ConsoleManager consoleManager) {
        this.consoleManager = consoleManager;
        this.commandManager = commandManager;
    }

    /**
     * Абстрактный метод execute(), который должен быть реализован в подклассах.
     * Определяет основную логику выполнения команды.
     */
    public Object execute() {
        return null;
    }
}