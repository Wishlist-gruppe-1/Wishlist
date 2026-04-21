package com.johanoliverlarsen.wishlist.controller;

import com.johanoliverlarsen.wishlist.exception.InvalidWishListException;
import com.johanoliverlarsen.wishlist.model.Profile;
import com.johanoliverlarsen.wishlist.model.WishList;
import com.johanoliverlarsen.wishlist.service.ProfileService;
import com.johanoliverlarsen.wishlist.service.WishListService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/profile/list")

public class WishListController {

    private final WishListService wishListService;
    private final ProfileService profileService;

    public WishListController(WishListService wishListService, ProfileService profileService) {
        this.wishListService = wishListService;
        this.profileService = profileService;
    }

    @GetMapping()
    public String list(HttpSession session, Model model) {
        Integer profileId = (Integer) session.getAttribute("profileId");
        if(profileId == null){
            return "redirect:/login";
        }
        Profile profile = profileService.findById(profileId);
        model.addAttribute("wishlists", wishListService.findAllByProfileId(profileId));
        model.addAttribute("profile", profile);
        return "wishlists/wishlist-list";
    }

    @GetMapping("/create")
    public String showCreateForm(HttpSession session, Model model) {
        if (session.getAttribute("profileId") == null) {
            return "redirect:/login";
        }

        model.addAttribute("wishlist", new WishList());
        model.addAttribute("formTitle", "Opret ønskeliste");
        model.addAttribute("formAction", "/wishlists"); //redirect til post endpoint ved submit
        model.addAttribute("submitLabel", "Opret");
        return "wishlists/wishlist-form";
    }


    @PostMapping
    public String create(@ModelAttribute WishList wishList, HttpSession session, Model model) {
        Integer profileId = (Integer) session.getAttribute("profileId");
        if(session.getAttribute("profileId") == null){
            return "redirect:/login";
        }

        try{
            wishListService.create(wishList, profileId);
            return "redirect:/wishlists";

        }catch (InvalidWishListException ex) {
            model.addAttribute("wishlist", wishList);
            model.addAttribute("formTitle", "Opret ønskeliste");
            model.addAttribute("formAction", "/wishLists");
            model.addAttribute("submitLabel", "Opret");
            model.addAttribute("errorMessage", ex.getMessage());
            return "wishlists/wishlist-form";
        }
    }


    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable int id, HttpSession session, Model model) {
        if(session.getAttribute("profileId") == null){
            return "redirect:/login";
        }

        WishList wishList = wishListService.findById(id);
        model.addAttribute("wishlist", wishList);
        model.addAttribute("formTitle", "Rediger ønskeliste");
        model.addAttribute("formAction",  "/wishlists/" + id);
        model.addAttribute("submit label", "Opdater");
        return "wishlists/wishlist-form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable int id, @ModelAttribute WishList wishList, HttpSession session, Model model) {
        if(session.getAttribute("profileId") == null){
            return "redirect:/login";
        }

        try{
            wishListService.update(id,wishList);
            return "redirect:/wishlists/";
        }catch(InvalidWishListException ex) {
            wishList.setWishListId(id);
            model.addAttribute("wishlist", wishList);
            model.addAttribute("formTitle", "Rediger liste");
            model.addAttribute("formAction", "/wishlist/" + id);
            model.addAttribute("submitLabel", "Opdater");
            model.addAttribute("errorMessage", ex.getMessage());
            return "wishlists/wishlist-form";
        }
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable int id, HttpSession session) {
        if(session.getAttribute("profileId") == null){
            return "redirect:/login";
        }
        wishListService.deleteById(id);
        return "redirect:/wishlists/";
    }
//tihi

}