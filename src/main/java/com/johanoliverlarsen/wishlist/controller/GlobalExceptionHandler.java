package com.johanoliverlarsen.wishlist.controller;

import com.johanoliverlarsen.wishlist.exception.DatabaseOperationException;
import com.johanoliverlarsen.wishlist.exception.ProfileNotFoundException;
import com.johanoliverlarsen.wishlist.exception.WishNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/*
+ handleProfileNotFound(ex : ProfileNotFoundException, model : Model)
+ handleWishNotFound(ex : WishNotFoundException, model : Model)
+ handleDatabaseOperation(ex DatabaseOperationException, model : Model)
*/

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(DatabaseOperationException.class)
    @ResponseStatus()
    public String handleDatabaseOperation() {

    }

    @ExceptionHandler(ProfileNotFoundException.class)
    @ResponseStatus()
    public String handleProfileNotFound() {

    }

    @ExceptionHandler(WishNotFoundException.class)
    @ResponseStatus()
    public String handleWishNotFound() {

    }

    @ExceptionHandler(WishNotFoundException.class)
    @ResponseStatus()
    public String handleWishListNotFound() {


    }


}
