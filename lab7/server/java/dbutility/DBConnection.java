package dbutility;
import java.sql.*;
import com.jcraft.jsch.*;

/**
 * Класс для установки соединения с базой данных через SSH-туннель.
 * Обеспечивает безопасное подключение к СУБД через туннелированное соединение.
 *
 * <p>Основные функции:
 * <ul>
 *   <li>Создание SSH-соединения с указанными параметрами</li>
 *   <li>Настройка перенаправления портов (port forwarding)</li>
 *   <li>Установка соединения с PostgreSQL базой данных</li>
 *   <li>Автоматическое управление параметрами подключения</li>
 * </ul>
 *
 * <p>Требования:
 * <ul>
 *   <li>Библиотека JSch для работы с SSH</li>
 *   <li>JDBC драйвер PostgreSQL</li>
 * </ul>
 */
public class DBConnection {
    /** Хост SSH-сервера */
    private String SSH_HOST;

    /** Порт SSH-сервера */
    private int SSH_PORT;

    /** Имя пользователя SSH */
    private String SSH_USER;

    /** Пароль SSH */
    private String SSH_PASSWORD;

    /** URL базы данных */
    private String DB_URL;

    /** Пользователь базы данных */
    private String DB_USER;

    /** Пароль базы данных */
    private String DB_PASSWORD;

    /**
     * Создает новый экземпляр DBConnection с параметрами подключения.
     *
     * @param SSH_HOST хост SSH-сервера
     * @param SSH_PORT порт SSH-сервера
     * @param SSH_USER пользователь SSH
     * @param SSH_PASSWORD пароль SSH
     * @param DB_URL URL базы данных (например, "jdbc:postgresql://localhost:5432/dbname")
     * @param DB_USER пользователь базы данных
     * @param DB_PASSWORD пароль базы данных
     */
    public DBConnection(String SSH_HOST, int SSH_PORT, String SSH_USER,
                        String SSH_PASSWORD, String DB_URL,
                        String DB_USER, String DB_PASSWORD) {
        this.SSH_HOST = SSH_HOST;
        this.SSH_PORT = SSH_PORT;
        this.SSH_USER = SSH_USER;
        this.SSH_PASSWORD = SSH_PASSWORD;
        this.DB_URL = DB_URL;
        this.DB_USER = DB_USER;
        this.DB_PASSWORD = DB_PASSWORD;
    }

    /**
     * Устанавливает соединение с базой данных через SSH-туннель.
     *
     * <p>Последовательность работы:
     * <ol>
     *   <li>Создает SSH-сессию с указанными параметрами</li>
     *   <li>Настраивает перенаправление портов (localhost:5432 → pg:5432)</li>
     *   <li>Загружает JDBC-драйвер PostgreSQL</li>
     *   <li>Устанавливает соединение с БД</li>
     * </ol>
     *
     * @return активное соединение с базой данных
     * @throws JSchException при ошибках SSH-соединения
     * @throws ClassNotFoundException если не найден JDBC-драйвер
     * @throws SQLException при ошибках подключения к БД
     */
    public Connection getConnection() throws JSchException, ClassNotFoundException, SQLException {
        Session sshSession;
        Connection dbConnection;

        sshSession = new JSch().getSession(SSH_USER, SSH_HOST, SSH_PORT);
        sshSession.setPassword(SSH_PASSWORD);
        sshSession.setConfig("StrictHostKeyChecking", "no");
        sshSession.connect();
        sshSession.setPortForwardingL(5432, "pg", 5432);
        System.out.println("SSH-туннель успешно создан");

        Class.forName("org.postgresql.Driver");
        dbConnection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        System.out.println("Подключение к БД установлено");

        return dbConnection;
    }
}