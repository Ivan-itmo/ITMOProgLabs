package commands;

import asks.AskerRemoveAnyByDifficulty;
import exceptions.MyNullPointerException;
import utility.CollectionManager;
import utility.CommandManager;
import utility.ConsoleManager;

public class RemoveAnyByDifficulty extends Command {
    public RemoveAnyByDifficulty(CollectionManager collectionManager, CommandManager commandManager, String command, ConsoleManager consoleManager) {
        super(collectionManager, commandManager, consoleManager);
        commandManager.put(command);
    }

    @Override
    public void execute() {
        try {
            if (collectionManager.collectionSize() == 0) {
                throw new MyNullPointerException("Ошибка: коллекция пуста");
            }
            AskerRemoveAnyByDifficulty asker = new AskerRemoveAnyByDifficulty(consoleManager);
            asker.ask();
            collectionManager.removeanybydifficultyLabWork(asker.getDifficulty());
        } catch (MyNullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

}