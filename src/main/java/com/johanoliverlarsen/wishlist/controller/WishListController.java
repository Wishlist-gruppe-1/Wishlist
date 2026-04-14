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
        this.WishListService = wishListService;
    }

    @GetMapping()
    public String list(Model model) {

    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {

    }

    @PostMapping
    public String create(@ModelAttribute WishList wishList, Model model) {

    }


    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable int id, Model model) {

    }

    @PostMapping("/{id}")
    public String update(@PathVariable int id, @ModelAttribute WishList wishList, Model model) {

    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable int id) {

    }


}
