package Exeption;

public class InvalidWishException extends RuntimeException {
    public InvalidWishException(String message) {
        super(message);
    }
}
