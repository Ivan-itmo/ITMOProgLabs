package asks;

import models.*;
import utility.*;

public class AskerAdd extends Asker {
    public AskerAdd(ConsoleManager consoleManager) {
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
        this.name = askName("add");
        this.x = askX("add");
        this.y = askY("add");
        this.minimalPoint = askminimalPoint("add");
        this.difficulty = askDifficulty("add");
        this.peoplename = askpeopleName("add");
        this.height = askHeight("add");
        this.eyeColor = askEyeColor("add");
        this.hairColor = askHairColor("add");
        this.country = askCountry("add");
        this.x1 = askX1("add");
        this.y1 = askY1("add");
        this.z1 = askZ1("add");
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
