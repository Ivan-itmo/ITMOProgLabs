package models;

import java.io.Serializable;

/**
 * Класс, представляющий локацию с координатами x, y и z.
 */
public class Location implements Serializable {
    private double x;
    private double y;
    private float z;

    /**
     * Конструктор для создания объекта {@link Location} с указанными координатами.
     *
     * @param x Координата X.
     * @param y Координата Y.
     * @param z Координата Z.
     */
    public Location(double x, double y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Публичный конструктор без параметров для Jackson.
     */
    public Location() {
        // Пустой конструктор для Jackson
    }

    /**
     * Возвращает координату X.
     *
     * @return Координата X.
     */
    public double getX() {
        return x;
    }

    /**
     * Возвращает координату Y.
     *
     * @return Координата Y.
     */
    public double getY() {
        return y;
    }

    /**
     * Возвращает координату Z.
     *
     * @return Координата Z.
     */
    public float getZ() {
        return z;
    }

    /**
     * Возвращает строковое представление объекта {@link Location}.
     *
     * @return Строковое представление локации.
     */
    @Override
    public String toString() {
        return "Location [x=" + x + ", y=" + y + ", z=" + z + "]";
    }
}