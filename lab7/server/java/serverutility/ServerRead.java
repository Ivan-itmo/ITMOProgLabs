package serverutility;

import exceptions.NotAvtorisationException;
import utility.PasswordManager;

import java.io.*;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RecursiveAction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Класс для обработки входящих запросов от клиентов в отдельном потоке.
 * Наследуется от RecursiveAction для выполнения в ForkJoinPool.
 *
 * <p><b>Принцип работы:</b>
 * <ol>
 *   <li>Десериализует входящие данные</li>
 *   <li>Определяет тип команды</li>
 *   <li>Проверяет аутентификацию пользователя (если требуется)</li>
 *   <li>Выполняет команду через ServerCommand</li>
 *   <li>Отправляет ответ клиенту через пул отправителей</li>
 * </ol>
 *
 * <p><b>Особенности обработки:</b>
 * <ul>
 *   <li>Поддержка команд аутентификации (login/registration)</li>
 *   <li>Проверка токена доступа для защищенных команд</li>
 *   <li>Обработка аргументов команды</li>
 *   <li>Централизованная обработка исключений</li>
 * </ul>
 */
public class ServerRead extends RecursiveAction {
    /** Входящие данные от клиента */
    private final byte[] data;

    /** Адрес клиента */
    private final SocketAddress client;

    /** Канал для отправки ответа */
    private final DatagramChannel channel;

    /** Пул потоков для отправки ответов */
    private final ExecutorService senderPool;

    /** Обработчик команд сервера */
    private final ServerCommand serverCommand;

    /**
     * Создает новый экземпляр обработчика запроса.
     *
     * @param data входящие данные от клиента
     * @param client адрес клиента
     * @param channel канал для отправки ответа
     * @param senderPool пул потоков для отправки ответов
     * @param serverCommand обработчик команд сервера
     */
    public ServerRead(byte[] data, SocketAddress client,
                      DatagramChannel channel,
                      ExecutorService senderPool,
                      ServerCommand serverCommand) {
        this.data = data;
        this.client = client;
        this.channel = channel;
        this.senderPool = senderPool;
        this.serverCommand = serverCommand;
    }

    /**
     * Основной метод обработки запроса.
     *
     * <p><b>Алгоритм работы:</b>
     * <ol>
     *   <li>Десериализация входящих данных</li>
     *   <li>Определение имени команды</li>
     *   <li>Проверка наличия токена аутентификации</li>
     *   <li>Обработка команд аутентификации (login/registration)</li>
     *   <li>Выполнение команды через ServerCommand</li>
     *   <li>Обработка исключений и формирование ответа</li>
     * </ol>
     */
    @Override
    protected void compute() {
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data))) {
            String className = ois.readObject().toString().split("@")[0];
            String commandName = className.substring(className.lastIndexOf('.') + 1).toLowerCase();

            Object token = ois.readObject();
            if (token != null) {
                String login = (String) ois.readObject();
                String password = (String) ois.readObject();

                Object argsObj = ois.readObject();
                List<Object> args = argsObj != null ?
                        IntStream.range(0, Integer.parseInt(argsObj.toString()))
                                .mapToObj(i -> {
                                    try {
                                        return ois.readObject();
                                    } catch (Exception e) {
                                        throw new RuntimeException(e);
                                    }
                                })
                                .collect(Collectors.toList()) :
                        Collections.emptyList();

                List<Object> response = serverCommand.execute(commandName, args, login);
                sendResponse(response);
            }
            else {
                try {
                    if (commandName.equals("login") || commandName.equals("registration")) {
                        PasswordManager passwordManager = serverCommand.getPasswordManager();
                        String username = ois.readObject().toString();
                        String password = ois.readObject().toString();
                        String answer;
                        if (commandName.equals("login")) {
                            answer = passwordManager.login(username, password);
                        }
                        else {
                            answer = passwordManager.registration(username, password);
                        }
                        List<Object> response = new ArrayList<>();
                        response.add(answer);
                        sendResponse(response);
                    }
                    else {
                        throw new NotAvtorisationException("Ошибка: пользователь не авторизован");
                    }
                }
                catch (NotAvtorisationException e) {
                    List<Object> response = new ArrayList<>();
                    response.add(e.getMessage());
                    sendResponse(response);
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка обработки запроса: " + e.getMessage());
            List<Object> response = new ArrayList<>();
            response.add(e.getMessage());
            sendResponse(response);
        }
    }

    /**
     * Отправляет ответ клиенту через пул отправителей.
     *
     * @param response список объектов для отправки
     */
    private void sendResponse(List<Object> response) {
        senderPool.execute(new ServerAnswer(channel, client, response));
    }
}