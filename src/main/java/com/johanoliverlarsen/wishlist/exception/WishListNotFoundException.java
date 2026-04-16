package com.johanoliverlarsen.wishlist.exception;

public class WishListNotFoundException extends RuntimeException {
    public WishListNotFoundException(int id) {
        super("Listen med id " + id + " kunne ikke findes.");
    }
}
