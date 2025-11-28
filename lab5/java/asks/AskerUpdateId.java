package asks;

import models.*;
import utility.*;

public class AskerUpdateId extends Asker {
    public AskerUpdateId(ConsoleManager consoleManager) {
        super(consoleManager);
    }
    public int id;
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
        this.id = askId("update id");
        this.name = askName("update id");
        this.x = askX("update id");
        this.y = askY("update id");
        this.minimalPoint = askminimalPoint("update id");
        this.difficulty = askDifficulty("update id");
        this.peoplename = askpeopleName("update id");
        this.height = askHeight("update id");
        this.eyeColor = askEyeColor("update id");
        this.hairColor = askHairColor("update id");
        this.country = askCountry("update id");
        this.x1 = askX1("update id");
        this.y1 = askY1("update id");
        this.z1 = askZ1("update id");
    }

    public int getId() {
        return id;
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
