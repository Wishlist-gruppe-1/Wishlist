package com.johanoliverlarsen.wishlist.controller;

import com.johanoliverlarsen.wishlist.exception.InvalidProfileException;
import com.johanoliverlarsen.wishlist.model.Profile;
import com.johanoliverlarsen.wishlist.service.ProfileService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final ProfileService profileService;

    public LoginController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping()
    public String showLoginForm() {
        return "auth/login";
    }

    @PostMapping()
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        try {
            Profile profile = profileService.login(email, password);
            session.setAttribute("profileId", profile.getProfileId());
            return "redirect:/profile/list";
        } catch (InvalidProfileException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "auth/login";
        }
    }



}
