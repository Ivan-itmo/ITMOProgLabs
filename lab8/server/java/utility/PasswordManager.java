package utility;

import exceptions.HavingUserException;
import exceptions.NotFoundUserException;
import exceptions.WrongPasswordException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;

/**
 * Класс {@code PasswordManager} предоставляет функциональность для управления учетными данными пользователей,
 * включая регистрацию, вход и безопасное хранение паролей.
 *
 * <p>Класс использует хэширование паролей с добавлением "соли" (salt) для повышения безопасности.
 * Также генерируются уникальные токены для авторизации пользователей после успешного входа.</p>
 *
 * <p>Основные функции:
 * <ul>
 *   <li>Регистрация нового пользователя с проверкой на уникальность имени пользователя.</li>
 *   <li>Вход пользователя с проверкой пароля.</li>
 *   <li>Генерация случайной "соли" для каждого пользователя.</li>
 *   <li>Хэширование паролей с использованием алгоритма MD5.</li>
 *   <li>Генерация уникальных токенов для авторизации.</li>
 * </ul></p>
 *
 * @see HavingUserException
 * @see NotFoundUserException
 * @see WrongPasswordException
 */
public class PasswordManager {
    private HashMap<String, String> passwords = new HashMap<>();
    private HashMap<String, String> salts = new HashMap<>();
    private final Map<String, String> userColors = new HashMap<>(); // username -> color
    private final Set<String> usedColors = new HashSet<>();

    /**
     * Создает новый экземпляр класса {@code PasswordManager}.
     */
    public PasswordManager() {}

    /**
     * Регистрирует нового пользователя.
     *
     * <p>Проверяет, существует ли уже пользователь с указанным именем. Если пользователь уже зарегистрирован,
     * выбрасывается исключение {@link HavingUserException}. В противном случае создается новая запись
     * с захэшированным паролем, солью и токеном авторизации.</p>
     *
     * @param username имя пользователя
     * @param password пароль пользователя
     * @return токен авторизации при успешной регистрации
     * @throws NoSuchAlgorithmException если возникает ошибка при хэшировании пароля
     */
    public String registration(String username, String password) throws NoSuchAlgorithmException {
        try {
            if (passwords.containsKey(username)) {
                throw new HavingUserException("Ошибка, такой пользователь уже есть");
            }
        } catch (HavingUserException e) {
            return e.getMessage();
        }

        String salt = generateSalt();
        String hashedPassword = hashPassword(password + salt);

        passwords.put(username, hashedPassword);
        salts.put(username, salt);
        String color = generateUniqueColor(); // теперь цвет вместо токена
        userColors.put(username, color);
        return color;
    }

    private String generateUniqueColor() {
        String color;
        do {
            color = String.format("#%06X", new Random().nextInt(0xFFFFFF + 1));
        } while (usedColors.contains(color));
        usedColors.add(color);
        return color;
    }

    /**
     * Выполняет вход пользователя.
     *
     * <p>Проверяет существование пользователя и корректность введенного пароля. Если пользователь не найден,
     * выбрасывается исключение {@link NotFoundUserException}. Если пароль неверный, выбрасывается исключение
     * {@link WrongPasswordException}. При успешной проверке возвращается токен авторизации.</p>
     *
     * @param username имя пользователя
     * @param password пароль пользователя
     * @return токен авторизации при успешном входе или сообщение об ошибке
     */
    public String login(String username, String password) {
        try {
            if (!passwords.containsKey(username) || !salts.containsKey(username)) {
                throw new NotFoundUserException("Ошибка: пользователь не найден");
            }
        } catch (NotFoundUserException e) {
            return e.getMessage();
        }

        try {
            String storedHash = passwords.get(username);
            String salt = salts.get(username);
            String inputHash = hashPassword(password + salt);

            try {
                if (inputHash.equals(storedHash)) {
                    return userColors.get(username);
                } else {
                    throw new WrongPasswordException("Ошибка: неправильный пароль");
                }
            } catch (WrongPasswordException e) {
                return e.getMessage();
            }

        } catch (NoSuchAlgorithmException e) {
            return "Ошибка сервера";
        }
    }

    /**
     * Генерирует случайную "соль" для безопасного хэширования паролей.
     *
     * <p>Используется алгоритм {@link SecureRandom} для создания 16-байтового массива случайных данных,
     * который затем кодируется в строку Base64.</p>
     *
     * @return строковое представление сгенерированной "соли"
     */
    private String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] saltBytes = new byte[16];
        random.nextBytes(saltBytes);
        return Base64.getEncoder().encodeToString(saltBytes);
    }

    /**
     * Хэширует входную строку с использованием алгоритма MD5.
     *
     * <p>Входная строка преобразуется в массив байтов, который затем хэшируется с помощью
     * {@link MessageDigest}. Результат кодируется в строку Base64.</p>
     *
     * @param input входная строка для хэширования
     * @return захэшированная строка в формате Base64
     * @throws NoSuchAlgorithmException если алгоритм MD5 недоступен
     */
    private String hashPassword(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] hashBytes = md.digest(input.getBytes());
        return Base64.getEncoder().encodeToString(hashBytes);
    }

    /**
     * Генерирует уникальный токен авторизации.
     *
     * <p>Используется класс {@link UUID} для создания уникального идентификатора.</p>
     *
     * @return уникальный токен в виде строки
     */
    public String generateToken() {
        return UUID.randomUUID().toString();
    }
}