package clientutility;

import utility.CommandData;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс Client реализует ClientInterface и предоставляет функционал
 * для отправки командных данных на сервер и получения ответов.
 * Выступает в качестве клиентского компонента, взаимодействующего с сервером
 * через соединение ClientConnection.
 */
public class Client implements ClientInterface {
    private ClientConnection clientConnection;

    /**
     * Создает новый экземпляр клиента с указанным соединением.
     *
     * @param clientConnection обработчик соединения для коммуникации с сервером
     * @throws IOException если возникает ошибка ввода/вывода при инициализации клиента
     */
    public Client(ClientConnection clientConnection) throws IOException {
        this.clientConnection = clientConnection;
    }

    /**
     * Обрабатывает переданные командные данные, отправляя их на сервер через ClientConnection,
     * и выводит полученный ответ.
     *
     * <p>Метод упаковывает командные данные в структуру List, содержащую:
     * <ol>
     *   <li>Имя команды</li>
     *   <li>Аутентификационный токен</li>
     *   <li>Логин</li>
     *   <li>Пароль</li>
     *   <li>Количество аргументов</li>
     *   <li>Все аргументы команды по порядку</li>
     * </ol>
     *
     * @param commandData командные данные для обработки и отправки на сервер
     * @throws IOException если возникает ошибка ввода/вывода при коммуникации с сервером
     */
    public void clientWork(CommandData commandData) throws IOException {
        List<Object> data = new ArrayList<>();
        data.add(commandData.getCommandName());
        data.add(commandData.getToken());
        data.add(commandData.getLogin());
        data.add(commandData.getPassword());
        data.add(commandData.getArgs().size());
        for (Object o : commandData.getArgs()) {
            data.add(o);
        }

        List<Object> response = clientConnection.work(data);
        for (Object o : response) {
            System.out.println(o);
        }
    }
}