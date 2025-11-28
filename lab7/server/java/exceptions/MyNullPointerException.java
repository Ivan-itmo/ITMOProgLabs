package exceptions;

/**
 * Исключение, выбрасываемое при попытке использования нулевой ссылки (null).
 * <p>
 * Это исключение является подклассом {@link RuntimeException} и используется для обработки
 * ошибок, связанных с непредвиденным использованием нулевых ссылок. Оно может быть полезным
 * для явного указания на то, что значение не должно быть null, вместо стандартного
 * {@link NullPointerException}.
 * </p>
 */
public class MyNullPointerException extends RuntimeException {

    /**
     * Создает новое исключение с указанным сообщением.
     *
     * @param message сообщение, описывающее причину исключения.
     */
    public MyNullPointerException(String message) {
        super(message);
    }
}