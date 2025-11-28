package commands;
import exceptions.*;
import utility.*;

public class Info extends Command {
    public Info(CollectionManager collectionManager, CommandManager commandManager, String command, ConsoleManager consoleManager) {
        super(collectionManager, commandManager, consoleManager);
        commandManager.put(command);
    }

    @Override
    public void execute() {
        try {
            if (collectionManager.collectionSize() == 0){
                throw new MyNullPointerException("Ошибка: коллекция пуста");
            }
            System.out.println("Тип коллекции: LinkedHashSet" + "\n" +
                    "Дата инициализации:" + collectionManager.getCreationCollectionDate() + "\n" +
                    "Количество элементов: " + collectionManager.collectionSize());
        } catch (MyNullPointerException e) {
            System.out.println(e.getMessage());
        }
    }
}