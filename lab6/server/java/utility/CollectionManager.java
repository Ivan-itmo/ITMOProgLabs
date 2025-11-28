package utility;

import models.Coordinates;
import models.Difficulty;
import models.LabWork;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Класс для управления коллекцией объектов {@link LabWork}.
 * Обеспечивает добавление, обновление, удаление и поиск элементов коллекции.
 */
public class CollectionManager {
    private Set<LabWork> collectionLabWork;
    private LocalDateTime creationcollectionDate;

    /**
     * Конструктор для создания объекта {@link CollectionManager}.
     * Инициализирует коллекцию и устанавливает дату создания.
     */
    public CollectionManager() {
        this.collectionLabWork = Collections.synchronizedSet(new LinkedHashSet<>());
        this.creationcollectionDate = LocalDateTime.now();
    }

    /**
     * Возвращает коллекцию объектов {@link LabWork}.
     *
     * @return Коллекция объектов {@link LabWork}.
     */
    public Set<LabWork> getCollection() {
        return this.collectionLabWork;
    }

    /**
     * Возвращает дату создания коллекции.
     *
     * @return Дата создания коллекции.
     */
    public LocalDateTime getCreationCollectionDate() {
        return creationcollectionDate;
    }

    /**
     * Возвращает количество элементов в коллекции.
     *
     * @return Количество элементов в коллекции.
     */
    public int collectionSize() {
        return collectionLabWork.size();
    }

    /**
     * Возвращает сумму значений поля {@code minimalPoint} для всех элементов коллекции.
     *
     * @return Сумма значений {@code minimalPoint}.
     */
    public long collectionSum() {
        synchronized (collectionLabWork) {
            long sum = 0;
            for (LabWork labWork : collectionLabWork) {
                sum += labWork.getMinimalPoint();
            }
            return sum;
        }
    }

    /**
     * Возвращает строковое представление всех элементов коллекции.
     *
     * @return Строковое представление коллекции.
     */
    public List<Object> collectionShow() {
        synchronized (collectionLabWork) {
            List<Object> show = new ArrayList<>();
            for (LabWork labWork : collectionLabWork) {
                show.add(labWork); // Без приведения типа
            }
            return show;
        }
    }

    /**
     * Добавляет новый элемент в коллекцию и сортирует её.
     *
     * @param labWork Объект {@link LabWork} для добавления в коллекцию.
     */
    public void addLabWork(LabWork labWork) {
        synchronized (collectionLabWork) {
            collectionLabWork.add(labWork);
            List<LabWork> sortedList = new ArrayList<>(collectionLabWork);
            Collections.sort(sortedList);

            Set<LabWork> sortedSet = new LinkedHashSet<>(sortedList);
            this.collectionLabWork.clear();
            this.collectionLabWork.addAll(sortedSet);
        }
    }

    /**
     * Обновляет элемент коллекции по указанному ID.
     *
     * @param id           ID элемента, который нужно обновить.
     * @param resultlabWork Новый объект {@link LabWork} для замены.
     */
    public void updateLabWork(int id, LabWork resultlabWork) {
        synchronized (collectionLabWork) {
            int f = 0;
            for (LabWork labWork : collectionLabWork) {
                if (labWork.getId() == id) {
                    f = 1;
                    labWork.setName(resultlabWork.getName());
                    labWork.setCoordinates(resultlabWork.getCoordinates());
                    labWork.setCreationDate(resultlabWork.getCreationDate());
                    labWork.setMinimalPoint(resultlabWork.getMinimalPoint());
                    labWork.setDifficulty(resultlabWork.getDifficulty());
                    labWork.setAuthor(resultlabWork.getAuthor());
                    IdGenerator.deleteId(resultlabWork.getId());
                }
            }
            if (f == 0) {
                IdGenerator.deleteId(resultlabWork.getId());
            }
            List<LabWork> sortedList = new ArrayList<>(collectionLabWork);
            Collections.sort(sortedList);

            Set<LabWork> sortedSet = new LinkedHashSet<>(sortedList);
            this.collectionLabWork.clear();
            this.collectionLabWork.addAll(sortedSet);
        }
    }

    /**
     * Удаляет элемент из коллекции по указанному ID.
     *
     * @param id ID элемента, который нужно удалить.
     */
    public void removebyidLabWork(int id) {
        synchronized (collectionLabWork) {
            Iterator<LabWork> iterator = collectionLabWork.iterator();
            while (iterator.hasNext()) {
                LabWork labWork = iterator.next();
                if (labWork.getId() == id) {
                    IdGenerator.deleteId(labWork.getId());
                    iterator.remove();
                    break;
                }
            }
        }
    }

    /**
     * Удаляет один элемент из коллекции по указанной сложности.
     *
     * @param difficulty Сложность, по которой нужно удалить элемент.
     */
    public void removeanybydifficultyLabWork(Difficulty difficulty) {
        synchronized (collectionLabWork) {
            Iterator<LabWork> iterator = collectionLabWork.iterator();
            while (iterator.hasNext()) {
                LabWork labWork = iterator.next();
                if (labWork.getDifficulty() == difficulty) {
                    IdGenerator.deleteId(labWork.getId());
                    iterator.remove();
                }
            }
        }
    }

    /**
     * Очищает коллекцию и удаляет все ID из {@link IdGenerator}.
     */
    public void clearLabWork() {
        synchronized (collectionLabWork) {
            for (LabWork labWork : collectionLabWork) {
                IdGenerator.deleteId(labWork.getId());
            }
            collectionLabWork.clear();
        }
    }

    /**
     * Возвращает элемент коллекции с максимальным значением.
     *
     * @return Элемент с максимальным значением или {@code null}, если коллекция пуста.
     */
    public LabWork getMaxElement() {
        synchronized (collectionLabWork) {
            if (collectionLabWork.isEmpty()) {
                return null;
            }
            return Collections.max(collectionLabWork);
        }
    }

    /**
     * Возвращает элемент коллекции с минимальным значением.
     *
     * @return Элемент с минимальным значением или {@code null}, если коллекция пуста.
     */
    public LabWork getMinElement() {
        synchronized (collectionLabWork) {
            if (collectionLabWork.isEmpty()) {
                return null;
            }
            return Collections.min(collectionLabWork);
        }
    }

    /**
     * Возвращает координаты с максимальным значением в коллекции.
     *
     * @return Координаты с максимальным значением.
     */
    public Coordinates getMaxCoordinates() {
        synchronized (collectionLabWork) {
            Coordinates result = new Coordinates(Long.MIN_VALUE, Long.MIN_VALUE);
            for (LabWork labWork : collectionLabWork) {
                if (labWork.getCoordinates().compareTo(result) > 0) {
                    result = labWork.getCoordinates();
                }
            }
            return result;
        }
    }
}