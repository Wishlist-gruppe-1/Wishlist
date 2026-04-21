package com.johanoliverlarsen.wishlist.controller;
import com.johanoliverlarsen.wishlist.exception.DuplicateProfileException;
import com.johanoliverlarsen.wishlist.exception.InvalidProfileException;
import com.johanoliverlarsen.wishlist.model.Profile;
import com.johanoliverlarsen.wishlist.service.ProfileService;
import jakarta.servlet.http.HttpSession;
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
        model.addAttribute("profiles", profileService.findAll());
        return "profiles/profile-list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("profile", new Profile());
        model.addAttribute("formTitle", "Opret profil");
        model.addAttribute("formAction", "/profile");
        model.addAttribute("submitLabel", "Opret");

        return "auth/signup";
    }

    @PostMapping
    public String create(@ModelAttribute Profile profile, Model model) {
        try {
            profileService.create(profile);
            return "redirect:/profil";
        } catch (InvalidProfileException | DuplicateProfileException ex) {
            model.addAttribute("profile", profile);
            model.addAttribute("formTitle", "Opret profil");
            model.addAttribute("formAction", "/profile");
            model.addAttribute("submitLabel", "Opret");
            model.addAttribute("errorMessage", ex.getMessage());

            return "auth/signup";
        }
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable int id, Model model) {
        Profile profile = profileService.findById(id);
        model.addAttribute("formTitle", "Rediger profil");
        model.addAttribute("formAction", "/profile/" + id);
        model.addAttribute("submitLabel", "Opdater");
        model.addAttribute("profile", profile);

        return "profiles/profile-form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable int id, @ModelAttribute Profile profile, Model model) {
        try {
            profileService.update(id, profile);
            return "redirect:/profil";
        } catch (InvalidProfileException | DuplicateProfileException ex) {
            profile.setProfileId(id);
            model.addAttribute("profile", profile);
            model.addAttribute("formTitle", "Rediger profil");
            model.addAttribute("formAction", "/profile/" + id);
            model.addAttribute("submitLabel", "Opdater");
            model.addAttribute("errorMessage", ex.getMessage());

            return "profiles/profile-form";
        }
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable int id) {
        profileService.deleteById(id);
        return "redirect:/profil";
    }
}






