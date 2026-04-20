package com.johanoliverlarsen.wishlist.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class LoginController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping
    public String login() {
        //hvis login false - redirect login
        //hvis

        return "";
    }

    @GetMapping
    public String register() {
        return "redirect:/create";
    }



}
