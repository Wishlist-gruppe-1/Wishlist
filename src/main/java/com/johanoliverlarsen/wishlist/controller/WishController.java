package com.johanoliverlarsen.wishlist.controller;

import com.johanoliverlarsen.wishlist.model.Wish;
import com.johanoliverlarsen.wishlist.service.WishService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/wishes")

 /*
    - wishService: WishService
    + list(model: Model): string
    + showCreateForm(model: Model): string
    + create(wish: Wish, model: Model): string
    + showEditForm(id: int, model: Model): string
    + update(id: int, wish: Wish, model: Model): string
    + delete(id: int): string public WishController (WishService service) {
        this.service = service;

     OBS!!! Ved ikke om jeg har annoteret get og post mapping rigtigt

     */

public class WishController {

    private final WishService wishService;

    public WishController(WishService wishService) {
        this.wishService = wishService;
    }

    @GetMapping()
    public String list(Model model) {
        return null;
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        return null;
    }

    @PostMapping
    public String create(@ModelAttribute Wish wish, Model model) {
        return null;
    }


    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable int id, Model model) {
        return null;
    }

    @PostMapping("/{id}")
    public String update(@PathVariable int id, @ModelAttribute Wish wish, Model model) {
        return null;
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable int id) {
        return null;
    }



}
