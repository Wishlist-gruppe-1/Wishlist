package com.johanoliverlarsen.wishlist.controller;

import com.johanoliverlarsen.wishlist.exception.DuplicateProfileException;
import com.johanoliverlarsen.wishlist.exception.InvalidWishException;
import com.johanoliverlarsen.wishlist.model.Wish;
import com.johanoliverlarsen.wishlist.service.WishService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/profile/list")

public class WishController {

    private final WishService wishService;

    public WishController(WishService wishService) {
        this.wishService = wishService;
    }

    @GetMapping("/{wishListId}")
    public String list(@PathVariable int wishListId, Model model) {
        model.addAttribute("wishes", wishService.findAllByWishListId(wishListId));
        return "wishes/wish-list";
    }

    @GetMapping("/{wishListId}/create-wish")
    public String showCreateForm(@PathVariable int wishListId, Model model) {
        model.addAttribute("wish", new Wish());
        model.addAttribute("formTitle", "Opret ønske");
        model.addAttribute("formAction", "profile/list/" + wishListId); //redirect til post endpoint ved submit
        model.addAttribute("submitLabel", "Opret");
        return "wishes/wish-form";
    }

    @PostMapping("/{wishListId}")
    public String create(@ModelAttribute Wish wish, @PathVariable int wishListId, Model model) {
        try {
            wishService.create(wish, wishListId);
            return "redirect:/profile/list/{wishListId}";
        } catch (InvalidWishException ex) {
            model.addAttribute("wish", new Wish());
            model.addAttribute("formTitle", "Opret ønske");
            model.addAttribute("formAction", "profile/list/" + wishListId); //redirect til post endpoint ved submit
            model.addAttribute("submitLabel", "Opret");
            model.addAttribute("errorMessage", ex.getMessage());
            return "wishes/wish-form";
        }
    }


    @GetMapping("/{wishListId}/wish/{id}/edit")
    public String showEditForm(@PathVariable int wishListId, @PathVariable int id, Model model) {
        Wish wish = wishService.findById(id);
        model.addAttribute("wish", wish);
        model.addAttribute("formTitle", "Rediger ønske");
        model.addAttribute("formAction", "profile/list/" + wishListId + "/wish/" + id); //redirect til post endpoint ved submit
        model.addAttribute("submitLabel", "Opdater");
        return "wishes/wish-form";

    }

    @PostMapping("{wishListId}/wish/{id}")
    public String update(@PathVariable int wishListId, @PathVariable int id, @ModelAttribute Wish wish, Model model) {
        try {
            wishService.update(id, wish);
            return "redirect:/profile/list/{wishListId}";
        } catch (InvalidWishException ex) {
            wish.setWishId(id);
            model.addAttribute("wish", wish);
            model.addAttribute("formTitle", "Rediger ønske");
            model.addAttribute("formAction", "profile/list/" + wishListId + "/wish/" + id); //redirect til post endpoint ved submit
            model.addAttribute("submitLabel", "Opdater");
            model.addAttribute("errorMessage", ex.getMessage());
            return "wishes/wish-form";
        }
    }

    @PostMapping("{wishListId}/wish/{id}/delete")
    public String delete(@PathVariable int wishListId, @PathVariable int id) {
        wishService.deleteById(id);
        return "redirect:wish-list";
    }



}
