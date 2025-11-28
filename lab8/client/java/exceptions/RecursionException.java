package exceptions;

/**
 * Исключение, выбрасываемое при обнаружении рекурсии в процессе выполнения.
 * <p>
 * Это исключение является подклассом {@link RuntimeException} и используется для обработки
 * ошибок, связанных с рекурсией. Например, когда рекурсивный вызов превышает допустимую глубину
 * или приводит к зацикливанию.
 * </p>
 */
public class RecursionException extends RuntimeException {

    /**
     * Создает новое исключение с указанным сообщением.
     *
     * @param message сообщение, описывающее причину исключения.
     */
    public RecursionException(String message) {
        super(message);
    }
}