package com.johanoliverlarsen.wishlist.exception;

public class WishNotFoundException extends RuntimeException {
    public WishNotFoundException(int id) {
        super("Ønsket med id " + id + " kunne ikke findes.");
    }
}
