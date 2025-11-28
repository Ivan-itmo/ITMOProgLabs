package asks;
import utility.*;

public class AskerFileNameScript extends Asker {
    public AskerFileNameScript(ConsoleManager consoleManager) {
        super(consoleManager);
    }
    private String filename;

    @Override
    public void ask() {
        this.filename = askFileName("execute_script");
    }

    public String getFilename() {
        return filename;
    }
}