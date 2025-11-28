package commands;

import utility.CollectionManager;
import utility.CommandManager;
import utility.ConsoleManager;

public abstract class Command {
    public CollectionManager collectionManager;
    public CommandManager commandManager;
    public ConsoleManager consoleManager;


    public Command(CollectionManager collectionManager, CommandManager commandManager, ConsoleManager consoleManager) {
        this.collectionManager = collectionManager;
        this.commandManager = commandManager;
        this.consoleManager = consoleManager;
    }

    public abstract void execute();
}
