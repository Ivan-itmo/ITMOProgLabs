package asks;

import models.Color;
import models.Country;
import models.Difficulty;
import utility.AskerValidatorArgs;
import utility.ConsoleManager;

/**
 * Класс AskerUpdateId предназначен для запроса у пользователя данных,
 * необходимых для обновления элемента коллекции по указанному идентификатору (id).
 * Наследует функциональность от класса Asker.
 */
public class AskerValidatorArgsUpdateId extends AskerValidatorArgs {

    /**
     * Конструктор класса AskerUpdateId.
     *
     * @param consoleManager объект класса ConsoleManager, используемый для взаимодействия с консолью.
     */
    public AskerValidatorArgsUpdateId(ConsoleManager consoleManager) {
        super(consoleManager);
    }

    private Integer updateId;
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
     * Метод ask запрашивает у пользователя данные для обновления элемента коллекции.
     * Запрашиваются как идентификатор элемента для обновления, так и новые данные,
     * включая имя, координаты, минимальный балл, сложность, данные создателя и местоположение.
     */
    @Override
    public void ask() {
        this.updateId = askUpdateId("update id");
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

    /**
     * Возвращает идентификатор элемента, который требуется обновить.
     *
     * @return идентификатор элемента для обновления.
     */
    public int getUpdateId() {
        return updateId;
    }

    /**
     * Возвращает новое имя элемента.
     *
     * @return новое имя элемента.
     */
    public String getName() {
        return name;
    }

    /**
     * Возвращает новую координату X элемента.
     *
     * @return новая координата X элемента.
     */
    public long getX() {
        return x;
    }

    /**
     * Возвращает новую координату Y элемента.
     *
     * @return новая координата Y элемента.
     */
    public Long getY() {
        return y;
    }

    /**
     * Возвращает новый минимальный балл элемента.
     *
     * @return новый минимальный балл элемента.
     */
    public long getMinimalPoint() {
        return minimalPoint;
    }

    /**
     * Возвращает новую сложность элемента.
     *
     * @return новая сложность элемента.
     */
    public Difficulty getDifficulty() {
        return difficulty;
    }

    /**
     * Возвращает новое имя создателя элемента.
     *
     * @return новое имя создателя элемента.
     */
    public String getPeopleName() {
        return peoplename;
    }

    /**
     * Возвращает новый рост создателя элемента.
     *
     * @return новый рост создателя элемента.
     */
    public float getHeight() {
        return height;
    }

    /**
     * Возвращает новый цвет глаз создателя элемента.
     *
     * @return новый цвет глаз создателя элемента.
     */
    public Color getEyeColor() {
        return eyeColor;
    }

    /**
     * Возвращает новый цвет волос создателя элемента.
     *
     * @return новый цвет волос создателя элемента.
     */
    public Color getHairColor() {
        return hairColor;
    }

    /**
     * Возвращает новую национальность создателя элемента.
     *
     * @return новая национальность создателя элемента.
     */
    public Country getCountry() {
        return country;
    }

    /**
     * Возвращает новую координату X1 местоположения элемента.
     *
     * @return новая координата X1 местоположения элемента.
     */
    public double getX1() {
        return x1;
    }

    /**
     * Возвращает новую координату Y1 местоположения элемента.
     *
     * @return новая координата Y1 местоположения элемента.
     */
    public double getY1() {
        return y1;
    }

    /**
     * Возвращает новую координату Z1 местоположения элемента.
     *
     * @return новая координата Z1 местоположения элемента.
     */
    public float getZ1() {
        return z1;
    }
}