package models;
import java.io.Serializable;
/**
 * Класс, представляющий координаты в двумерном пространстве.
 * Координаты состоят из двух значений: x и y, где x ограничено максимальным значением 249,
 * а y не может быть null.
 * <p>
 * Этот класс реализует интерфейс {@link Comparable}, что позволяет сравнивать объекты
 * {@code Coordinates} между собой. Сравнение происходит сначала по значению x,
 * а затем по значению y.
 * </p>
 */
public class Coordinates implements Comparable<Coordinates>, Serializable{
    /**
     * Координата x. Максимальное значение: 249.
     */
    private long x;

    /**
     * Координата y. Не может быть null.
     */
    private Long y;

    /**
     * Конструктор, создающий объект {@code Coordinates} с заданными значениями x и y.
     *
     * @param x значение координаты x (максимальное значение: 249).
     * @param y значение координаты y (не может быть null).
     */
    public Coordinates(long x, Long y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Публичный конструктор без параметров для использования библиотекой Jackson.
     * Этот конструктор не инициализирует поля, поэтому они должны быть установлены
     * через сеттеры или другие методы.
     */
    public Coordinates() {
        // Пустой конструктор для Jackson
    }

    /**
     * Возвращает значение координаты x.
     *
     * @return значение координаты x.
     */
    public long getX() {
        return x;
    }

    /**
     * Возвращает значение координаты y.
     *
     * @return значение координаты y.
     */
    public Long getY() {
        return y;
    }

    /**
     * Возвращает строковое представление объекта {@code Coordinates}.
     *
     * @return строковое представление в формате "Coordinates [x=..., y=...]".
     */
    @Override
    public String toString() {
        return "Coordinates [x=" + x + ", y=" + y + "]";
    }

    /**
     * Сравнивает текущий объект {@code Coordinates} с другим объектом {@code Coordinates}.
     * Сравнение происходит сначала по значению x, а затем по значению y.
     *
     * @param other объект {@code Coordinates}, с которым происходит сравнение.
     * @return отрицательное число, если текущий объект меньше другого;
     *         ноль, если объекты равны;
     *         положительное число, если текущий объект больше другого.
     */
    @Override
    public int compareTo(Coordinates other) {
        // Сначала сравниваем по x
        int compareX = Long.compare(this.x, other.x);
        if (compareX != 0) {
            return compareX;
        }
        // Если x равны, сравниваем по y
        return Long.compare(this.y, other.y);
    }
}