package commands;

import asks.AskerValidatorArgsRemoveById;
import utility.CommandManager;
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
    public RemoveById(CommandManager commandManager, ConsoleManager consoleManager) {
        super(commandManager, consoleManager);
    }

    /**
     * Метод execute() реализует логику команды remove_by_id.
     * Удаляет элемент из коллекции по заданному идентификатору.
     * Если коллекция пуста, выбрасывается исключение MyNullPointerException.
     */
    @Override
    public Integer execute() {
            AskerValidatorArgsRemoveById asker = new AskerValidatorArgsRemoveById(consoleManager);
            asker.ask();
            return asker.getId();

    }
}