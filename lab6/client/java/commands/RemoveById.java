package commands;

import asks.AskerRemoveById;
import utility.ConsoleManager;

/**
 * Команда для удаления элемента из коллекции по заданному идентификатору.
 * Класс наследует абстрактный класс Command и реализует метод execute().
 */
public class RemoveById extends Command {

    /**
     * Конструктор класса RemoveById.
     *
     * @param consoleManager менеджер консоли, который используется для взаимодействия с пользователем.
     */
    public RemoveById(ConsoleManager consoleManager) {
        super(consoleManager);
    }

    /**
     * Метод execute() реализует логику команды remove_by_id.
     * Удаляет элемент из коллекции по заданному идентификатору.
     * Если коллекция пуста, выбрасывается исключение MyNullPointerException.
     */
    @Override
    public Integer execute() {
            AskerRemoveById asker = new AskerRemoveById(consoleManager);
            asker.ask();
            return asker.getId();

    }
}