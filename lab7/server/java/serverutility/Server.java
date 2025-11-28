package serverutility;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.*;

/**
 * Класс сервера для обработки сетевых запросов через UDP с использованием NIO.
 * Реализует многопоточную обработку входящих соединений и асинхронное выполнение команд.
 *
 * <p><b>Архитектура сервера:</b>
 * <ul>
 *   <li>Пул потоков для приема входящих пакетов (receiverPool)</li>
 *   <li>ForkJoinPool для обработки полученных данных (processingPool)</li>
 *   <li>Пул потоков для отправки ответов (senderPool)</li>
 *   <li>Несколько DatagramChannel для балансировки нагрузки</li>
 * </ul>
 */
public class Server implements ServerInterface {
    /** Порт сервера */
    private final int port;

    /** Обработчик команд сервера */
    private final ServerCommand serverCommand;

    /** Пул для обработки задач */
    private final ForkJoinPool processingPool;

    /** Пул для приема сообщений */
    private final ExecutorService receiverPool;

    /** Пул для отправки сообщений */
    private final ExecutorService senderPool;

    /** Флаг работы сервера */
    private volatile boolean isRunning;

    /** Массив каналов для приема сообщений */
    private DatagramChannel[] channels;

    /**
     * Создает новый экземпляр сервера.
     *
     * @param serverCommand обработчик команд сервера
     * @param threads количество потоков для обработки соединений
     * @throws IOException если возникла ошибка при инициализации каналов
     */
    public Server(ServerCommand serverCommand, int threads) throws IOException {
        this.serverCommand = serverCommand;
        this.processingPool = new ForkJoinPool();
        this.receiverPool = Executors.newFixedThreadPool(threads);
        this.senderPool = Executors.newFixedThreadPool(threads);
        this.port = findAvailablePort(12345);
        this.channels = new DatagramChannel[threads];

        initializeChannels();
    }

    /**
     * Инициализирует каналы для приема сообщений.
     *
     * @throws IOException если возникла ошибка при создании или настройке каналов
     */
    public void initializeChannels() throws IOException {
        InetSocketAddress address = new InetSocketAddress(port);
        for (int i = 0; i < channels.length; i++) {
            channels[i] = DatagramChannel.open();
            channels[i].configureBlocking(false);
            channels[i].setOption(StandardSocketOptions.SO_REUSEADDR, true);
            channels[i].bind(address);
        }
    }

    /**
     * Находит доступный порт в заданном диапазоне.
     *
     * @param startPort начальный порт для поиска
     * @return номер доступного порта
     * @throws IOException если не удалось найти доступный порт в диапазоне startPort..startPort+100
     */
    public int findAvailablePort(int startPort) throws IOException {
        int currentPort = startPort;
        while (currentPort <= startPort + 100) {
            try (DatagramChannel testChannel = DatagramChannel.open()) {
                testChannel.setOption(StandardSocketOptions.SO_REUSEADDR, true);
                testChannel.bind(new InetSocketAddress(currentPort));
                return currentPort;
            } catch (IOException e) {
                currentPort++;
            }
        }
        throw new IOException("Не удалось найти доступный порт в диапазоне " + startPort + "-" + (startPort + 100));
    }

    /**
     * Запускает сервер.
     * <p>
     * Активирует потоки для приема входящих соединений на всех каналах.
     */
    public void start() {
        isRunning = true;
        for (DatagramChannel channel : channels) {
            receiverPool.execute(() -> handleReceives(channel));
        }
        System.out.println("Сервер запущен на порту " + port);
    }

    /**
     * Обрабатывает входящие сообщения на указанном канале.
     *
     * @param channel канал для приема сообщений
     */
    public void handleReceives(DatagramChannel channel) {
        ByteBuffer buffer = ByteBuffer.allocate(2048);
        while (isRunning) {
            try {
                SocketAddress client = channel.receive(buffer);
                if (client != null) {
                    byte[] data = new byte[buffer.position()];
                    System.arraycopy(buffer.array(), 0, data, 0, data.length);
                    processingPool.execute(new ServerRead(data, client, channel, senderPool, serverCommand));
                    buffer.clear();
                }
            } catch (IOException e) {
                if (isRunning) {
                    System.err.println("Ошибка в потоке " + Thread.currentThread().getName() + ": " + e.getMessage());
                }
            }
        }
    }

    /**
     * Останавливает сервер.
     * <p>
     * Выполняет корректное завершение работы:
     * <ol>
     *   <li>Устанавливает флаг isRunning в false</li>
     *   <li>Останавливает пулы потоков в правильном порядке</li>
     *   <li>Закрывает все каналы</li>
     * </ol>
     */
    /*public void stop() {
        isRunning = false;

        // Остановка в правильном порядке
        receiverPool.shutdown();
        processingPool.shutdown();
        senderPool.shutdown();

        try {
            // Даем время на завершение операций
            if (!receiverPool.awaitTermination(1, TimeUnit.SECONDS)) receiverPool.shutdownNow();
            if (!processingPool.awaitTermination(1, TimeUnit.SECONDS)) processingPool.shutdownNow();
            if (!senderPool.awaitTermination(1, TimeUnit.SECONDS)) senderPool.shutdownNow();

            // Закрываем каналы
            for (DatagramChannel ch : channels) {
                if (ch != null && ch.isOpen()) {
                    ch.close();
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка при остановке: " + e.getMessage());
        }
    }*/
}