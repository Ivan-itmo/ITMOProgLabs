package utility;
import java.io.Serializable;
import java.util.List;

/**
 * Класс CommandData представляет контейнер для передачи командных данных между клиентом и сервером.
 * Содержит все необходимые данные для выполнения команды, включая аутентификационную информацию.
 * Реализует интерфейс Serializable для поддержки сериализации при передаче по сети.
 *
 * <p>Структура данных включает:
 * <ul>
 *   <li>Имя команды</li>
 *   <li>Токен аутентификации</li>
 *   <li>Учетные данные пользователя</li>
 *   <li>Аргументы команды</li>
 * </ul>
 */
public class CommandData implements Serializable {
    /** Название команды */
    private final String commandName;

    /** Токен аутентификации пользователя */
    private final String token;

    /** Логин пользователя */
    private final String login;

    /** Пароль пользователя */
    private final String password;

    /** Список аргументов команды */
    private final List<Object> args;

    /**
     * Создает новый объект CommandData с полным набором параметров.
     *
     * @param commandName название выполняемой команды
     * @param token токен аутентификации (может быть null)
     * @param login логин пользователя (может быть null)
     * @param password пароль пользователя (может быть null)
     * @param args список аргументов команды
     */
    public CommandData(String commandName, String token, String login, String password, List<Object> args) {
        this.commandName = commandName;
        this.token = token;
        this.login = login;
        this.password = password;
        this.args = args;
    }

    /**
     * Возвращает название команды.
     *
     * @return название команды
     */
    public String getCommandName() {
        return commandName;
    }

    /**
     * Возвращает токен аутентификации.
     *
     * @return токен пользователя (может быть null)
     */
    public String getToken() {
        return token;
    }

    /**
     * Возвращает логин пользователя.
     *
     * @return логин пользователя (может быть null)
     */
    public String getLogin() {
        return login;
    }

    /**
     * Возвращает пароль пользователя.
     *
     * @return пароль пользователя (может быть null)
     */
    public String getPassword() {
        return password;
    }

    /**
     * Возвращает список аргументов команды.
     *
     * @return список аргументов (может быть пустым)
     */
    public List<Object> getArgs() {
        return args;
    }
}