package commands;
import utility.*;

public class Help extends Command{
    public Help(CollectionManager collectionManager, CommandManager commandManager, String command, ConsoleManager consoleManager) {
        super(collectionManager, commandManager, consoleManager);
        commandManager.put(command);
    }


    @Override
    public void execute() {
        for (var entry : commandManager.getCommands().entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
    }
}