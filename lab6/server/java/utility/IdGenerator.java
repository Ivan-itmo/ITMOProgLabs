package utility;

import exceptions.OverflowException;
import models.LabWork;

import java.util.HashSet;
import java.util.Set;

/**
 * Класс для генерации и управления уникальными идентификаторами (ID) для элементов коллекции.
 * Обеспечивает уникальность ID и предотвращает переполнение коллекции.
 */
public class IdGenerator {
    private static Integer counterId = 0;
    private static final Set<Integer> idSet = new HashSet<>();

    /**
     * Генерирует уникальный ID для элемента коллекции. Если переданный ID уже существует,
     * ищет следующий доступный уникальный ID.
     *
     * @param id      Идентификатор, который нужно добавить или заменить.
     * @param labWork Объект {@link LabWork}, для которого генерируется ID.
     * @throws OverflowException Если количество элементов в коллекции превышает {@link Integer#MAX_VALUE}.
     */
    public static void generateId(int id, LabWork labWork) {
        try {
            if (counterId >= Integer.MAX_VALUE) {
                throw new OverflowException("Много значений в коллекции");
            }
        } catch (OverflowException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }

        if (idSet.contains(id)) {
            for (int i = 1; i < Integer.MAX_VALUE; i++) {
                if (!idSet.contains(i)) {
                    idSet.add(i);
                    counterId++;
                    labWork.setId(i);
                    break;
                }
            }
        } else {
            idSet.add(id);
        }
        System.out.println("Список id" + idSet);
    }

    /**
     * Удаляет ID из коллекции и уменьшает счетчик ID.
     *
     * @param id Идентификатор, который нужно удалить.
     */
    public static void deleteId(int id) {
        idSet.remove(id);
        counterId--;
        System.out.println("Список id" + idSet);
    }
}