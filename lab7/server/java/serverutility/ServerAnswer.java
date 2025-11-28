package serverutility;

import java.io.*;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.List;

/**
 * Класс для асинхронной отправки ответов клиенту через UDP-канал.
 * Реализует интерфейс Runnable для выполнения в отдельном потоке.
 *
 * <p><b>Принцип работы:</b>
 * <ol>
 *   <li>Принимает данные для отправки (список объектов)</li>
 *   <li>Сериализует объекты в байтовый поток</li>
 *   <li>Отправляет данные через указанный DatagramChannel</li>
 * </ol>
 *
 * <p><b>Особенности:</b>
 * <ul>
 *   <li>Автоматическое управление ресурсами (try-with-resources)</li>
 *   <li>Обработка ошибок ввода-вывода</li>
 *   <li>Поддержка отправки нескольких объектов в одном пакете</li>
 * </ul>
 */
public class ServerAnswer implements Runnable {
    /** UDP-канал для отправки данных */
    private final DatagramChannel channel;

    /** Адрес клиента для отправки ответа */
    private final SocketAddress client;

    /** Список объектов для отправки */
    private final List<Object> response;

    /**
     * Создает новый экземпляр ServerAnswer.
     *
     * @param channel UDP-канал для отправки данных
     * @param client адрес клиента, которому отправляется ответ
     * @param response список объектов для отправки
     */
    public ServerAnswer(DatagramChannel channel, SocketAddress client, List<Object> response) {
        this.channel = channel;
        this.client = client;
        this.response = response;
    }

    /**
     * Выполняет отправку данных клиенту.
     *
     * <p><b>Алгоритм работы:</b>
     * <ol>
     *   <li>Создает потоки для сериализации объектов</li>
     *   <li>Последовательно записывает все объекты в выходной поток</li>
     *   <li>Преобразует данные в ByteBuffer</li>
     *   <li>Отправляет данные через UDP-канал</li>
     * </ol>
     *
     * <p>В случае ошибки ввода-вывода выводит сообщение в System.err,
     * но не прерывает выполнение потока.
     */
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