package clientutility;

import utility.CommandData;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Set;

public class Client {
    int port;
    DatagramChannel dc;
    InetAddress host;
    Selector selector;
    private volatile boolean isRunning = true;
    private int retryCount = 0;
    private final int MAX_RETRIES = 3;
    private final int RESPONSE_TIMEOUT_MS = 5000;

    public Client(int port) throws IOException {
        this.port = port;
        host = InetAddress.getLocalHost();
        selector = Selector.open();
    }

    public void clientWork(CommandData commandData) throws IOException {
        System.out.println("Клиент отправляет данные на сервер: " + host.getHostAddress());

        dc = DatagramChannel.open();
        dc.configureBlocking(false);
        dc.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(commandData.getCommandName());
        objectOutputStream.writeObject(commandData.getArgs().size());
        for (Object o : commandData.getArgs()) {
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

                            try {
                                while (true) {
                                    Object response = ois.readObject();
                                    System.out.println(response);
                                }
                            } catch (EOFException e) {

                            }

                            waitingForResponse = false;
                        } catch (ClassNotFoundException e) {
                            System.err.println("Ошибка: " + e.getMessage());
                        }
                    }
                }
            }
            selectedKeys.clear();
        }

        dc.close();
    }

    public void stop() {
        System.out.println("Клиент завершает работу...");
        isRunning = false;
        if (selector != null) {
            selector.wakeup();
        }
    }
}