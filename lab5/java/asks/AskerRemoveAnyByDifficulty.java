package asks;

import models.*;
import utility.*;

public class AskerRemoveAnyByDifficulty extends Asker{
    public AskerRemoveAnyByDifficulty(ConsoleManager consoleManager) {
        super(consoleManager);
    }
    private Difficulty difficulty;

    @Override
    public void ask() {
        this.difficulty = askDifficulty("remove_any_by_difficulty");
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }
}
