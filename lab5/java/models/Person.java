package models;

public class Person {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private float height; //Значение поля должно быть больше 0
    private Color eyeColor; //Поле не может быть null
    private Color hairColor; //Поле может быть null
    private Country nationality; //Поле может быть null
    private Location location; //Поле может быть null

    public Person(String name, float height, Color eyeColor, Color hairColor, Country nationality, Location location) {
        this.name = name;
        this.height = height;
        this.eyeColor = eyeColor;
        this.hairColor = hairColor;
        this.nationality = nationality;
        this.location = location;
    }

    public String getName() {
        return name;
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
    public Country getNationality() {
        return nationality;
    }
    public Location getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return "Person [name=" + name + ", height=" + height + ", eyeColor=" + eyeColor + ", hairColor=" + hairColor + ", nationality=" + nationality + ", location=" + location + "]";
    }
}
