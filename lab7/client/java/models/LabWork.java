package models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Класс, представляющий лабораторную работу с различными характеристиками.
 * Наследует класс {@link Element} и реализует интерфейс {@link Comparable}.
 */
public class LabWork extends Element implements Comparable<LabWork>, Serializable {

    @JsonIgnore // Исключаем поле из десериализации
    private LocalDateTime creationDate; // Поле не может быть null

    private Integer id;
    private String name; // Поле не может быть null, строка не может быть пустой
    private Coordinates coordinates; // Поле не может быть null
    private long minimalPoint; // Значение поля должно быть больше 0
    private Difficulty difficulty; // Поле может быть null
    private Person author; // Поле не может быть null

    /**
     * Публичный конструктор без параметров для Jackson.
     * Инициализирует поля при создании объекта.
     */
    public LabWork() {
        initializeFields(); // Инициализация полей
    }

    /**
     * Метод для автоматической инициализации полей.
     * Устанавливает текущую дату создания.
     */
    private void initializeFields() {
        this.creationDate = LocalDateTime.now(); // Установка текущей даты
    }

    /**
     * Конструктор для создания объекта {@link LabWork} с указанными параметрами.
     *
     * @param id           Уникальный идентификатор лабораторной работы.
     * @param name         Название лабораторной работы (не может быть null или пустым).
     * @param coordinates  Координаты лабораторной работы (не могут быть null).
     * @param minimalPoint Минимальное количество баллов (должно быть больше 0).
     * @param difficulty   Уровень сложности (может быть null).
     * @param author       Автор лабораторной работы (не может быть null).
     */
    public LabWork(int id, String name, Coordinates coordinates, long minimalPoint, Difficulty difficulty, Person author) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = LocalDateTime.now();
        this.minimalPoint = minimalPoint;
        this.difficulty = difficulty;
        this.author = author;
    }

    /**
     * Сравнивает текущую лабораторную работу с другой по значению поля {@code minimalPoint}.
     *
     * @param other Другая лабораторная работа для сравнения.
     * @return Результат сравнения (отрицательное число, если текущая работа меньше, положительное, если больше, 0, если равны).
     */
    @Override
    public int compareTo(LabWork other) {
        return Long.compare(this.minimalPoint, other.minimalPoint);
    }

    /**
     * Возвращает уникальный идентификатор лабораторной работы.
     *
     * @return Уникальный идентификатор.
     */
    @Override
    public Integer getId() {
        return id;
    }

    /**
     * Устанавливает уникальный идентификатор лабораторной работы.
     *
     * @param id Уникальный идентификатор.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Возвращает название лабораторной работы.
     *
     * @return Название лабораторной работы.
     */
    public String getName() {
        return name;
    }

    /**
     * Устанавливает название лабораторной работы.
     *
     * @param name Название лабораторной работы (не может быть null или пустым).
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Возвращает координаты лабораторной работы.
     *
     * @return Координаты лабораторной работы.
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Устанавливает координаты лабораторной работы.
     *
     * @param coordinates Координаты лабораторной работы (не могут быть null).
     */
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * Возвращает дату создания лабораторной работы.
     *
     * @return Дата создания.
     */
    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    /**
     * Устанавливает дату создания лабораторной работы.
     *
     * @param creationDate Дата создания (не может быть null).
     */
    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Возвращает минимальное количество баллов для лабораторной работы.
     *
     * @return Минимальное количество баллов.
     */
    public long getMinimalPoint() {
        return minimalPoint;
    }

    /**
     * Устанавливает минимальное количество баллов для лабораторной работы.
     *
     * @param minimalPoint Минимальное количество баллов (должно быть больше 0).
     */
    public void setMinimalPoint(long minimalPoint) {
        this.minimalPoint = minimalPoint;
    }

    /**
     * Возвращает уровень сложности лабораторной работы.
     *
     * @return Уровень сложности (может быть null).
     */
    public Difficulty getDifficulty() {
        return difficulty;
    }

    /**
     * Устанавливает уровень сложности лабораторной работы.
     *
     * @param difficulty Уровень сложности (может быть null).
     */
    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * Возвращает автора лабораторной работы.
     *
     * @return Автор лабораторной работы.
     */
    public Person getAuthor() {
        return author;
    }

    /**
     * Устанавливает автора лабораторной работы.
     *
     * @param author Автор лабораторной работы (не может быть null).
     */
    public void setAuthor(Person author) {
        this.author = author;
    }

    /**
     * Возвращает строковое представление объекта {@link LabWork}.
     *
     * @return Строковое представление лабораторной работы.
     */
    @Override
    public String toString() {
        return "LabWork:\n"
                + "id = " + id + "\n"
                + ", name = " + name + "\n"
                + ", coordinates = " + coordinates + "\n"
                + ", creationDate = " + creationDate + "\n"
                + ", minimalPoint = " + minimalPoint + "\n"
                + ", difficulty = " + difficulty + "\n"
                + ", author = " + author + "\n";
    }
}