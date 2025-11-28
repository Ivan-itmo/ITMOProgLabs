package commands;
import exceptions.*;
import utility.*;

public class Clear extends Command {
    public Clear(CollectionManager collectionManager, CommandManager commandManager, String command, ConsoleManager consoleManager) {
        super(collectionManager, commandManager, consoleManager);
        commandManager.put(command);
    }

    @Override
    public void execute() {
        try {
            if (collectionManager.collectionSize() == 0) {
                throw new MyNullPointerException("Ошибка: коллекция пуста");
            }
            collectionManager.clearLabWork();
        } catch (MyNullPointerException e) {
            System.out.println(e.getMessage());
        }
    }
}
