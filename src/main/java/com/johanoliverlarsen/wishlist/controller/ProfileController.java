package com.johanoliverlarsen.wishlist.controller;
import com.johanoliverlarsen.wishlist.model.Profile;
import com.johanoliverlarsen.wishlist.service.ProfileService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/profile")

/*
- profileService: ProfileService

+ list(model: Model): string
+ showCreateForm(model: Model): string
+ create(profile: Profile, model: Model): string
+ showEditForm(id: int, model: Model): string
+ update(id: int, profile: Profile, model: Model): string
+ delete(id: int): string
 */

public class ProfileController {


    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
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
    public String create(@ModelAttribute Profile profile, Model model) {
        return null;
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable int id, Model model) {
        return null;
    }

    @PostMapping("/{id}")
    public String update(@PathVariable int id, @ModelAttribute Profile profile, Model model) {
        return null;
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable int id) {
        return null;
    }





}
