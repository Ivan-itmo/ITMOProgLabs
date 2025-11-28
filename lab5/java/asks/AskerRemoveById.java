package asks;
import utility.*;

public class AskerRemoveById extends Asker{
    public AskerRemoveById(ConsoleManager consoleManager) {
        super(consoleManager);
    }
    public int id;

    @Override
    public void ask() {
        this.id = askId("Remove_any_by_id");
    }

    public int getId(){
        return id;
    }
}

