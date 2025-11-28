package filesutility;

/**
 * Когда считываем скрипт, то у нас у каждой команды есть ее id, название и массив аргументов
 */
public class CommandFromScript {
    private final int idCommand;
    private final String command;
    private final String[] arguments;

    public CommandFromScript(int idCommand, String command, String[] arguments) {
        this.idCommand = idCommand;
        this.command = command;
        this.arguments = arguments;
    }

    public int getIdCommand() {
        return idCommand;
    }

    public String getCommand() {
        return command;
    }

    public String[] getArguments() {
        return arguments;
    }
}
