package asks;

import models.Color;
import models.Country;
import models.Difficulty;
import utility.AskerValidatorArgs;
import utility.ConsoleManager;

/**
 * Класс для запроса данных у пользователя при добавлении нового элемента в коллекцию,
 * если его значение превышает максимальный элемент в коллекции.
 * Наследует абстрактный класс Asker и реализует метод ask().
 */
public class AskerValidatorArgsAddIfMax extends AskerValidatorArgs {

    /**
     * Конструктор класса AskerAddIfMax.
     *
     * @param consoleManager менеджер консоли, который используется для взаимодействия с пользователем.
     */
    public AskerValidatorArgsAddIfMax(ConsoleManager consoleManager) {
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

    /**
     * Метод ask() запрашивает у пользователя данные для создания нового элемента коллекции.
     * Запрашивает все необходимые поля, такие как id, name, координаты, минимальный балл, сложность и т.д.
     */
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

    /**
     * Возвращает имя элемента.
     *
     * @return имя элемента.
     */
    public String getName() {
        return name;
    }

    /**
     * Возвращает координату X.
     *
     * @return координата X.
     */
    public long getX() {
        return x;
    }

    /**
     * Возвращает координату Y.
     *
     * @return координата Y.
     */
    public Long getY() {
        return y;
    }

    /**
     * Возвращает минимальный балл.
     *
     * @return минимальный балл.
     */
    public long getMinimalPoint() {
        return minimalPoint;
    }

    /**
     * Возвращает сложность.
     *
     * @return сложность.
     */
    public Difficulty getDifficulty() {
        return difficulty;
    }

    /**
     * Возвращает имя автора.
     *
     * @return имя автора.
     */
    public String getPeopleName() {
        return peoplename;
    }

    /**
     * Возвращает рост автора.
     *
     * @return рост автора.
     */
    public float getHeight() {
        return height;
    }

    /**
     * Возвращает цвет глаз автора.
     *
     * @return цвет глаз автора.
     */
    public Color getEyeColor() {
        return eyeColor;
    }

    /**
     * Возвращает цвет волос автора.
     *
     * @return цвет волос автора.
     */
    public Color getHairColor() {
        return hairColor;
    }

    /**
     * Возвращает страну автора.
     *
     * @return страна автора.
     */
    public Country getCountry() {
        return country;
    }

    /**
     * Возвращает координату X1.
     *
     * @return координата X1.
     */
    public double getX1() {
        return x1;
    }

    /**
     * Возвращает координату Y1.
     *
     * @return координата Y1.
     */
    public double getY1() {
        return y1;
    }

    /**
     * Возвращает координату Z1.
     *
     * @return координата Z1.
     */
    public float getZ1() {
        return z1;
    }
}