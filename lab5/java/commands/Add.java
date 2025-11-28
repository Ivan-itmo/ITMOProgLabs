package commands;

import asks.AskerAdd;
import models.Coordinates;
import models.LabWork;
import models.Location;
import models.Person;
import utility.CollectionManager;
import utility.CommandManager;
import utility.ConsoleManager;

public class Add extends Command {
    public Add(CollectionManager collectionManager, CommandManager commandManager, String command, ConsoleManager consoleManager) {
        super(collectionManager, commandManager, consoleManager);
        commandManager.put(command);
    }

    @Override
    public void execute() {
        AskerAdd asker = new AskerAdd(consoleManager);
        asker.ask();
        collectionManager.addLabWork(new LabWork(asker.getName(), new Coordinates(asker.getX(), asker.getY()),
                asker.getMinimalPoint(), asker.getDifficulty(), new Person(asker.getPeopleName(), asker.getHeight(),
                asker.getEyeColor(), asker.getHairColor(), asker.getCountry(),
                new Location(asker.getX1(), asker.getY1(), asker.getZ1()))));
    }

}
