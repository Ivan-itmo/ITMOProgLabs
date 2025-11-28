package serverutility;

import java.io.*;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RecursiveAction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RequestProcessor extends RecursiveAction {
    private final byte[] data;
    private final SocketAddress client;
    private final DatagramChannel channel;
    private final ExecutorService senderPool;
    private final ServerCommand serverCommand;

    public RequestProcessor(byte[] data, SocketAddress client,
                            DatagramChannel channel,
                            ExecutorService senderPool,
                            ServerCommand serverCommand) {
        this.data = data;
        this.client = client;
        this.channel = channel;
        this.senderPool = senderPool;
        this.serverCommand = serverCommand;
    }

    @Override
    protected void compute() {
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data))) {
            // Чтение команды
            String className = ois.readObject().toString().split("@")[0];
            String commandName = className.substring(className.lastIndexOf('.') + 1).toLowerCase();

            // Безопасное чтение списка аргументов
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

            // Выполнение команды
            List<Object> response = serverCommand.execute(commandName, args);

            // Отправка ответа
            senderPool.execute(new ResponseSender(channel, client, response));
        } catch (Exception e) {
            System.err.println("Ошибка обработки запроса: " + e.getMessage());
            List<Object> response = new ArrayList<>();
            response.add(e.getMessage());
            senderPool.execute(new ResponseSender(channel, client, response));
        }

    }
}