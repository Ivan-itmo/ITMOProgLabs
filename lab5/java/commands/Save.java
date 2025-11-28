package commands;

import asks.AskerFileNameSave;
import filesutility.JsonFileWriter;
import models.LabWork;
import utility.CollectionManager;
import utility.CommandManager;
import utility.ConsoleManager;

import java.util.ArrayList;
import java.util.List;

public class Save extends Command {
    public Save(CollectionManager collectionManager, CommandManager commandManager, String command, ConsoleManager consoleManager) {
        super(collectionManager, commandManager, consoleManager);
        commandManager.put(command);
    }


    @Override
    public void execute() {
        AskerFileNameSave asker = new AskerFileNameSave(consoleManager);
        asker.ask();
        String fileName = asker.getFilename(); // Получаем имя файла

        // Преобразуем LinkedHashSet в List
        List<LabWork> labWorksList = new ArrayList<>(collectionManager.getCollection());

        JsonFileWriter jsonFileWriter = new JsonFileWriter();
        jsonFileWriter.writeJsonToFile(fileName, labWorksList);
    }
}