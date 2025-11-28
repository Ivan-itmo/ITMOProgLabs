package commands;

import asks.AskerAddIfMin;
import exceptions.MyNullPointerException;
import models.Coordinates;
import models.LabWork;
import models.Location;
import models.Person;
import utility.CollectionManager;
import utility.CommandManager;
import utility.ConsoleManager;
import utility.IdGenerator;

public class AddIfMin extends Command {
    public AddIfMin(CollectionManager collectionManager, CommandManager commandManager, String command, ConsoleManager consoleManager) {
        super(collectionManager, commandManager, consoleManager);
        commandManager.put(command);
    }

    @Override
    public void execute() {
        try {
            if (collectionManager.collectionSize() == 0) {
                throw new MyNullPointerException("Ошибка: коллекция пуста");
            }
            AskerAddIfMin asker = new AskerAddIfMin(consoleManager);
            asker.ask();
            LabWork minLabWork = new LabWork(asker.getName(), new Coordinates(asker.getX(), asker.getY()),
                    asker.getMinimalPoint(), asker.getDifficulty(), new Person(asker.getPeopleName(), asker.getHeight(),
                    asker.getEyeColor(), asker.getHairColor(), asker.getCountry(),
                    new Location(asker.getX1(), asker.getY1(), asker.getZ1())));
            if (minLabWork.compareTo(collectionManager.getMinElement()) < 0) {
                collectionManager.addLabWork(minLabWork);
            } else {
                IdGenerator.setId(IdGenerator.getId() - 1);
            }
        } catch (MyNullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

}
