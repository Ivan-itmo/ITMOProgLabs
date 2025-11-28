package dbutility;

import models.LabWork;
import java.sql.SQLException;
import java.util.List;

/**
 * Интерфейс для работы с базой данных, содержащей объекты {@link LabWork}.
 * Определяет основные операции для взаимодействия с базой данных.
 */
public interface DBManagerInterface {

    /**
     * Добавляет новый объект LabWork в базу данных.
     * @param labWork Объект для добавления
     * @return 1 в случае успеха, 0 в случае ошибки
     */
    int addLabWork(LabWork labWork);

    /**
     * Обновляет существующий объект LabWork в базе данных.
     * @param id ID обновляемого объекта
     * @param updatedLabWork Объект с новыми данными
     * @return 1 в случае успеха, 0 в случае ошибки
     */
    int updateLabWork(int id, LabWork updatedLabWork);

    /**
     * Удаляет объект LabWork из базы данных по ID.
     * @param id ID удаляемого объекта
     * @return Количество удаленных строк (1 в случае успеха, 0 если объект не найден)
     */
    int removeByIdLabWork(int id);

    /**
     * Удаляет несколько объектов LabWork из базы данных по списку ID.
     * @param idsToRemove Список ID для удаления
     * @return Количество удаленных строк
     */
    int clearLabWork(List<Integer> idsToRemove);
}