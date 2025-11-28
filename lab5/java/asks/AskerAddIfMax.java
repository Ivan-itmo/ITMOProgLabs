package asks;

import models.*;
import utility.*;

public class AskerAddIfMax extends Asker {
    public AskerAddIfMax(ConsoleManager consoleManager) {
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
        this.name = askName("add_if_max");
        this.x = askX("add_if_max");
        this.y = askY("add_if_max");
        this.minimalPoint = askminimalPoint("add_if_max");
        this.difficulty = askDifficulty("add_if_max");
        this.peoplename = askpeopleName("add_if_max");
        this.height = askHeight("add_if_max");
        this.eyeColor = askEyeColor("add_if_max");
        this.hairColor = askHairColor("add_if_max");
        this.country = askCountry("add_if_max");
        this.x1 = askX1("add_if_max");
        this.y1 = askY1("add_if_max");
        this.z1 = askZ1("add_if_max");
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