package exceptions;

public class MyInvalidRangeException extends RuntimeException {
    public MyInvalidRangeException(String message) {
        super(message);
    }
}
