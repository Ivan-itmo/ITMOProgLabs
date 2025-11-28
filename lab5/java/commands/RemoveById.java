package commands;

import asks.AskerRemoveById;
import exceptions.MyNullPointerException;
import utility.CollectionManager;
import utility.CommandManager;
import utility.ConsoleManager;

public class RemoveById extends Command {
    public RemoveById(CollectionManager collectionManager, CommandManager commandManager, String command, ConsoleManager consoleManager) {
        super(collectionManager, commandManager, consoleManager);
        commandManager.put(command);
    }

    @Override
    public void execute() {
        try {
            if (collectionManager.collectionSize() == 0) {
                throw new MyNullPointerException("Ошибка: коллекция пуста");
            }
            AskerRemoveById asker = new AskerRemoveById(consoleManager);
            asker.ask();
            collectionManager.removebyidLabWork(asker.getId());
        } catch (MyNullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

}