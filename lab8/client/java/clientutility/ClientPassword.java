package clientutility;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс ClientPassword обеспечивает обработку операций аутентификации,
 * включая регистрацию и вход в систему. Управляет токеном доступа пользователя.
 *
 * <p>Основные функции:
 * <ul>
 *   <li>Отправка учетных данных на сервер</li>
 *   <li>Обработка ответов сервера</li>
 *   <li>Хранение токена аутентификации</li>
 *   <li>Обработка ошибок авторизации</li>
 * </ul>
 */
public class ClientPassword {
    /** Соединение с сервером */
    private ClientConnection clientConnection;

    /** Токен аутентификации (null если пользователь не авторизован) */
    private String color = null;

    /**
     * Создает экземпляр ClientPassword с указанным соединением.
     *
     * @param clientConnection соединение с сервером для отправки запросов
     * @throws IOException если возникает ошибка при инициализации соединения
     */
    public ClientPassword(ClientConnection clientConnection) throws IOException {
        this.clientConnection = clientConnection;
    }

    /**
     * Выполняет операцию аутентификации (регистрация/вход).
     *
     * <p>Последовательность работы:
     * <ol>
     *   <li>Формирует запрос с инструкцией и учетными данными</li>
     *   <li>Отправляет запрос на сервер</li>
     *   <li>Обрабатывает ответ сервера</li>
     *   <li>Сохраняет токен при успешной аутентификации</li>
     *   <li>Выводит сообщение об ошибке при неудаче</li>
     * </ol>
     *
     * @param instruction тип операции ("register" или "login")
     * @param login логин пользователя
     * @param password пароль пользователя
     * @throws IOException если возникает ошибка при взаимодействии с сервером
     */
    public void clientPasswordWork(String instruction, String login, String password) throws IOException, InterruptedException {
        List<Object> data = new ArrayList<>();
        data.add(instruction);
        data.add(this.color);
        data.add(login);
        data.add(password);

        clientConnection.work(data);

// (Желательно подождать чуть-чуть — можно цикл ожидания):
        for (int i = 0; i < 20; i++) {
            List<Object> response = clientConnection.getLastResponse();
            if (response != null) {
                String answer = (String) response.get(0);
                if (!answer.contains("Ошибка")) {
                    color = answer;
                } else {
                    System.out.println(answer);
                }
                break;
            }
            Thread.sleep(100); // подождать немного, пока поток приёма обработает ответ
        }
    }

    /**
     * Возвращает текущий токен аутентификации.
     *
     * @return токен доступа или null если пользователь не авторизован
     */
    public String getColor() {
        return color;
    }
}