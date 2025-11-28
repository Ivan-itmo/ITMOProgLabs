package clientutility;

import models.LabWork;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.HashMap;
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
 *   <li>Фоновый поток для постоянного приёма сообщений от сервера</li>
 * </ul>
 */
public class ClientConnection {
    private final int port;
    private final InetAddress host;

    private DatagramChannel channel;
    private Selector selector;

    private final int MAX_RETRIES = 0;
    private final int RESPONSE_TIMEOUT_MS = 5000;
    private final ByteBuffer receiveBuffer = ByteBuffer.allocate(8192);

    private Thread receiverThread;
    private volatile boolean isRunning = true;

    private volatile List<Object> lastResponse;

    private volatile HashMap<LabWork, String> lastCollection;

    public ClientConnection(int port) throws IOException {
        this.port = port;
        this.host = InetAddress.getLocalHost();
        initChannel();
    }

    private void initChannel() throws IOException {
        channel = DatagramChannel.open();
        channel.bind(new InetSocketAddress(0)); // свободный порт
        channel.configureBlocking(false);
        channel.connect(new InetSocketAddress(host, port));

        selector = Selector.open();
        channel.register(selector, SelectionKey.OP_WRITE);

        startReceiver();

        System.out.println("Клиент: канал — " + channel.getLocalAddress());
    }

    private void startReceiver() {
        receiverThread = new Thread(() -> {
            try (Selector readSelector = Selector.open()) {
                channel.register(readSelector, SelectionKey.OP_READ);

                while (isRunning) {
                    int ready = readSelector.select(500);
                    if (ready == 0) continue;

                    Set<SelectionKey> keys = readSelector.selectedKeys();
                    for (SelectionKey key : keys) {
                        if (key.isReadable()) {
                            receiveBuffer.clear();
                            SocketAddress addr = channel.receive(receiveBuffer);
                            if (addr != null) {
                                receiveBuffer.flip();
                                try (ObjectInputStream ois = new CustomObjectInputStream(
                                        new ByteArrayInputStream(receiveBuffer.array(), 0, receiveBuffer.limit()))) {
                                    Object obj = ois.readObject();
                                    if (obj instanceof ServerResponse response) {
                                        lastResponse = response.asObjectList();
                                        lastCollection = response.getCollectionSnapshot();
                                        if (response.getCommandResponse() != List.of()) {
                                            System.out.println("Ответ на команду:");
                                            response.getCommandResponse().forEach(System.out::println);
                                        }
                                        //System.out.println("Текущая коллекция:");
                                        //response.getCollectionSnapshot().forEach((labWork, color) ->
                                                //System.out.println(labWork + " — цвет: " + color));
                                    } else {
                                        System.out.println("Получен неизвестный объект: " + obj.getClass());
                                    }
                                } catch (Exception e) {
                                    System.err.println("Ошибка приёма:");
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                    keys.clear();
                }
            } catch (IOException e) {
                System.err.println("Ошибка приёма:");
                e.printStackTrace();
            }
        });

        receiverThread.setDaemon(true);
        receiverThread.start();
    }

    public List<Object> work(List<Object> data) throws IOException, InterruptedException, SocketTimeoutException {
        // Сериализуем данные
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            for (Object obj : data) {
                oos.writeObject(obj);
            }
        }
        ByteBuffer buffer = ByteBuffer.wrap(baos.toByteArray());

        lastResponse = null; // сброс перед отправкой

        // Отправляем данные один раз
        channel.write(buffer);
        System.out.println("Данные отправлены на сервер.");

        // Ждём ответ в течение RESPONSE_TIMEOUT_MS
        long startWait = System.currentTimeMillis();
        while (lastResponse == null && System.currentTimeMillis() - startWait < RESPONSE_TIMEOUT_MS) {
            Thread.sleep(50);
        }

        if (lastResponse == null) {
            throw new SocketTimeoutException("Таймаут ожидания ответа от сервера");
        }

        return lastResponse;
    }

    public void stop() {
        isRunning = false;
        if (selector != null) selector.wakeup();
        if (receiverThread != null) receiverThread.interrupt();
        try {
            if (channel != null) channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Object> getLastResponse() {
        return lastResponse;
    }

    public HashMap<LabWork, String> getLastCollection() {
        return lastCollection;
    }
}