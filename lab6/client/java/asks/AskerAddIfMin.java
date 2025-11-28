package asks;

import models.Color;
import models.Country;
import models.Difficulty;
import utility.Asker;
import utility.ConsoleManager;

/**
 * Класс AskerAddIfMin предназначен для запроса данных у пользователя
 * для создания нового объекта, который будет добавлен в коллекцию,
 * если его значение минимального элемента меньше, чем у всех существующих элементов.
 * Наследует функциональность от класса Asker.
 */
public class AskerAddIfMin extends Asker {

    /**
     * Конструктор класса AskerAddIfMin.
     *
     * @param consoleManager объект класса ConsoleManager, используемый для взаимодействия с консолью.
     */
    public AskerAddIfMin(ConsoleManager consoleManager) {
        super(consoleManager);
    }

    private Integer id;
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

    /**
     * Метод ask запрашивает у пользователя данные для создания нового объекта.
     * Данные включают идентификатор, имя, координаты, минимальный балл, сложность,
     * имя создателя, рост, цвет глаз, цвет волос, национальность и координаты местоположения.
     */
    @Override
    public void ask() {
        this.id = askId("add_if_min");
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

    /**
     * Возвращает идентификатор объекта.
     *
     * @return идентификатор объекта.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Устанавливает идентификатор объекта.
     *
     * @param id идентификатор объекта.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Возвращает имя объекта.
     *
     * @return имя объекта.
     */
    public String getName() {
        return name;
    }

    /**
     * Возвращает координату X объекта.
     *
     * @return координата X объекта.
     */
    public long getX() {
        return x;
    }

    /**
     * Возвращает координату Y объекта.
     *
     * @return координата Y объекта.
     */
    public Long getY() {
        return y;
    }

    /**
     * Возвращает минимальный балл объекта.
     *
     * @return минимальный балл объекта.
     */
    public long getMinimalPoint() {
        return minimalPoint;
    }

    /**
     * Возвращает сложность объекта.
     *
     * @return сложность объекта.
     */
    public Difficulty getDifficulty() {
        return difficulty;
    }

    /**
     * Возвращает имя создателя объекта.
     *
     * @return имя создателя объекта.
     */
    public String getPeopleName() {
        return peoplename;
    }

    /**
     * Возвращает рост создателя объекта.
     *
     * @return рост создателя объекта.
     */
    public float getHeight() {
        return height;
    }

    /**
     * Возвращает цвет глаз создателя объекта.
     *
     * @return цвет глаз создателя объекта.
     */
    public Color getEyeColor() {
        return eyeColor;
    }

    /**
     * Возвращает цвет волос создателя объекта.
     *
     * @return цвет волос создателя объекта.
     */
    public Color getHairColor() {
        return hairColor;
    }

    /**
     * Возвращает национальность создателя объекта.
     *
     * @return национальность создателя объекта.
     */
    public Country getCountry() {
        return country;
    }

    /**
     * Возвращает координату X1 местоположения объекта.
     *
     * @return координата X1 местоположения объекта.
     */
    public double getX1() {
        return x1;
    }

    /**
     * Возвращает координату Y1 местоположения объекта.
     *
     * @return координата Y1 местоположения объекта.
     */
    public double getY1() {
        return y1;
    }

    /**
     * Возвращает координату Z1 местоположения объекта.
     *
     * @return координата Z1 местоположения объекта.
     */
    public float getZ1() {
        return z1;
    }
}