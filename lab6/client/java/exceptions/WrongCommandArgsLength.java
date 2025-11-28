package exceptions;

/**
 * Исключение, выбрасываемое при вводе неверной команды.
 * <p>
 * Это исключение является подклассом {@link RuntimeException} и используется для обработки
 * ошибок, связанных с вводом неверной или неподдерживаемой команды. Например, когда
 * в скрипте не то количество аргументов.
 * </p>
 */
public class WrongCommandArgsLength extends RuntimeException {

    /**
     * Создает новое исключение с указанным сообщением.
     *
     * @param message сообщение, описывающее причину исключения.
     */
    public WrongCommandArgsLength(String message) {
        super(message);
    }
}