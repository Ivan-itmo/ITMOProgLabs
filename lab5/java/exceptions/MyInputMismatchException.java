package exceptions;

public class MyInputMismatchException extends RuntimeException {
    public MyInputMismatchException(String message) {
        super(message);
    }
}