package clientutility;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.ArrayList;
import java.util.Set;
import java.util.List;

/**
 * Класс ClientConnection обеспечивает UDP-соединение между клиентом и сервером
 * с использованием неблокирующего I/O и селектора. Управляет повторными попытками
 * соединения, таймаутами и сериализацией/десериализацией данных.
 *
 * <p>Реализует надежную UDP-коммуникацию со следующими возможностями:
 * <ul>
 *   <li>Автоматические повторные попытки передачи при сбоях (до MAX_RETRIES раз)</li>
 *   <li>Настраиваемый таймаут ожидания ответа</li>
 *   <li>Неблокирующие I/O операции</li>
 *   <li>Сериализация объектов для передачи сложных данных</li>
 * </ul>
 */
public class ClientConnection {
    /** Порт сервера */
    int port;

    /** Канал для обмена датаграммами */
    DatagramChannel dc;

    /** Адрес сервера */
    InetAddress host;

    /** Селектор для управления неблокирующим I/O */
    Selector selector;

    /** Флаг работы клиента */
    private volatile boolean isRunning = true;

    /** Счетчик попыток повторной отправки */
    private int retryCount = 0;

    /** Максимальное количество попыток повторной отправки */
    private final int MAX_RETRIES = 3;

    /** Таймаут ожидания ответа в миллисекундах */
    private final int RESPONSE_TIMEOUT_MS = 5000;

    /**
     * Создает новое подключение к указанному порту.
     * Инициализирует соединение с localhost.
     *
     * @param port номер порта сервера
     * @throws IOException если произошла ошибка ввода/вывода при инициализации
     */
    public ClientConnection(int port) throws IOException {
        this.port = port;
        host = InetAddress.getLocalHost();
        selector = Selector.open();
    }

    /**
     * Отправляет данные на сервер и ожидает ответ.
     * Реализует логику повторных попыток и обработки таймаутов.
     *
     * <p>Метод выполняет следующие операции:
     * <ol>
     *   <li>Сериализует список данных в байтовый буфер</li>
     *   <li>Отправляет данные на сервер с использованием неблокирующего I/O</li>
     *   <li>Ожидает ответ с обработкой таймаутов</li>
     *   <li>Повторяет отправку при отсутствии ответа (до MAX_RETRIES раз)</li>
     *   <li>Десериализует ответ сервера в список объектов</li>
     * </ol>
     *
     * @param data список объектов для отправки на сервер
     * @return список объектов, полученных от сервера
     * @throws IOException если произошла ошибка ввода/вывода при коммуникации
     * @throws SocketTimeoutException если сервер не ответил в течение заданного времени
     */
    public List<Object> work(List<Object> data) throws IOException {
        System.out.println("Клиент отправляет данные на сервер: " + host.getHostAddress());

        dc = DatagramChannel.open();
        dc.configureBlocking(false);
        dc.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        for (Object o : data) {
            objectOutputStream.writeObject(o);
        }
        objectOutputStream.close();

        byte[] buffer = byteArrayOutputStream.toByteArray();
        ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);

        boolean waitingForResponse = true;
        long requestStartTime = System.currentTimeMillis();

        while (waitingForResponse && isRunning) {
            int readyChannels = selector.select(RESPONSE_TIMEOUT_MS);

            if (readyChannels == 0) {
                if (System.currentTimeMillis() - requestStartTime > RESPONSE_TIMEOUT_MS * MAX_RETRIES) {
                    throw new SocketTimeoutException("Сервер недоступен: превышено время ожидания");
                }

                if (retryCount < MAX_RETRIES) {
                    retryCount++;
                    System.out.println("Повторная попытка " + retryCount + "/" + MAX_RETRIES);
                    dc.send(byteBuffer.duplicate(), new InetSocketAddress(host, port));
                    continue;
                } else {
                    throw new SocketTimeoutException("Сервер недоступен после " + MAX_RETRIES + " попыток");
                }
            }

            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            for (SelectionKey key : selectedKeys) {
                if (key.isWritable()) {
                    dc.send(byteBuffer, new InetSocketAddress(host, port));
                    System.out.println("Данные отправлены на сервер.");
                    key.interestOps(SelectionKey.OP_READ);
                    retryCount = 0; // Сброс счетчика при успешной отправке
                }

                if (key.isReadable()) {
                    ByteBuffer receiveBuffer = ByteBuffer.allocate(1024 * 8);
                    SocketAddress serverAddr = dc.receive(receiveBuffer);
                    if (serverAddr != null) {
                        receiveBuffer.flip();

                        try (ByteArrayInputStream bais = new ByteArrayInputStream(
                                receiveBuffer.array(), 0, receiveBuffer.limit());
                             ObjectInputStream ois = new ObjectInputStream(bais)) {

                            List<Object> answer = new ArrayList<>();
                            try {
                                while (true) {
                                    Object response = ois.readObject();
                                    answer.add(response);
                                }
                            } catch (EOFException e) {
                                // Конец потока достигнут - нормальное завершение
                            }

                            waitingForResponse = false;
                            return answer;

                        } catch (IOException | ClassNotFoundException e) {
                            throw new RuntimeException("Ошибка десериализации ответа", e);
                        }
                    }
                }
            }
            selectedKeys.clear();
        }

        dc.close();
        return null;
    }

    /**
     * Останавливает работу клиента.
     * Устанавливает флаг isRunning в false и пробуждает селектор.
     */
    public void stop() {
        System.out.println("Клиент завершает работу...");
        isRunning = false;
        if (selector != null) {
            selector.wakeup();
        }
    }
}