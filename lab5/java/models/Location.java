package models;

public class Location {
    private double x;
    private double y;
    private float z;
    public Location(double x, double y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public float getZ() {
        return z;
    }

    @Override
    public String toString() {
        return "Location [x=" + x + ", y=" + y + ", z=" + z + "]";
    }
}
