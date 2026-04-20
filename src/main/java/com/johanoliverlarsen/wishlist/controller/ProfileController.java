package com.johanoliverlarsen.wishlist.controller;

import com.johanoliverlarsen.wishlist.exception.DuplicateProfileException;
import com.johanoliverlarsen.wishlist.exception.InvalidProfileException;
import com.johanoliverlarsen.wishlist.model.Profile;
import com.johanoliverlarsen.wishlist.service.ProfileService;
import com.johanoliverlarsen.wishlist.service.WishListService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/profile")

public class ProfileController {

    /*
- profileService: ProfileService

+ list(model: Model): string
+ showCreateForm(model: Model): string
+ create(profile: Profile, model: Model): string
+ showEditForm(id: int, model: Model): string
+ update(id: int, profile: Profile, model: Model): string
+ delete(id: int): string
 */

    private final ProfileService profileService;
    private final WishListService wishListService;

    public ProfileController(ProfileService profileService, WishListService wishListService) {
        this.profileService = profileService;
        this.wishListService = wishListService;
    }

    @GetMapping()
    public String list(Model model) {
        model.addAttribute("profiles", profileService.findAll());
        return "profiles/profile-list";
    }

    @GetMapping("/my-profile")
    public String dashboard(HttpSession session, Model model) {
        Integer profileId = (Integer) session.getAttribute("profileId");
        if (profileId == null) {
            return "redirect:/profile/login";
        }
        Profile profile = profileService.findById(profileId);
        model.addAttribute("profile", profile);
        model.addAttribute("wishlists", wishListService.findAllByProfileId(profileId));
        return "profile/my-profile";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("profile", new Profile());
        model.addAttribute("formTitle", "Opret profil");
        model.addAttribute("formAction", "/profile");
        model.addAttribute("submitLabel", "Opret");
        return "profiles/profile-form";
    }

    @PostMapping
    public String create(@ModelAttribute Profile profile, HttpSession session, Model model) {
        try {
            Profile created = profileService.create(profile);
            session.setAttribute("profileId", created.getProfileId());
            return "redirect:/profile/my-profile";
        } catch (InvalidProfileException | DuplicateProfileException ex) {
            model.addAttribute("profile", profile);
            model.addAttribute("formTitle", "Opret profil");
            model.addAttribute("formAction", "/profile");
            model.addAttribute("submitLabel", "Opret");
            model.addAttribute("errorMessage", ex.getMessage());
            return "profiles/profile-form";
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
            return "redirect:/profile/my-profile";
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

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        try {
            Profile profile = profileService.login(email, password);
            session.setAttribute("profileId", profile.getProfileId());
            return "redirect:/profile/my-profile";
        } catch (InvalidProfileException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "login";
        }
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable int id) {
        profileService.deleteById(id);
        return "redirect:/profile";
    }
}
