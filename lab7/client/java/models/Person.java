package models;

import java.io.Serializable;

/**
 * Класс, представляющий человека (автора) с различными характеристиками.
 */
public class Person implements Serializable {
    private String name; // Поле не может быть null, строка не может быть пустой
    private float height; // Значение поля должно быть больше 0
    private Color eyeColor; // Поле не может быть null
    private Color hairColor; // Поле может быть null
    private Country nationality; // Поле может быть null
    private Location location; // Поле может быть null

    /**
     * Конструктор для создания объекта {@link Person} с указанными параметрами.
     *
     * @param name        Имя человека (не может быть null или пустым).
     * @param height      Рост человека (должен быть больше 0).
     * @param eyeColor    Цвет глаз (не может быть null).
     * @param hairColor   Цвет волос (может быть null).
     * @param nationality Национальность (может быть null).
     * @param location    Локация (может быть null).
     */
    public Person(String name, float height, Color eyeColor, Color hairColor, Country nationality, Location location) {
        this.name = name;
        this.height = height;
        this.eyeColor = eyeColor;
        this.hairColor = hairColor;
        this.nationality = nationality;
        this.location = location;
    }

    /**
     * Публичный конструктор без параметров для Jackson.
     */
    public Person() {
        // Пустой конструктор для Jackson
    }

    /**
     * Возвращает имя человека.
     *
     * @return Имя человека.
     */
    public String getName() {
        return name;
    }

    /**
     * Возвращает рост человека.
     *
     * @return Рост человека.
     */
    public float getHeight() {
        return height;
    }

    /**
     * Возвращает цвет глаз человека.
     *
     * @return Цвет глаз.
     */
    public Color getEyeColor() {
        return eyeColor;
    }

    /**
     * Возвращает цвет волос человека.
     *
     * @return Цвет волос (может быть null).
     */
    public Color getHairColor() {
        return hairColor;
    }

    /**
     * Возвращает национальность человека.
     *
     * @return Национальность (может быть null).
     */
    public Country getNationality() {
        return nationality;
    }

    /**
     * Возвращает локацию человека.
     *
     * @return Локация (может быть null).
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Возвращает строковое представление объекта {@link Person}.
     *
     * @return Строковое представление объекта.
     */
    @Override
    public String toString() {
        return "Person [name=" + name + ", height=" + height + ", eyeColor=" + eyeColor + ", hairColor=" + hairColor +
                ", nationality=" + nationality + ", location=" + location + "]";
    }
}