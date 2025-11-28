package utility;

import models.Color;
import models.Country;
import models.Difficulty;

/**
 * Интерфейс для валидации и запроса данных у пользователя.
 * Обеспечивает ввод и проверку данных для создания или обновления объектов.
 */
public interface AskerValidatorInterface {
    /**
     * Запрашивает ID для обновления элемента.
     *
     * @param command Команда, для которой запрашивается ID.
     * @return Введенный ID.
     */
    int askUpdateId(String command);

    /**
     * Запрашивает имя элемента.
     *
     * @param command Команда, для которой запрашивается имя.
     * @return Введенное имя.
     */
    String askName(String command);

    /**
     * Запрашивает координату X.
     *
     * @param command Команда, для которой запрашивается координата.
     * @return Введенная координата X.
     */
    long askX(String command);

    /**
     * Запрашивает координату Y.
     *
     * @param command Команда, для которой запрашивается координата.
     * @return Введенная координата Y.
     */
    Long askY(String command);

    /**
     * Запрашивает минимальное количество баллов.
     *
     * @param command Команда, для которой запрашивается значение.
     * @return Введенное минимальное количество баллов.
     */
    long askminimalPoint(String command);

    /**
     * Запрашивает уровень сложности.
     *
     * @param command Команда, для которой запрашивается уровень сложности.
     * @return Введенный уровень сложности или {@code null}, если ввод пустой.
     */
    Difficulty askDifficulty(String command);

    /**
     * Запрашивает имя автора.
     *
     * @param command Команда, для которой запрашивается имя.
     * @return Введенное имя автора.
     */
    String askpeopleName(String command);

    /**
     * Запрашивает рост автора.
     *
     * @param command Команда, для которой запрашивается рост.
     * @return Введенный рост.
     */
    float askHeight(String command);

    /**
     * Запрашивает цвет глаз автора.
     *
     * @param command Команда, для которой запрашивается цвет глаз.
     * @return Введенный цвет глаз.
     */
    Color askEyeColor(String command);

    /**
     * Запрашивает цвет волос автора.
     *
     * @param command Команда, для которой запрашивается цвет волос.
     * @return Введенный цвет волос или {@code null}, если ввод пустой.
     */
    Color askHairColor(String command);

    /**
     * Запрашивает страну автора.
     *
     * @param command Команда, для которой запрашивается страна.
     * @return Введенная страна или {@code null}, если ввод пустой.
     */
    Country askCountry(String command);

    /**
     * Запрашивает координату X для локации.
     *
     * @param command Команда, для которой запрашивается координата.
     * @return Введенная координата X.
     */
    double askX1(String command);

    /**
     * Запрашивает координату Y для локации.
     *
     * @param command Команда, для которой запрашивается координата.
     * @return Введенная координата Y.
     */
    double askY1(String command);

    /**
     * Запрашивает координату Z для локации.
     *
     * @param command Команда, для которой запрашивается координата.
     * @return Введенная координата Z.
     */
    float askZ1(String command);

    /**
     * Запрашивает имя файла.
     *
     * @param command Команда, для которой запрашивается имя файла.
     * @return Введенное имя файла.
     */
    String askFileName(String command);
}