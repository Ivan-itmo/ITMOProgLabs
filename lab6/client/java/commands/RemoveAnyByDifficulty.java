package commands;

import asks.AskerRemoveAnyByDifficulty;
import models.Difficulty;
import utility.ConsoleManager;

/**
 * Команда для удаления элемента из коллекции по заданной сложности.
 * Класс наследует абстрактный класс Command и реализует метод execute().
 */
public class RemoveAnyByDifficulty extends Command {

    /**
     * Конструктор класса RemoveAnyByDifficulty.
     *
     * @param consoleManager менеджер консоли, который используется для взаимодействия с пользователем.
     */
    public RemoveAnyByDifficulty(ConsoleManager consoleManager) {
        super(consoleManager);
    }

    /**
     * Метод execute() реализует логику команды remove_any_by_difficulty.
     * Удаляет элемент из коллекции по заданной сложности.
     * Если коллекция пуста, выбрасывается исключение MyNullPointerException.
     */
    @Override
    public Difficulty execute() {
            AskerRemoveAnyByDifficulty asker = new AskerRemoveAnyByDifficulty(consoleManager);
            asker.ask();
            return asker.getDifficulty();

    }
}