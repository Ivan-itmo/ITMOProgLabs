package exceptions;

/**
 * Исключение, выбрасываемое при вводе неверной команды.
 * <p>
 * Это исключение является подклассом {@link RuntimeException} и используется для обработки
 * ошибок, связанных с вводом неверной или неподдерживаемой команды. Например, когда
 * пользователь вводит команду, которая не существует или не может быть выполнена.
 * </p>
 */
public class WrongCommandException extends RuntimeException {

    /**
     * Создает новое исключение с указанным сообщением.
     *
     * @param message сообщение, описывающее причину исключения.
     */
    public WrongCommandException(String message) {
        super(message);
    }
}