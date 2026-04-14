package Exeption;

public class InvalidWishListException extends RuntimeException {
    public InvalidWishListException(String message) {
        super(message);
    }
}
