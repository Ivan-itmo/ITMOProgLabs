package models;

import utility.*;

import java.time.LocalDateTime;

public class LabWork extends Element implements Comparable<LabWork>{
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private long minimalPoint; //Значение поля должно быть больше 0
    private Difficulty difficulty; //Поле может быть null
    private Person author; //Поле не может быть null

    public LabWork(String name, Coordinates coordinates, long minimalPoint, Difficulty difficulty, Person author) {
        this.id = IdGenerator.generateId();
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = LocalDateTime.now();
        this.minimalPoint = minimalPoint;
        this.difficulty = difficulty;
        this.author = author;
    }
    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public int compareTo(LabWork other) {
        return Long.compare(this.minimalPoint, other.minimalPoint);
    }

    public String getName() {
        return name;
    }
    public Coordinates getCoordinates() {
        return coordinates;
    }
    public LocalDateTime getCreationDate() {
        return creationDate;
    }
    public long getMinimalPoint() {
        return minimalPoint;
    }
    public Difficulty getDifficulty() {
        return difficulty;
    }
    public Person getAuthor() {
        return author;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
    public void setMinimalPoint(long minimalPoint) {
        this.minimalPoint = minimalPoint;
    }
    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }
    public void setAuthor(Person author) {
        this.author = author;
    }

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
