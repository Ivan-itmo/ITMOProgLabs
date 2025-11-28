package serverutility;

import java.io.*;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.List;

public class ResponseSender implements Runnable {
    private final DatagramChannel channel;
    private final SocketAddress client;
    private final List<Object> response;

    public ResponseSender(DatagramChannel channel, SocketAddress client, List<Object> response) {
        this.channel = channel;
        this.client = client;
        this.response = response;
    }

    @Override
    public void run() {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {

            for (Object o : response) {
                oos.writeObject(o);
            }
            oos.flush();

            ByteBuffer buffer = ByteBuffer.wrap(baos.toByteArray());
            channel.send(buffer, client);
        } catch (IOException e) {
            System.err.println("Ошибка отправки: " + e.getMessage());
        }
    }
}