package models;

public class Coordinates implements Comparable<Coordinates>{
    private long x; //Максимальное значения поля: 249
    private Long y; //Поле не может быть null
    public Coordinates(long x, Long y) {
        this.x = x;
        this.y = y;
    }

    public long getX() {
        return x;
    }

    public Long getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Coordinates [x=" + x + ", y=" + y + "]";
    }

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