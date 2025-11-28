package serverutility;

import java.io.*;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ServerReader {
    ByteBuffer buffer;

    public ServerReader(ByteBuffer buffer) {
        this.buffer = buffer;
    }


    public void handleRead(DatagramChannel dc, ServerCommand serverCommand, SelectionKey key)
            throws IOException, ClassNotFoundException {

        ByteBuffer buffer = ByteBuffer.allocate(1024 * 4);
        SocketAddress clientAddr = dc.receive(buffer);

        if (clientAddr != null) {
            System.out.println("Данные получены от " + clientAddr);

            try (ByteArrayInputStream bais = new ByteArrayInputStream(buffer.array(), 0, buffer.position());
                 ObjectInputStream ois = new ObjectInputStream(bais)) {

                String className = ois.readObject().toString().split("@")[0];
                String commandName = className.substring(className.lastIndexOf('.') + 1).toLowerCase();

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

                serverCommand.execute(commandName, args);

                key.attach(clientAddr);
                key.interestOps(SelectionKey.OP_WRITE);
            }
        }
    }

}
