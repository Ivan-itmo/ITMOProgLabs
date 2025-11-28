package serverutility;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.*;

public class Server {
    private final int port;
    private final ServerCommand serverCommand;
    private final ForkJoinPool processingPool;
    private final ExecutorService receiverPool;
    private final ExecutorService senderPool;
    private volatile boolean isRunning;
    private DatagramChannel[] channels;

    public Server(ServerCommand serverCommand, int threads) throws IOException {
        this.serverCommand = serverCommand;
        this.processingPool = new ForkJoinPool();
        this.receiverPool = Executors.newFixedThreadPool(threads);
        this.senderPool = Executors.newFixedThreadPool(threads);
        this.port = findAvailablePort(12345);
        this.channels = new DatagramChannel[threads];

        initializeChannels();
    }

    private void initializeChannels() throws IOException {
        InetSocketAddress address = new InetSocketAddress(port);
        for (int i = 0; i < channels.length; i++) {
            channels[i] = DatagramChannel.open();
            channels[i].configureBlocking(false);

            // Включаем SO_REUSEADDR для всех ОС
            channels[i].setOption(StandardSocketOptions.SO_REUSEADDR, true);

            // Включаем SO_REUSEPORT для Linux
            if (System.getProperty("os.name").toLowerCase().contains("linux")) {
                channels[i].setOption(StandardSocketOptions.SO_REUSEPORT, true);
            }

            channels[i].bind(address);
            System.out.println("Поток " + i + " слушает порт " + port);
        }
    }

    private int findAvailablePort(int startPort) throws IOException {
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

    public void start() {
        isRunning = true;
        for (DatagramChannel channel : channels) {
            receiverPool.execute(() -> handleReceives(channel));
        }
        System.out.println("Сервер запущен на порту " + port);
    }

    private void handleReceives(DatagramChannel channel) {
        ByteBuffer buffer = ByteBuffer.allocate(2048);
        while (isRunning) {
            try {
                SocketAddress client = channel.receive(buffer);
                if (client != null) {
                    byte[] data = new byte[buffer.position()];
                    System.arraycopy(buffer.array(), 0, data, 0, data.length);
                    processingPool.execute(new RequestProcessor(data, client, channel, senderPool, serverCommand));
                    buffer.clear();
                }
            } catch (IOException e) {
                if (isRunning) {
                    System.err.println("Ошибка в потоке " + Thread.currentThread().getName() + ": " + e.getMessage());
                }
            }
        }
    }

    public void stop() {
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
    }
}