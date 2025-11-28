package commands;
import exceptions.*;
import utility.CollectionManager;
import utility.CommandManager;
import utility.ConsoleManager;

public class Show extends Command {
    public Show(CollectionManager collectionManager, CommandManager commandManager, String command, ConsoleManager consoleManager) {
        super(collectionManager, commandManager, consoleManager);
        commandManager.put(command);
    }

    @Override
    public void execute() {
        try {
            if (collectionManager.collectionSize() == 0){
                throw new MyNullPointerException("Ошибка: коллекция пуста");
            }
            System.out.println(collectionManager.collectionShow());
        } catch (MyNullPointerException e) {
            System.out.println(e.getMessage());
        }

    }
}
