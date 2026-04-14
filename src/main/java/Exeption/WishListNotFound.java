package Exeption;

public class WishListNotFound extends RuntimeException {
    public WishListNotFound(String message) {
        super(message);
    }
}
