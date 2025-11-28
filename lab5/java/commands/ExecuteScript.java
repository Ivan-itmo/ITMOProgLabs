package commands;

import asks.AskerFileNameScript;
import filesutility.*;
import utility.*;

import java.util.Objects;

public class ExecuteScript extends Command {

    public ExecuteScript(CollectionManager collectionManager, CommandManager commandManager, String command, ConsoleManager consoleManager) {
        super(collectionManager, commandManager, consoleManager);
        commandManager.put(command); // Регистрация команды в менеджере
    }

    @Override
    public void execute() {
        // Запрашиваем имя файла скрипта у пользователя
        AskerFileNameScript asker = new AskerFileNameScript(consoleManager);
        asker.ask();
        String fileName = asker.getFilename(); // Получаем имя файла

        // Считываем команды из файла
        ScriptRead scriptRead = new ScriptRead();
        scriptRead.readCommandsFromFile(fileName, commandManager.commandList, commandManager.counterOfCommands);

        // Переключаем режим консоли на выполнение скрипта
        ConsoleManager.mode = 1;
        int f = 0;

        // Выполняем команды из списка
        for (CommandFromScript entry : commandManager.commandList) {
            if (entry.getIdCommand() == commandManager.counterOfCommands) {
                System.out.println("ID команды: " + entry.getIdCommand());
                if (Objects.equals(entry.getCommand(), "execute_script")){
                    f += 1;
                    break;
                }
                consoleManager.execute(entry.getCommand()); // Выполнение команды
                commandManager.counterOfCommands++; // Увеличиваем счетчик команд
            }
        }
        if (f == 1){
            consoleManager.execute("execute_script"); // Выполнение команды
            commandManager.counterOfCommands++; // Увеличиваем счетчик команд
        }

        // Возвращаем режим консоли в нормальный
        ConsoleManager.mode = 0;
    }
}