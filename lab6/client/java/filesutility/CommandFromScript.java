package filesutility;

/**
 * Класс, представляющий команду, считанную из скрипта.
 * <p>
 * Каждая команда содержит уникальный идентификатор, название команды и массив аргументов.
 * Этот класс используется для хранения и передачи информации о командах, считанных из файла скрипта.
 * </p>
 */
public class CommandFromScript {
    /**
     * Уникальный идентификатор команды.
     */
    private final int idCommand;

    /**
     * Название команды.
     */
    private final String command;

    /**
     * Массив аргументов команды.
     */
    private final String[] arguments;

    /**
     * Конструктор для создания объекта {@code CommandFromScript}.
     *
     * @param idCommand уникальный идентификатор команды.
     * @param command   название команды.
     * @param arguments массив аргументов команды.
     */
    public CommandFromScript(int idCommand, String command, String[] arguments) {
        this.idCommand = idCommand;
        this.command = command;
        this.arguments = arguments;
    }

    /**
     * Возвращает уникальный идентификатор команды.
     *
     * @return идентификатор команды.
     */
    public int getIdCommand() {
        return idCommand;
    }

    /**
     * Возвращает название команды.
     *
     * @return название команды.
     */
    public String getCommand() {
        return command;
    }

    /**
     * Возвращает массив аргументов команды.
     *
     * @return массив аргументов команды.
     */
    public String[] getArguments() {
        return arguments;
    }
}