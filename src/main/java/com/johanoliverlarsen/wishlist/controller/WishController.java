package com.johanoliverlarsen.wishlist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping


public class WishController {
    /*
    + list(model: Model): string
    + showCreateForm(model: Model): string
    + create(wish: Wish, model: Model): string
    + showEditForm(id: int, model: Model): string
    + update(id: int, wish: Wish, model: Model): string
    + delete(id: int): string public WishController (WishService service) {
        this.service = service;

     */

    

    @GetMapping()
    public String home() {

    }


    @GetMapping()
    public String getAllWishes() {


    }

    @GetMapping("/{title}")
    public String getWishByTitle() {

    }

}
