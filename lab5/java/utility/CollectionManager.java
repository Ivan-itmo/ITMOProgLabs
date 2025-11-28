package utility;

import models.Coordinates;
import models.Difficulty;
import models.LabWork;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Класс манагера коллекции, нужен для работы с коллекцией: получение информации о ней, всякие действия
 */
public class CollectionManager {
    private LinkedHashSet<LabWork> collectionLabWork;
    private LocalDateTime creationcollectionDate;
    public CollectionManager() {
        this.collectionLabWork = new LinkedHashSet<>();
        this.creationcollectionDate = LocalDateTime.now();
    }
    public LinkedHashSet<LabWork> getCollection(){
        return this.collectionLabWork;
    }
    public LocalDateTime getCreationCollectionDate() {
        return creationcollectionDate;
    }
    public int collectionSize() {
        return collectionLabWork.size();
    }

    public long collectionSum() {
        long sum = 0;
        for (LabWork labWork : collectionLabWork) {
            sum += labWork.getMinimalPoint();
        }
        return sum;
    }

    public String collectionShow(){
        String result = "";
        for (LabWork labWork : collectionLabWork) {
            result += labWork.toString() + "\n";
        }
        return result;
    }

    public void addLabWork(LabWork labWork) {
        collectionLabWork.add(labWork);
        List<LabWork> sortedList = new ArrayList<>(collectionLabWork); // Преобразуем в список
        Collections.sort(sortedList); // Сортируем

        Set<LabWork> sortedSet = new LinkedHashSet<>(sortedList);// Переносим в LinkedHashSet
        this.collectionLabWork.clear();
        this.collectionLabWork.addAll(sortedSet);
    }

    public void updateLabWork(int id, LabWork resultlabWork) {
        for (LabWork labWork : collectionLabWork) {
            if (labWork.getId() == id) {
                labWork.setName(resultlabWork.getName());
                labWork.setCoordinates(resultlabWork.getCoordinates());
                labWork.setCreationDate(resultlabWork.getCreationDate());
                labWork.setMinimalPoint(resultlabWork.getMinimalPoint());
                labWork.setDifficulty(resultlabWork.getDifficulty());
                labWork.setAuthor(resultlabWork.getAuthor());
            }
        }
        List<LabWork> sortedList = new ArrayList<>(collectionLabWork); // Преобразуем в список
        Collections.sort(sortedList); // Сортируем

        Set<LabWork> sortedSet = new LinkedHashSet<>(sortedList);// Переносим в LinkedHashSet
        this.collectionLabWork.clear();
        this.collectionLabWork.addAll(sortedSet);
    }
    public void removebyidLabWork(int id) {
        collectionLabWork.removeIf(labWork -> labWork.getId() == id);
    }
    public void removeanybydifficultyLabWork(Difficulty difficulty) {
        List<LabWork> toRemove = new ArrayList<>();
        for (LabWork labWork : collectionLabWork) {
            if (labWork.getDifficulty() == difficulty) {
                toRemove.add(labWork);
            }
        }
        collectionLabWork.removeAll(toRemove);
    }

    public void clearLabWork() {
        collectionLabWork.clear();
    }

    public LabWork getMaxElement() {
        if (collectionLabWork.isEmpty()) {
            return null; // Если коллекция пустая, возвращаем null
        }
        return Collections.max(collectionLabWork);
    }

    public LabWork getMinElement() {
        if (collectionLabWork.isEmpty()) {
            return null; // Если коллекция пустая, возвращаем null
        }
        return Collections.min(collectionLabWork);
    }

    public Coordinates getMaxCoordinates() {
        Coordinates result = new Coordinates(Long.MIN_VALUE, Long.MIN_VALUE); // Начальные минимальные координаты
        for (LabWork labWork : collectionLabWork) {
            if (labWork.getCoordinates().compareTo(result) > 0) {
                result = labWork.getCoordinates(); // Обновляем координаты, если текущие больше
            }
        }
        return result; // Возвращаем максимальные координаты
    }
}
