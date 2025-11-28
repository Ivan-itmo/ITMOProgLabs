package utility;

import models.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * Интерфейс для управления коллекцией объектов {@link LabWork}.
 * Определяет основные операции для работы с коллекцией.
 */
public interface CollectionManagerInterface {

    /**
     * Возвращает коллекцию объектов {@link LabWork}.
     * @return Коллекция объектов {@link LabWork}.
     */
    Set<LabWork> getCollection();

    /**
     * Возвращает дату создания коллекции.
     * @return Дата создания коллекции.
     */
    LocalDateTime getCreationCollectionDate();

    /**
     * Возвращает количество элементов в коллекции.
     * @return Количество элементов в коллекции.
     */
    int collectionSize();

    /**
     * Возвращает сумму значений поля {@code minimalPoint} для всех элементов коллекции.
     * @return Сумма значений {@code minimalPoint}.
     */
    long collectionSum();

    /**
     * Возвращает строковое представление всех элементов коллекции.
     * @return Список объектов коллекции.
     */
    List<Object> collectionShow();

    /**
     * Добавляет новый элемент в коллекцию.
     * @param labWork Объект {@link LabWork} для добавления.
     * @param login Логин пользователя, добавляющего элемент.
     */
    void addLabWork(LabWork labWork, String login);

    /**
     * Обновляет элемент коллекции по указанному ID.
     * @param id ID элемента для обновления.
     * @param updatedLabWork Новые данные элемента.
     * @param login Логин пользователя.
     */
    void updateLabWork(int id, LabWork updatedLabWork, String login);

    /**
     * Удаляет элемент из коллекции по указанному ID.
     * @param id ID элемента для удаления.
     * @param login Логин пользователя.
     */
    void removeByIdLabWork(int id, String login);

    /**
     * Удаляет один элемент из коллекции по указанной сложности.
     * @param difficulty Сложность для удаления.
     * @param login Логин пользователя.
     */
    void removeAnyByDifficultyLabWork(Difficulty difficulty, String login);

    /**
     * Очищает коллекцию от элементов, принадлежащих указанному пользователю.
     * @param login Логин пользователя.
     */
    void clearLabWork(String login);

    /**
     * Возвращает элемент коллекции с максимальным значением.
     * @return Элемент с максимальным значением или {@code null}, если коллекция пуста.
     */
    LabWork getMaxElement();

    /**
     * Возвращает элемент коллекции с минимальным значением.
     * @return Элемент с минимальным значением или {@code null}, если коллекция пуста.
     */
    LabWork getMinElement();

    /**
     * Возвращает координаты с максимальным значением в коллекции.
     * @return Координаты с максимальным значением.
     */
    Coordinates getMaxCoordinates();
}