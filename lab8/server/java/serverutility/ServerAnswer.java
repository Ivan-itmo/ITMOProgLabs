package serverutility;

import models.LabWork;
import utility.CollectionManager;

import java.io.*;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;

public class ServerAnswer implements Runnable {
    private final DatagramChannel channel;
    private final SocketAddress client;
    private final List<Object> response;
    private final HashMap<LabWork, String> collectionSnapshot;

    public ServerAnswer(DatagramChannel channel,
                        SocketAddress client,
                        List<Object> response,
                        CollectionManager collectionManager) {
        this.channel = channel;
        this.client = client;
        this.response = response;
        this.collectionSnapshot = collectionManager.getCollectionLabWorkLogin();
    }

    @Override
    public void run() {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {

            // Создаем единый объект ServerResponse
            ServerResponse serverResponse = new ServerResponse(response, collectionSnapshot);

            // Отправляем единый объект
            oos.writeObject(serverResponse);
            oos.flush();

            ByteBuffer buffer = ByteBuffer.wrap(baos.toByteArray());
            channel.send(buffer, client);
            System.out.println("Server answer sent to " + client);
        } catch (IOException e) {
            System.err.println("Ошибка отправки клиенту: " + e.getMessage());
        }
    }

    public static void broadcastToAll(DatagramChannel channel,
                                      Set<SocketAddress> clients,
                                      CollectionManager collectionManager,
                                      ExecutorService senderPool) {
        HashMap<LabWork, String> snapshot = collectionManager.getCollectionLabWorkLogin();
        for (SocketAddress client : clients) {
            senderPool.execute(new ServerAnswer(channel, client, List.of(), collectionManager));
        }
    }
}
