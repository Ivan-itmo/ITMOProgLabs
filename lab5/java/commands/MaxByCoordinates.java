package commands;
import exceptions.MyNullPointerException;
import models.Coordinates;
import models.LabWork;
import utility.CollectionManager;
import utility.CommandManager;
import utility.ConsoleManager;

public class MaxByCoordinates extends Command {
    public MaxByCoordinates(CollectionManager collectionManager, CommandManager commandManager, String command, ConsoleManager consoleManager) {
        super(collectionManager, commandManager, consoleManager);
        commandManager.put(command);
    }

    @Override
    public void execute() {
        try {
            if (collectionManager.collectionSize() == 0) {
                throw new MyNullPointerException("Ошибка: коллекция пуста");
            }
            Coordinates maxCoordinates = collectionManager.getMaxCoordinates(); // Получаем максимальные координаты
            if (maxCoordinates != null) {
                // Проходим по всем LabWork и выводим те, чьи координаты равны максимальным
                for (LabWork labWork : collectionManager.getCollection()) {
                    if (labWork.getCoordinates().compareTo(maxCoordinates) == 0) {
                        System.out.println(labWork);
                    }
                }
            }
        } catch (MyNullPointerException e) {
            System.out.println(e.getMessage());
        }
    }
}
