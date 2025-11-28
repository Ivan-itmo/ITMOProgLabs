package utility;

import exceptions.WrongCommandException;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import commands.*;

/**
 * Класс для управления вводом и выводом команд в консоли.
 * Обеспечивает выполнение команд и обработку пользовательского ввода.
 */
public class ConsoleManager {
    /**
     * Режим работы консоли:
     * 0 - интерактивный режим (ввод с клавиатуры),
     * 1 - режим выполнения скрипта.
     */
    public static int mode = 0;
    private CommandManager commandManager;
    public ConsoleManager(CommandManager commandManager) {
        this.commandManager = commandManager;
    }


    List<List<Object>> argCom = new ArrayList<>();
    public List<List<Object>> getArgsCom(){
        return argCom;
    }


    /**
     * Выполняет команду, соответствующую переданной строке.
     *
     * @param input Строка, содержащая команду для выполнения.
     * @throws WrongCommandException Если команда неизвестна.
     */
    public List<Object> inputCommand(String input) {
        List<Object> commandAndArgs = new ArrayList<>();
        try {
            switch (input) {
                case "help":
                    commandAndArgs.add(new Help(commandManager, this));
                    break;
                case "info":
                    commandAndArgs.add(new Info(commandManager, this));
                    break;
                case "show":
                    commandAndArgs.add(new Show(commandManager, this));
                    break;
                case "add":
                    Add add = new Add(commandManager, this);
                    commandAndArgs.add(add);
                    commandAndArgs.add(add.execute());
                    break;
                case "update id":
                    UpdateId updateId = new UpdateId(commandManager, this);
                    commandAndArgs.add(updateId);
                    Object[] args = updateId.execute();
                    commandAndArgs.add(args[0]);
                    commandAndArgs.add(args[1]);
                    break;
                case "remove_by_id":
                    RemoveById removeById = new RemoveById(commandManager, this);
                    commandAndArgs.add(removeById);
                    commandAndArgs.add(removeById.execute());
                    break;
                case "clear":
                    commandAndArgs.add(new Clear(commandManager, this));
                    break;
                case "history":
                    commandAndArgs.add(new History(commandManager, this));
                    break;
                case "remove_any_by_difficulty":
                    RemoveAnyByDifficulty removeAnyByDifficulty = new RemoveAnyByDifficulty(commandManager, this);
                    commandAndArgs.add(removeAnyByDifficulty);
                    commandAndArgs.add(removeAnyByDifficulty.execute());
                    break;
                case "sum_of_minimal_point":
                    commandAndArgs.add(new SumOfMinimalPoint(commandManager, this));
                    break;
                case "add_if_max":
                    AddIfMax addIfMax = new AddIfMax(commandManager, this);
                    commandAndArgs.add(addIfMax);
                    commandAndArgs.add(addIfMax.execute());
                    break;
                case "add_if_min":
                    AddIfMin addIfMin = new AddIfMin(commandManager, this);
                    commandAndArgs.add(addIfMin);
                    commandAndArgs.add(addIfMin.execute());
                    break;
                case "max_by_coordinates":
                    commandAndArgs.add(new MaxByCoordinates(commandManager, this));
                    break;
                case "execute_script":
                    ExecuteScript executeScript = new ExecuteScript(commandManager, this);
                    argCom = executeScript.execute();
                    break;
                default:
                    throw new WrongCommandException("Ошибка: неизвестная команда '" + input + "'");
            }
        } catch (WrongCommandException e) {
            System.out.println(e.getMessage());
        }
        return commandAndArgs;
    }

    /**
     * Получает ввод от пользователя или из скрипта в зависимости от режима.
     *
     * @return Введенная строка.
     */


    public String inputArgs(String command) {
        if (mode == 0) {
            Scanner scanner = new Scanner(System.in);
            return scanner.nextLine();
        } else {
            return commandManager.getNext(command);
        }
    }



}