package commands;

import filesutility.JsonFileWriter;
import utility.CollectionManager;
import utility.CommandManager;
import java.util.ArrayList;
import java.util.List;
import models.LabWork;
import serverutility.ServerCommand;
import serverutility.ServerAnswers;

/**
 * Команда для сохранения коллекции в файл.
 * Класс наследует абстрактный класс Command и реализует метод execute().
 * Сохраняет текущее состояние коллекции в файл в формате JSON.
 */
public class Save extends Command {

    /**
     * Конструктор класса Save.
     *
     * @param collectionManager менеджер коллекции, с которым будет работать команда.
     * @param commandManager менеджер команд, который управляет регистрацией и выполнением команд.
     * @param command название команды, которое будет зарегистрировано в менеджере команд.
     * @param serverCommand менеджер консоли, который используется для взаимодействия с пользователем.
     */
    public Save(CollectionManager collectionManager, CommandManager commandManager, String command, List<Object> args, ServerCommand serverCommand) {
        super(collectionManager, commandManager, args, serverCommand);
        commandManager.put(command); // Регистрация команды в менеджере
    }

    /**
     * Метод execute() реализует логику команды save.
     * Сохраняет текущее состояние коллекции в файл, указанный в менеджере команд.
     * Использует JsonFileWriter для записи данных в формате JSON.
     */
    @Override
    public List<Object> execute() {
        String fileName = commandManager.getFilePath();
        List<LabWork> labWorksList = new ArrayList<>(collectionManager.getCollection());

        JsonFileWriter jsonFileWriter = new JsonFileWriter();
        jsonFileWriter.writeJsonToFile(fileName, labWorksList);
        List<Object> response = new ArrayList<>();
        response.add("Выполнено");
        return response;
    }
}