package asks;

import utility.AskerValidatorArgs;
import utility.ConsoleManager;

/**
 * Класс AskerRemoveById предназначен для запроса у пользователя идентификатора (id),
 * чтобы удалить элемент из коллекции, соответствующий указанному идентификатору.
 * Наследует функциональность от класса Asker.
 */
public class AskerValidatorArgsRemoveById extends AskerValidatorArgs {

    /**
     * Конструктор класса AskerRemoveById.
     *
     * @param consoleManager объект класса ConsoleManager, используемый для взаимодействия с консолью.
     */
    public AskerValidatorArgsRemoveById(ConsoleManager consoleManager) {
        super(consoleManager);
    }

    private int id;

    /**
     * Метод ask запрашивает у пользователя идентификатор (id),
     * который будет использоваться для удаления соответствующего элемента из коллекции.
     * Результат сохраняется в поле id.
     */
    @Override
    public void ask() {
        this.id = askUpdateId("Remove_any_by_id");
    }

    /**
     * Возвращает идентификатор (id), который был запрошен у пользователя.
     *
     * @return идентификатор (id), используемый для удаления элемента из коллекции.
     */
    public int getId() {
        return id;
    }
}