package serverutility;

import exceptions.WrongCommandException;
import utility.CollectionManager;
import utility.CommandManager;
import java.util.*;
import java.util.concurrent.*;
import commands.*;
import utility.PasswordManager;

/**
 * Класс {@code ServerCommand} отвечает за выполнение команд на стороне сервера.
 * Он управляет обработкой команд, используя пул потоков для параллельного выполнения,
 * и обеспечивает взаимодействие с менеджерами коллекции и команд.
 *
 * <p>Основные функции:
 * <ul>
 *   <li>Выполнение команд с использованием пула потоков.</li>
 *   <li>Обработка различных типов команд через внутренний класс {@link CommandTask}.</li>
 *   <li>Интеграция с {@link CollectionManager} для управления коллекцией данных.</li>
 *   <li>Интеграция с {@link CommandManager} для управления логикой команд.</li>
 *   <li>Проверка авторизации пользователей через {@link PasswordManager}.</li>
 * </ul></p>
 *
 * @see CollectionManager
 * @see CommandManager
 * @see PasswordManager
 * @see WrongCommandException
 */
public class ServerCommand {
    private final CollectionManager collectionManager;
    private final CommandManager commandManager;
    private final PasswordManager passwordManager;
    private final ForkJoinPool commandPool;

    /**
     * Создает новый экземпляр класса {@code ServerCommand}.
     *
     * @param collectionManager менеджер коллекции, отвечающий за управление данными.
     * @param commandManager менеджер команд, отвечающий за логику выполнения команд.
     * @param passwordManager менеджер паролей, отвечающий за аутентификацию пользователей.
     * @param parallelism уровень параллелизма для пула потоков (количество потоков).
     */
    public ServerCommand(CollectionManager collectionManager,
                         CommandManager commandManager, PasswordManager passwordManager,
                         int parallelism) {
        this.collectionManager = collectionManager;
        this.commandManager = commandManager;
        this.passwordManager = passwordManager;
        this.commandPool = new ForkJoinPool(parallelism);
    }

    /**
     * Выполняет указанную команду в пуле потоков.
     *
     * <p>Команда выполняется асинхронно с использованием {@link CompletableFuture} и пула потоков.
     * Если выполнение команды завершается ошибкой, выбрасывается исключение {@link RuntimeException}.</p>
     *
     * @param command команда для выполнения.
     * @param args аргументы команды.
     * @param token токен авторизации пользователя.
     * @return результат выполнения команды в виде списка объектов.
     * @throws RuntimeException если возникает ошибка при выполнении команды.
     */
    public List<Object> execute(String command, List<Object> args, String token) {
        CommandTask task = new CommandTask(command, args, this, token);
        try {
            return CompletableFuture.supplyAsync(() -> commandPool.invoke(task), commandPool)
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Error executing command: " + command, e);
        }
    }

    /**
     * Внутренний класс {@code CommandTask}, реализующий задачу для выполнения команды.
     *
     * <p>Класс расширяет {@link RecursiveTask} и используется для параллельного выполнения команд.
     * Он определяет логику обработки различных команд и возвращает результат их выполнения.</p>
     */
    private class CommandTask extends RecursiveTask<List<Object>> {
        private final String input;
        private final List<Object> args;
        private final ServerCommand serverCommand;
        private final String login;

        /**
         * Создает новую задачу для выполнения команды.
         *
         * @param command команда для выполнения.
         * @param args аргументы команды.
         * @param serverCommand ссылка на внешний класс {@code ServerCommand}.
         * @param login токен авторизации пользователя.
         */
        public CommandTask(String command, List<Object> args, ServerCommand serverCommand, String login) {
            this.input = command;
            this.args = args;
            this.serverCommand = serverCommand;
            this.login = login;
        }

        /**
         * Выполняет команду в зависимости от её имени.
         *
         * <p>Метод обрабатывает различные команды, вызывая соответствующие классы из пакета {@code commands}.
         * Если команда неизвестна, выбрасывается исключение {@link WrongCommandException}.</p>
         *
         * @return результат выполнения команды в виде списка объектов.
         */
        @Override
        protected List<Object> compute() {
            try {
                switch (input) {
                    case "help":
                        return new Help(collectionManager, commandManager, input, args, serverCommand, login).execute();
                    case "info":
                        return new Info(collectionManager, commandManager, input, args, serverCommand, login).execute();
                    case "show":
                        return new Show(collectionManager, commandManager, input, args, serverCommand, login).execute();
                    case "add":
                        return new Add(collectionManager, commandManager, input, args, serverCommand, login).execute();
                    case "updateid":
                        return new UpdateId(collectionManager, commandManager, input, args, serverCommand, login).execute();
                    case "removebyid":
                        return new RemoveById(collectionManager, commandManager, input, args, serverCommand, login).execute();
                    case "clear":
                        return new Clear(collectionManager, commandManager, input, args, serverCommand, login).execute();
                    case "history":
                        return new History(collectionManager, commandManager, input, args, serverCommand, login).execute();
                    case "removeanybydifficulty":
                        return new RemoveAnyByDifficulty(collectionManager, commandManager, input, args, serverCommand, login).execute();
                    case "sumofminimalpoint":
                        return new SumOfMinimalPoint(collectionManager, commandManager, input, args, serverCommand, login).execute();
                    case "addifmax":
                        return new AddIfMax(collectionManager, commandManager, input, args, serverCommand, login).execute();
                    case "addifmin":
                        return new AddIfMin(collectionManager, commandManager, input, args, serverCommand, login).execute();
                    case "maxbycoordinates":
                        return new MaxByCoordinates(collectionManager, commandManager, input, args, serverCommand, login).execute();
                    default:
                        throw new WrongCommandException("Ошибка: неизвестная команда '" + input + "'");
                }
            } catch (WrongCommandException e) {
                List<Object> result = new ArrayList<>();
                result.add(e.getMessage());
                return result;
            }
        }
    }

    /**
     * Возвращает менеджер паролей.
     *
     * @return экземпляр {@link PasswordManager}.
     */
    public PasswordManager getPasswordManager() {
        return passwordManager;
    }
}