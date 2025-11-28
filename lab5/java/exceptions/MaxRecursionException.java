package exceptions;

public class MaxRecursionException extends RuntimeException {
    public MaxRecursionException(String message) {
        super(message);
    }
}