package commands;

import asks.AskerAddIfMax;
import exceptions.MyNullPointerException;
import models.Coordinates;
import models.LabWork;
import models.Location;
import models.Person;
import utility.CollectionManager;
import utility.CommandManager;
import utility.ConsoleManager;
import utility.IdGenerator;

public class AddIfMax extends Command {
    public AddIfMax(CollectionManager collectionManager, CommandManager commandManager, String command, ConsoleManager consoleManager) {
        super(collectionManager, commandManager, consoleManager);
        commandManager.put(command);
    }

    @Override
    public void execute() {
        try {
            if (collectionManager.collectionSize() == 0) {
                throw new MyNullPointerException("Ошибка: коллекция пуста");
            }
            AskerAddIfMax asker = new AskerAddIfMax(consoleManager);
            asker.ask();
            LabWork maxLabWork = new LabWork(asker.getName(), new Coordinates(asker.getX(), asker.getY()),
                    asker.getMinimalPoint(), asker.getDifficulty(), new Person(asker.getPeopleName(), asker.getHeight(),
                    asker.getEyeColor(), asker.getHairColor(), asker.getCountry(),
                    new Location(asker.getX1(), asker.getY1(), asker.getZ1())));
            if (maxLabWork.compareTo(collectionManager.getMaxElement()) > 0) {
                collectionManager.addLabWork(maxLabWork);
            } else {
                IdGenerator.setId(IdGenerator.getId() - 1);
            }
        } catch (MyNullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

}

