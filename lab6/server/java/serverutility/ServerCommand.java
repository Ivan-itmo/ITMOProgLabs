package serverutility;

import exceptions.WrongCommandException;
import commands.*;
import utility.CollectionManager;
import utility.CommandManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Класс для управления вводом и выводом команд в консоли.
 * Обеспечивает выполнение команд и обработку пользовательского ввода.
 */
public class ServerCommand {
    /**
     * Режим работы консоли:
     * 0 - интерактивный режим (ввод с клавиатуры),
     * 1 - режим выполнения скрипта.
     */
    public static int mode = 0;
    private List<String> arguments = new ArrayList<>();
    public static int point = 0;
    private CollectionManager collectionManager;
    private CommandManager commandManager;
    private ServerAnswers serverAnswers;

    /**
     * Конструктор для создания объекта {@link ServerCommand}.
     *
     * @param collectionManager Менеджер коллекции, используемый для выполнения команд.
     * @param commandManager    Менеджер команд, используемый для управления командами.
     */
    public ServerCommand(CollectionManager collectionManager, CommandManager commandManager) {
        this.collectionManager = collectionManager;
        this.commandManager = commandManager;
    }

    /**
     * Выполняет команду, соответствующую переданной строке.
     *
     * @param input Строка, содержащая команду для выполнения.
     * @throws WrongCommandException Если команда неизвестна.
     */
    public List<Object> execute(String input, List<Object> args) throws WrongCommandException {
        try {
            switch (input) {
                case "save":
                    return new Save(collectionManager, commandManager, input, args, this).execute();
                case "help":
                    return new Help(collectionManager, commandManager, input, args, this).execute();
                case "info":
                    return new Info(collectionManager, commandManager, input, args, this).execute();
                case "show":
                    return new Show(collectionManager, commandManager, input, args, this).execute();
                case "add":
                    return new Add(collectionManager, commandManager, input, args, this).execute();
                case "updateid":
                    return new UpdateId(collectionManager, commandManager, input, args, this).execute();
                case "removebyid":
                    return new RemoveById(collectionManager, commandManager, input, args, this).execute();
                case "clear":
                    return new Clear(collectionManager, commandManager, input, args, this).execute();
                case "history":
                    return new History(collectionManager, commandManager, input, args, this).execute();
                case "removeanybydifficulty":
                    return new RemoveAnyByDifficulty(collectionManager, commandManager, input, args, this).execute();
                case "sumofminimalpoint":
                    return new SumOfMinimalPoint(collectionManager, commandManager, input, args, this).execute();
                case "addifmax":
                    return new AddIfMax(collectionManager, commandManager, input, args, this).execute();
                case "addifmin":
                    return new AddIfMin(collectionManager, commandManager, input, args, this).execute();
                case "maxbycoordinates":
                    return new MaxByCoordinates(collectionManager, commandManager, input, args, this).execute();
                default:
                    throw new WrongCommandException("Ошибка: неизвестная команда '" + input + "'");
            }
        } catch (WrongCommandException e) {
            if (mode == 1) {
                System.exit(1);
            }
            List<Object> result = new ArrayList<>();
            result.add(e.getMessage());
            return result;
        }
    }

    /**
     * Получает ввод от пользователя или из скрипта в зависимости от режима.
     *
     * @param command Команда, для которой запрашивается ввод.
     * @return Введенная строка.
     */
    public String input(String command) {
        if (mode == 0) {
            Scanner scanner = new Scanner(System.in);
            return scanner.nextLine();
        } else {
            return commandManager.getNext(command);
        }
    }
}