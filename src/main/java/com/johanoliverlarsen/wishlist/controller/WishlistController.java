package com.johanoliverlarsen.wishlist.controller;

import com.johanoliverlarsen.wishlist.model.WishList;
import com.johanoliverlarsen.wishlist.service.WishListService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/wishlists")

public class WishListController {

    private final WishListService wishListService;

    public WishListController(WishListService wishListService) {
        this.wishListService = wishListService;
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
    public String create(@ModelAttribute WishList wishList, Model model) {
        return null;
    }


    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable int id, Model model) {
        return null;
    }

    @PostMapping("/{id}")
    public String update(@PathVariable int id, @ModelAttribute WishList wishList, Model model) {
        return null;
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable int id) {
        return null;
    }


}
