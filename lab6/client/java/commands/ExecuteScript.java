package commands;

import asks.AskerFileNameScript;
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
public class ExecuteScript {
    CommandManager commandManager;
    ConsoleManager consoleManager;
    List<Object> argCom = new ArrayList<>();
    /**
     * Конструктор класса ExecuteScript.
     *
     * @param commandManager менеджер команд, который управляет регистрацией и выполнением команд.
     * @param command название команды, которое будет зарегистрировано в менеджере команд.
     * @param consoleManager менеджер консоли, который используется для взаимодействия с пользователем.
     */
    public ExecuteScript(CommandManager commandManager, String command, ConsoleManager consoleManager) {
        this.commandManager = commandManager;
        this.consoleManager = consoleManager;
        commandManager.put(command); // Регистрация команды в менеджере
    }

    /**
     * Метод execute() реализует логику выполнения скрипта из файла.
     * Запрашивает имя файла скрипта у пользователя, проверяет на рекурсию,
     * считывает команды из файла и выполняет их.
     * Если обнаружена рекурсия, выбрасывается исключение RecursionException.
     * После выполнения всех команд возвращает режим консоли в нормальный.
     */
    public List<List<Object>> execute() {
        List<List<Object>> ret = new ArrayList<>();
        AskerFileNameScript asker = new AskerFileNameScript(consoleManager);
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
        // Считываем команды из файла
        ScriptRead scriptRead = new ScriptRead();
        scriptRead.readCommandsFromFile(fileName, commandManager);

        // Переключаем режим консоли на выполнение скрипта
        ConsoleManager.mode = 1;
        int f = 0;

        // Выполняем команды из списка
        for (CommandFromScript entry : commandManager.commandList) {
            if (entry.getIdCommand() == commandManager.counterOfCommands) {
                System.out.println("ID команды: " + entry.getIdCommand());
                if (Objects.equals(entry.getCommand(), "execute_script")) {
                    f += 1;
                    break;
                }
                argCom = consoleManager.execute(entry.getCommand()); // Выполнение команды
                ret.add(argCom);
                commandManager.counterOfCommands++; // Увеличиваем счетчик команд
            }
        }
        if (f == 1) {
            consoleManager.execute("execute_script"); // Выполнение команды
            commandManager.counterOfCommands++; // Увеличиваем счетчик команд
        }


        ConsoleManager.mode = 0;
        return ret;
    }
}