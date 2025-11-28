package commands;

import utility.CollectionManager;
import utility.CommandManager;
import serverutility.ServerCommand;
import serverutility.ServerAnswers;
import java.util.List;
/**
 * Абстрактный класс, представляющий команду.
 * Класс служит базовым классом для всех команд и определяет общую структуру и поведение команд.
 */
public abstract class Command {

    /** Менеджер коллекции, с которым будет работать команда. */
    public CollectionManager collectionManager;

    /** Менеджер команд, который управляет регистрацией и выполнением команд. */
    public CommandManager commandManager;

    /** Менеджер консоли, который используется для взаимодействия с пользователем. */
    public ServerCommand serverCommand;

    public List<Object> args;
    /**
     * Конструктор класса Command.
     *
     * @param collectionManager менеджер коллекции, с которым будет работать команда.
     * @param commandManager менеджер команд, который управляет регистрацией и выполнением команд.
     * @param serverCommand менеджер консоли, который используется для взаимодействия с пользователем.
     */
    public Command(CollectionManager collectionManager, CommandManager commandManager, List<Object> args, ServerCommand serverCommand) {
        this.collectionManager = collectionManager;
        this.commandManager = commandManager;
        this.serverCommand = serverCommand;
        this.args = args;
    }

    /**
     * Абстрактный метод execute(), который должен быть реализован в подклассах.
     * Определяет основную логику выполнения команды.
     */
    public abstract List<Object> execute();
}