package com.johanoliverlarsen.wishlist.controller;

import com.johanoliverlarsen.wishlist.exception.DuplicateProfileException;
import com.johanoliverlarsen.wishlist.exception.InvalidProfileException;
import com.johanoliverlarsen.wishlist.model.Profile;
import com.johanoliverlarsen.wishlist.service.ProfileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProfileController.class)
public class ProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProfileService profileService;

    // GET /profile
    @Test
    void getList_shouldReturnProfileListView() throws Exception {
        when(profileService.findAll())
                .thenReturn(List.of(new Profile(1, "Johan", "johan@mail.dk", "1234")));

        mockMvc.perform(get("/profile"))
                .andExpect(status().isOk())
                .andExpect(view().name("profiles/profile-list"))
                .andExpect(model().attributeExists("profiles"));
    }

    // GET /profile/create
    @Test
    void getCreateForm_shouldReturnFormView() throws Exception {
        mockMvc.perform(get("/profile/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("profiles/profile-form"))
                .andExpect(model().attributeExists("profile"))
                .andExpect(model().attribute("formTitle", "Opret profil"))
                .andExpect(model().attribute("submitLabel", "Opret"))
                .andExpect(model().attribute("formAction", "/profile"));
    }

    // POST /profile
    @Test
    void postCreate_success_shouldRedirect() throws Exception {
        when(profileService.create(any(Profile.class)))
                .thenReturn(new Profile(1, "Johan", "johan@mail.dk", "1234"));

        mockMvc.perform(post("/profile")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", "Johan")
                        .param("email", "johan@mail.dk")
                        .param("password", "1234")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profil")); // bemærk: controller redirecter til /profil
    }

    @Test
    void postCreate_invalidProfile_shouldReturnFormWithError() throws Exception {
        doThrow(new InvalidProfileException("Navn er nødvendigt."))
                .when(profileService).create(any(Profile.class));

        mockMvc.perform(post("/profile")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", "")
                        .param("email", "johan@mail.dk")
                        .param("password", "1234")
                )
                .andExpect(status().isOk())
                .andExpect(view().name("profiles/profile-form"))
                .andExpect(model().attribute("errorMessage", "Navn er nødvendigt."))
                .andExpect(model().attribute("formTitle", "Opret profil"))
                .andExpect(model().attribute("submitLabel", "Opret"));
    }

    @Test
    void postCreate_duplicateProfile_shouldReturnFormWithError() throws Exception {
        doThrow(new DuplicateProfileException("Navn eller email findes allerede"))
                .when(profileService).create(any(Profile.class));

        mockMvc.perform(post("/profile")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", "Johan")
                        .param("email", "johan@mail.dk")
                        .param("password", "1234")
                )
                .andExpect(status().isOk())
                .andExpect(view().name("profiles/profile-form"))
                .andExpect(model().attribute("errorMessage", "Navn eller email findes allerede"));
    }


    // GET /profile/{id}/edit
    @Test
    void getEditForm_shouldReturnFormWithProfile() throws Exception {
        Profile existing = new Profile(1, "Johan", "johan@mail.dk", "1234");
        when(profileService.findById(1)).thenReturn(existing);

        mockMvc.perform(get("/profile/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("profiles/profile-form"))
                .andExpect(model().attribute("profile", existing))
                .andExpect(model().attribute("formTitle", "Rediger profil"))
                .andExpect(model().attribute("submitLabel", "Opdater"))
                .andExpect(model().attribute("formAction", "/profile/1"));
    }

    // POST /profile/{id}
    @Test
    void postUpdate_success_shouldRedirect() throws Exception {
        when(profileService.update(eq(1), any(Profile.class)))
                .thenReturn(new Profile(1, "Johan", "johan@mail.dk", "1234"));

        mockMvc.perform(post("/profile/1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", "Johan")
                        .param("email", "johan@mail.dk")
                        .param("password", "1234")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profil"));
    }

    @Test
    void postUpdate_invalidProfile_shouldReturnFormWithError() throws Exception {
        doThrow(new InvalidProfileException("Email format er forkert."))
                .when(profileService).update(eq(1), any(Profile.class));

        mockMvc.perform(post("/profile/1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", "Johan")
                        .param("email", "ikke-en-email")
                        .param("password", "1234")
                )
                .andExpect(status().isOk())
                .andExpect(view().name("profiles/profile-form"))
                .andExpect(model().attribute("errorMessage", "Email format er forkert."))
                .andExpect(model().attribute("formTitle", "Rediger profil"))
                .andExpect(model().attribute("submitLabel", "Opdater"))
                .andExpect(model().attribute("formAction", "/profile/1"));
    }

    @Test
    void postUpdate_duplicateProfile_shouldReturnFormWithError() throws Exception {
        doThrow(new DuplicateProfileException("Navn eller email findes allerede"))
                .when(profileService).update(eq(1), any(Profile.class));

        mockMvc.perform(post("/profile/1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", "Johan")
                        .param("email", "johan@mail.dk")
                        .param("password", "1234")
                )
                .andExpect(status().isOk())
                .andExpect(view().name("profiles/profile-form"))
                .andExpect(model().attribute("errorMessage", "Navn eller email findes allerede"));
    }

    // POST /profile/{id}/delete
    @Test
    void postDelete_success_shouldRedirect() throws Exception {
        // deleteById() er void – ingen stubbing nødvendig

        mockMvc.perform(post("/profile/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profil"));
    }
}