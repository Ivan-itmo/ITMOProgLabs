package commands;
import exceptions.MyNullPointerException;
import utility.*;

public class SumOfMinimalPoint extends Command{
    public SumOfMinimalPoint(CollectionManager collectionManager, CommandManager commandManager, String command, ConsoleManager consoleManager) {
        super(collectionManager, commandManager, consoleManager);
        commandManager.put(command);
    }


    @Override
    public void execute() {
        try {
            if (collectionManager.collectionSize() == 0) {
                throw new MyNullPointerException("Ошибка: коллекция пуста");
            }
            System.out.println("Сумма значений: " + collectionManager.collectionSum());
        } catch (MyNullPointerException e) {
            System.out.println(e.getMessage());
        }
    }
}