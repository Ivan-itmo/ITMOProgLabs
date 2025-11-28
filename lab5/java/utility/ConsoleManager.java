package utility;

import commands.*;
import exceptions.WrongCommandException;

import java.util.Scanner;

/**
 * Класс манагера консоли, то есть всякие вводы и выводы команд
 */
public class ConsoleManager {
    public static int mode = 0;
    CollectionManager collectionManager;
    CommandManager commandManager;
    public ConsoleManager(CollectionManager collectionManager, CommandManager commandManager) {
        this.collectionManager = collectionManager;
        this.commandManager = commandManager;
    }

    public void execute(String input) {
                try {
                    switch (input) {
                        case "help":
                            (new Help(collectionManager, commandManager, input, this)).execute();
                            break;
                        case "info":
                            (new Info(collectionManager, commandManager, input, this)).execute();
                            break;
                        case "show":
                            (new Show(collectionManager, commandManager, input, this)).execute();
                            break;
                        case "add":
                            (new Add(collectionManager, commandManager, input, this)).execute();
                            break;
                        case "update id":
                            (new UpdateId(collectionManager, commandManager, input, this)).execute();
                            break;
                        case "remove_by_id":
                            (new RemoveById(collectionManager, commandManager, input, this)).execute();
                            break;
                        case "clear":
                            (new Clear(collectionManager, commandManager, input, this)).execute();
                            break;
                        case "history":
                            (new History(collectionManager, commandManager, input, this)).execute();
                            break;
                        case "remove_any_by_difficulty":
                            (new RemoveAnyByDifficulty(collectionManager, commandManager, input, this)).execute();
                            break;
                        case "sum_of_minimal_point":
                            (new SumOfMinimalPoint(collectionManager, commandManager, input, this)).execute();
                            break;
                        case "add_if_max":
                            (new AddIfMax(collectionManager, commandManager, input, this)).execute();
                            break;
                        case "add_if_min":
                            (new AddIfMin(collectionManager, commandManager, input, this)).execute();
                            break;
                        case "max_by_coordinates":
                            (new MaxByCoordinates(collectionManager, commandManager, input, this)).execute();
                            break;
                        case "save":
                            (new Save(collectionManager, commandManager, input, this)).execute();
                            break;
                        case "execute_script":
                            (new ExecuteScript(collectionManager, commandManager, input, this)).execute();
                            break;
                        default:
                            throw new WrongCommandException("Ошибка: неизвестная команда '" + input + "'");
                    }
                } catch (WrongCommandException e) {
                    System.out.println(e.getMessage());
                    if (mode == 1){
                        System.exit(1);
                    }
                }


    }

    public String input(String command) {
        if (mode == 0) {
            Scanner scanner = new Scanner(System.in);
            return scanner.nextLine();
        } else{
            return commandManager.getNext(command);
        }
    }
}
