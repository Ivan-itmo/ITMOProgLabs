package utility;
import java.io.Serializable;
import java.util.List;

public class CommandData implements Serializable {
    private final String commandName;
    private final List<Object> args;

    public CommandData(String commandName, List<Object> args) {
        this.commandName = commandName;
        this.args = args;
    }

    public String getCommandName() {
        return commandName;
    }

    public List<Object> getArgs() {
        return args;
    }
}