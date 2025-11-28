package utility;

import dbutility.DBManager;
import models.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;


/**
 * Класс для управления коллекцией объектов {@link LabWork}.
 * Обеспечивает добавление, обновление, удаление и поиск элементов коллекции.
 */
public class CollectionManager implements CollectionManagerInterface {
    private final Set<LabWork> collectionLabWork;
    private LocalDateTime creationcollectionDate;
    private HashMap<LabWork, String> collectionLabWorkLogin = new HashMap<>();
    public DBManager dbManager;
    private String color;
    /**
     * Конструктор для создания объекта {@link CollectionManager}.
     * Инициализирует коллекцию и устанавливает дату создания.
     */
    public CollectionManager(DBManager dbManager, String color) {
        this.collectionLabWork = Collections.synchronizedSet(new LinkedHashSet<>());
        this.creationcollectionDate = LocalDateTime.now();
        this.dbManager = dbManager;
        this.color = color;
    }

    /**
     * Возвращает коллекцию объектов {@link LabWork}.
     *
     * @return Коллекция объектов {@link LabWork}.
     */
    public Set<LabWork> getCollection() {
        return this.collectionLabWork;
    }

    public HashMap<LabWork, String> getCollectionLabWorkLogin() {
        return this.collectionLabWorkLogin;
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
    public void addLabWork(LabWork labWork, String color) {
        synchronized (collectionLabWork) {
            int flag = 0;
            if(!color.equals("#000000")) {
                System.out.println("ok");
                flag = dbManager.addLabWork(labWork);
            }
            else {
                flag = 1;
            }

            if (flag == 1) {
                collectionLabWork.add(labWork);
                collectionLabWorkLogin.put(labWork, color);
                System.out.println("Lab work added: " + labWork);
                List<LabWork> sortedList = new ArrayList<>(collectionLabWork);
                Collections.sort(sortedList);

                Set<LabWork> sortedSet = new LinkedHashSet<>(sortedList);
                this.collectionLabWork.clear();
                this.collectionLabWork.addAll(sortedSet);
            }


        }
    }

    /**
     * Обновляет элемент коллекции по указанному ID.
     *
     * @param id           ID элемента, который нужно обновить.
     */
    public void updateLabWork(int id, LabWork updatedLabWork, String color) {
        synchronized (collectionLabWork) {
            LabWork targetLabWork = null;
            for (LabWork labWork : collectionLabWork) {
                if (labWork.getId() == id && Objects.equals(collectionLabWorkLogin.get(labWork), color)) {
                    targetLabWork = labWork;
                    break;
                }
            }

            if (targetLabWork == null) {
                return;
            }

            int flag = dbManager.updateLabWork(id, updatedLabWork);


            if (flag == 1) {
                targetLabWork.setName(updatedLabWork.getName());
                targetLabWork.setCoordinates(updatedLabWork.getCoordinates());
                targetLabWork.setCreationDate(updatedLabWork.getCreationDate());
                targetLabWork.setMinimalPoint(updatedLabWork.getMinimalPoint());
                targetLabWork.setDifficulty(updatedLabWork.getDifficulty());
                targetLabWork.setAuthor(updatedLabWork.getAuthor());

                // Сортируем коллекцию
                List<LabWork> sortedList = new ArrayList<>(collectionLabWork);
                Collections.sort(sortedList);

                Set<LabWork> sortedSet = new LinkedHashSet<>(sortedList);
                this.collectionLabWork.clear();
                this.collectionLabWork.addAll(sortedSet);
            }
        }
    }

    /**
     * Удаляет элемент из коллекции по указанному ID.
     *
     * @param id ID элемента, который нужно удалить.
     */
    public void removeByIdLabWork(int id, String color) {
        synchronized (collectionLabWork) {
            Iterator<LabWork> iterator = collectionLabWork.iterator();
            while (iterator.hasNext()) {
                LabWork labWork = iterator.next();
                if (labWork.getId() == id && Objects.equals(collectionLabWorkLogin.get(labWork), color)) {
                    int affectedRows = dbManager.removeByIdLabWork(id);
                    if (affectedRows > 0) {
                        iterator.remove();
                        collectionLabWorkLogin.remove(labWork);
                    } else {
                        return;
                    }
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
    public void removeAnyByDifficultyLabWork(Difficulty difficulty, String color) {
        synchronized (collectionLabWork) {
            Iterator<LabWork> iterator = collectionLabWork.iterator();
            while (iterator.hasNext()) {
                LabWork labWork = iterator.next();
                if (labWork.getDifficulty() == difficulty &&
                        Objects.equals(collectionLabWorkLogin.get(labWork), color)) {
                    int affectedRows = dbManager.removeByIdLabWork(labWork.getId());

                    if (affectedRows > 0) {
                        iterator.remove();
                        collectionLabWorkLogin.remove(labWork);
                    }

                }
            }

            // Сортировка коллекции
            List<LabWork> sortedList = new ArrayList<>(collectionLabWork);
            Collections.sort(sortedList);
            Set<LabWork> sortedSet = new LinkedHashSet<>(sortedList);
            collectionLabWork.clear();
            collectionLabWork.addAll(sortedSet);
        }
    }

    public void clearLabWork(String color) {
        synchronized (collectionLabWork) {
            List<Integer> idsToRemove = new ArrayList<>();
            List<LabWork> labWorksToRemove = new ArrayList<>();


            for (LabWork labWork : collectionLabWork) {
                if (Objects.equals(collectionLabWorkLogin.get(labWork), color)) {
                    idsToRemove.add(labWork.getId());
                    labWorksToRemove.add(labWork);
                }
            }

            if (!idsToRemove.isEmpty()) {
                int totalDeleted = dbManager.clearLabWork(idsToRemove);

                if (totalDeleted > 0) {
                    collectionLabWork.removeAll(labWorksToRemove);
                    labWorksToRemove.forEach(collectionLabWorkLogin::remove);
                }
            }

            List<LabWork> sortedList = new ArrayList<>(collectionLabWork);
            Collections.sort(sortedList);
            Set<LabWork> sortedSet = new LinkedHashSet<>(sortedList);
            collectionLabWork.clear();
            collectionLabWork.addAll(sortedSet);
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