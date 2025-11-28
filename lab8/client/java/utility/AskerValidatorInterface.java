package utility;

import exceptions.ValidationException;
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
     * @throws ValidationException если ввод некорректен.
     */
    int askUpdateId(String command) throws ValidationException;

    /**
     * Запрашивает имя элемента.
     *
     * @param command Команда, для которой запрашивается имя.
     * @return Введенное имя.
     * @throws ValidationException если ввод некорректен.
     */
    String askName(String command) throws ValidationException;

    /**
     * Запрашивает координату X.
     *
     * @param command Команда, для которой запрашивается координата.
     * @return Введенная координата X.
     * @throws ValidationException если ввод некорректен.
     */
    long askX(String command) throws ValidationException;

    /**
     * Запрашивает координату Y.
     *
     * @param command Команда, для которой запрашивается координата.
     * @return Введенная координата Y.
     * @throws ValidationException если ввод некорректен.
     */
    Long askY(String command) throws ValidationException;

    /**
     * Запрашивает минимальное количество баллов.
     *
     * @param command Команда, для которой запрашивается значение.
     * @return Введенное минимальное количество баллов.
     * @throws ValidationException если ввод некорректен.
     */
    long askminimalPoint(String command) throws ValidationException;

    /**
     * Запрашивает уровень сложности.
     *
     * @param command Команда, для которой запрашивается уровень сложности.
     * @return Введенный уровень сложности или {@code null}, если ввод пустой.
     * @throws ValidationException если ввод некорректен.
     */
    Difficulty askDifficulty(String command) throws ValidationException;

    /**
     * Запрашивает имя автора.
     *
     * @param command Команда, для которой запрашивается имя.
     * @return Введенное имя автора.
     * @throws ValidationException если ввод некорректен.
     */
    String askpeopleName(String command) throws ValidationException;

    /**
     * Запрашивает рост автора.
     *
     * @param command Команда, для которой запрашивается рост.
     * @return Введенный рост.
     * @throws ValidationException если ввод некорректен.
     */
    float askHeight(String command) throws ValidationException;

    /**
     * Запрашивает цвет глаз автора.
     *
     * @param command Команда, для которой запрашивается цвет глаз.
     * @return Введенный цвет глаз.
     * @throws ValidationException если ввод некорректен.
     */
    Color askEyeColor(String command) throws ValidationException;

    /**
     * Запрашивает цвет волос автора.
     *
     * @param command Команда, для которой запрашивается цвет волос.
     * @return Введенный цвет волос или {@code null}, если ввод пустой.
     * @throws ValidationException если ввод некорректен.
     */
    Color askHairColor(String command) throws ValidationException;

    /**
     * Запрашивает страну автора.
     *
     * @param command Команда, для которой запрашивается страна.
     * @return Введенная страна или {@code null}, если ввод пустой.
     * @throws ValidationException если ввод некорректен.
     */
    Country askCountry(String command) throws ValidationException;

    /**
     * Запрашивает координату X для локации.
     *
     * @param command Команда, для которой запрашивается координата.
     * @return Введенная координата X.
     * @throws ValidationException если ввод некорректен.
     */
    double askX1(String command) throws ValidationException;

    /**
     * Запрашивает координату Y для локации.
     *
     * @param command Команда, для которой запрашивается координата.
     * @return Введенная координата Y.
     * @throws ValidationException если ввод некорректен.
     */
    double askY1(String command) throws ValidationException;

    /**
     * Запрашивает координату Z для локации.
     *
     * @param command Команда, для которой запрашивается координата.
     * @return Введенная координата Z.
     * @throws ValidationException если ввод некорректен.
     */
    float askZ1(String command) throws ValidationException;

    /**
     * Запрашивает имя файла.
     *
     * @param command Команда, для которой запрашивается имя файла.
     * @return Введенное имя файла.
     * @throws ValidationException если ввод некорректен.
     */
    String askFileName(String command) throws ValidationException;
}