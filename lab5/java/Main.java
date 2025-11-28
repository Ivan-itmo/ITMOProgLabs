import utility.*;
import java.util.Scanner;

/**
 * Главный класс, в нем создаются все манагеры, для дальнейшей работы с ними, а также есть цикл while, чтобы работать до команды выхода
 */
public class Main {
    public static void main(String[] args) {
        CollectionManager collectionManager = new CollectionManager();
        CommandManager commandManager = new CommandManager();
        ConsoleManager consoleManager = new ConsoleManager(collectionManager, commandManager);

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        while (!input.equals("exit") && ConsoleManager.mode == 0){
            consoleManager.execute(input);
            input = scanner.nextLine();
        }
    }
}