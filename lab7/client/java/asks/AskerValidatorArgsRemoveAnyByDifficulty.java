package asks;

import models.Difficulty;
import utility.AskerValidatorArgs;
import utility.ConsoleManager;

/**
 * Класс AskerRemoveAnyByDifficulty предназначен для запроса у пользователя уровня сложности (Difficulty),
 * чтобы удалить любой элемент из коллекции, соответствующий указанной сложности.
 * Наследует функциональность от класса Asker.
 */
public class AskerValidatorArgsRemoveAnyByDifficulty extends AskerValidatorArgs {

    /**
     * Конструктор класса AskerRemoveAnyByDifficulty.
     *
     * @param consoleManager объект класса ConsoleManager, используемый для взаимодействия с консолью.
     */
    public AskerValidatorArgsRemoveAnyByDifficulty(ConsoleManager consoleManager) {
        super(consoleManager);
    }

    private Difficulty difficulty;

    /**
     * Метод ask запрашивает у пользователя уровень сложности (Difficulty),
     * который будет использоваться для удаления соответствующего элемента из коллекции.
     * Результат сохраняется в поле difficulty.
     */
    @Override
    public void ask() {
        this.difficulty = askDifficulty("remove_any_by_difficulty");
    }

    /**
     * Возвращает уровень сложности (Difficulty), который был запрошен у пользователя.
     *
     * @return уровень сложности (Difficulty), используемый для удаления элемента из коллекции.
     */
    public Difficulty getDifficulty() {
        return difficulty;
    }
}