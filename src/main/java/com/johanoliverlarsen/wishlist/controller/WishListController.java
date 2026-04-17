package com.johanoliverlarsen.wishlist.controller;

import com.johanoliverlarsen.wishlist.exception.InvalidWishListException;
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

    @GetMapping("/{profileId}")
    public String list(@PathVariable int profileId, Model model) {
        model.addAttribute("wishlists", wishListService.findAllByProfileId(profileId));
        return "wishlists/wishlist-list";
    }

    @GetMapping("/{profileId}/create-wishlist")
    public String showCreateForm(@PathVariable int profileId, Model model) {
        model.addAttribute("wishlist", new WishList());
        model.addAttribute("formTitle", "Opret ønskeliste");
        model.addAttribute("formAction", "profile" + profileId); //redirect til post endpoint ved submit
        model.addAttribute("submitLabel", "Opret");
        return "wishlists/wishlist-form";
    }

    @PostMapping("/{profileId}")
    public String create(@ModelAttribute WishList wishList, @PathVariable int profileId, Model model) {
        try{
            wishListService.create(wishList, profileId);
            return "redirect:/profile/{profileId}";
        }catch (InvalidWishListException ex) {
            model.addAttribute("wishlist", new WishList());
            model.addAttribute("formTitle", "Opret ønskeliste");
            model.addAttribute("formAction", "profile" + profileId);
            model.addAttribute("submitLabel", "Opret");
            model.addAttribute("errorMessage", ex.getMessage());
            return "wishlists/wishlist-form";
        }
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
