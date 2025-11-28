package clientutility;

import utility.CommandData;
import java.io.IOException;
import java.net.SocketTimeoutException;

public interface ClientInterface {
    /**
     * Запускает работу клиента, отправляя команду на сервер и ожидая ответа.
     *
     * @param commandData данные команды для отправки
     * @throws IOException если произошла ошибка ввода-вывода
     * @throws SocketTimeoutException если сервер не отвечает слишком долго
     */
    void clientWork(CommandData commandData) throws IOException, SocketTimeoutException, InterruptedException;

}