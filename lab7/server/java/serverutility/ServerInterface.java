package serverutility;

import java.io.IOException;
import java.nio.channels.DatagramChannel;

/**
 * Интерфейс для работы сервера, принимающего и обрабатывающего запросы.
 */
public interface ServerInterface {


    void initializeChannels() throws IOException;

    int findAvailablePort(int startPort) throws IOException;

    void start();

    void handleReceives(DatagramChannel channel);


    /**
     * Останавливает работу сервера.
     */
    //void stop();

}