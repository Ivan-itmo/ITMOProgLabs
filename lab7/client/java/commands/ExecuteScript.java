package commands;

import asks.AskerValidatorArgsFileNameScript;
import exceptions.RecursionException;
import filesutility.CommandFromScript;
import filesutility.ScriptRead;
import utility.CommandManager;
import utility.ConsoleManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Команда для выполнения скрипта из файла.
 * Класс наследует абстрактный класс Command и реализует метод execute().
 * Позволяет выполнять команды, записанные в файле, и обрабатывает рекурсию.
 */
public class ExecuteScript extends Command {
    List<Object> argCom = new ArrayList<>();
    /**
     * Конструктор класса ExecuteScript.
     *
     * @param commandManager менеджер команд, который управляет регистрацией и выполнением команд.
     * @param consoleManager менеджер консоли, который используется для взаимодействия с пользователем.
     */
    public ExecuteScript(CommandManager commandManager, ConsoleManager consoleManager) {
        super(commandManager, consoleManager);
    }

    /**
     * Метод execute() реализует логику выполнения скрипта из файла.
     * Запрашивает имя файла скрипта у пользователя, проверяет на рекурсию,
     * считывает команды из файла и выполняет их.
     * Если обнаружена рекурсия, выбрасывается исключение RecursionException.
     * После выполнения всех команд возвращает режим консоли в нормальный.
     */
    @Override
    public List<List<Object>> execute() {
        List<List<Object>> ret = new ArrayList<>();
        AskerValidatorArgsFileNameScript asker = new AskerValidatorArgsFileNameScript(consoleManager);
        asker.ask();
        String fileName = asker.getFilename(); // Получаем имя файла
        try {
            if (!commandManager.listFilePath.contains(fileName)) {
                commandManager.listFilePath.add(fileName);
            } else {
                throw new RecursionException("Рекурсия зацикленная");
            }
        } catch (RecursionException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }

        ScriptRead scriptRead = new ScriptRead();
        scriptRead.readCommandsFromFile(fileName, commandManager);

        ConsoleManager.mode = 1;
        int f = 0;

        for (CommandFromScript entry : commandManager.commandList) {
            if (entry.getIdCommand() == commandManager.counterOfCommands) {
                System.out.println("ID команды: " + entry.getIdCommand());
                if (Objects.equals(entry.getCommand(), "execute_script")) {
                    f += 1;
                    break;
                }
                argCom = consoleManager.inputCommand(entry.getCommand());
                ret.add(argCom);
                commandManager.counterOfCommands++;
            }
        }
        if (f == 1) {
            consoleManager.inputCommand("execute_script");
            commandManager.counterOfCommands++;
        }


        ConsoleManager.mode = 0;
        return ret;
    }
}