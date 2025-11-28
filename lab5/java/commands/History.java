package commands;
import utility.*;

public class History extends Command{
    public History(CollectionManager collectionManager, CommandManager commandManager, String command, ConsoleManager consoleManager) {
        super(collectionManager, commandManager, consoleManager);
        commandManager.put(command);
    }

    @Override
    public void execute() {
        System.out.println("Список команд:");
        for (String cmd : commandManager.getQueue()) { // Используем геттер из CommandManager
            System.out.println(cmd);
        }
    }
}