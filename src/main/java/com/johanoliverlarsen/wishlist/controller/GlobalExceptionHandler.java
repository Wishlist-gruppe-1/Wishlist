package com.johanoliverlarsen.wishlist.controller;

import com.johanoliverlarsen.wishlist.exception.DatabaseOperationException;
import com.johanoliverlarsen.wishlist.exception.ProfileNotFoundException;
import com.johanoliverlarsen.wishlist.exception.WishListNotFoundException;
import com.johanoliverlarsen.wishlist.exception.WishNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
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
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleDatabaseOperation(DatabaseOperationException ex, Model model) {
        model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        model.addAttribute("error", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        model.addAttribute("message", "Der er sket en database-fejl.");
        return "error/500";
    }

    @ExceptionHandler(ProfileNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleProfileNotFound(ProfileNotFoundException ex, Model model) {
        model.addAttribute("status", HttpStatus.NOT_FOUND.value());
        model.addAttribute("error", HttpStatus.NOT_FOUND.getReasonPhrase());
        model.addAttribute("message", ex.getMessage());
        return "error/404";

    }

    @ExceptionHandler(WishNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleWishNotFound(WishNotFoundException ex, Model model) {
        model.addAttribute("status", HttpStatus.NOT_FOUND.value());
        model.addAttribute("error", HttpStatus.NOT_FOUND.getReasonPhrase());
        model.addAttribute("message", ex.getMessage());
        return "error/404";

    }

    @ExceptionHandler(WishListNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleWishListNotFound(WishListNotFoundException ex, Model model) {
        model.addAttribute("status", HttpStatus.NOT_FOUND.value());
        model.addAttribute("error", HttpStatus.NOT_FOUND.getReasonPhrase());
        model.addAttribute("message", ex.getMessage());
        return "error/404";
    }

}
