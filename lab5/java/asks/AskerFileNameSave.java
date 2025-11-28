package asks;
import utility.*;

public class AskerFileNameSave extends Asker {
    public AskerFileNameSave(ConsoleManager consoleManager) {
        super(consoleManager);
    }
    private String filename;

    @Override
    public void ask() {
        this.filename = askFileName("save");
    }

    public String getFilename() {
        return filename;
    }
}
