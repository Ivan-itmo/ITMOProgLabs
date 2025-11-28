package asks;

import models.*;
import utility.*;

public class AskerAddIfMin extends Asker {
    public AskerAddIfMin(ConsoleManager consoleManager) {
        super(consoleManager);
    }
    private String name;
    private long x;
    private Long y;
    private long minimalPoint;
    private Difficulty difficulty;
    private String peoplename;
    private float height;
    private Color eyeColor;
    private Color hairColor;
    private Country country;
    private double x1;
    private double y1;
    private float z1;

    @Override
    public void ask() {
        this.name = askName("add_if_min");
        this.x = askX("add_if_min");
        this.y = askY("add_if_min");
        this.minimalPoint = askminimalPoint("add_if_min");
        this.difficulty = askDifficulty("add_if_min");
        this.peoplename = askpeopleName("add_if_min");
        this.height = askHeight("add_if_min");
        this.eyeColor = askEyeColor("add_if_min");
        this.hairColor = askHairColor("add_if_min");
        this.country = askCountry("add_if_min");
        this.x1 = askX1("add_if_min");
        this.y1 = askY1("add_if_min");
        this.z1 = askZ1("add_if_min");
    }

    public String getName() {
        return name;
    }

    public long getX() {
        return x;
    }

    public Long getY() {
        return y;
    }

    public long getMinimalPoint() {
        return minimalPoint;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public String getPeopleName() {
        return peoplename;
    }

    public float getHeight() {
        return height;
    }

    public Color getEyeColor() {
        return eyeColor;
    }

    public Color getHairColor() {
        return hairColor;
    }

    public Country getCountry() {
        return country;
    }

    public double getX1() {
        return x1;
    }

    public double getY1() {
        return y1;
    }

    public float getZ1() {
        return z1;
    }
}