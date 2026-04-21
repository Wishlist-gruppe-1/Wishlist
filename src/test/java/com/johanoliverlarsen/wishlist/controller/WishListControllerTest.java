package com.johanoliverlarsen.wishlist.controller;

import com.johanoliverlarsen.wishlist.exception.InvalidWishListException;
import com.johanoliverlarsen.wishlist.model.WishList;
import com.johanoliverlarsen.wishlist.service.ProfileService;
import com.johanoliverlarsen.wishlist.service.WishListService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WishListController.class)
public class WishListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private WishListService wishListService;

    @MockitoBean
    private ProfileService profileService;

    // Opretter en session med profileId = 1, som simulerer en logget ind bruger
    private MockHttpSession sessionWithProfile() {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("profileId", 1);
        return session;
    }

    // GET /profile/list

    // Verificerer at en logget ind bruger får vist ønskeliste-siden med data
    @Test
    void getList_loggedIn_shouldReturnWishlistView() throws Exception {
        when(wishListService.findAllByProfileId(1))
                .thenReturn(List.of(new WishList()));

        mockMvc.perform(get("/profile/list")
                        .session(sessionWithProfile()))
                .andExpect(status().isOk())
                .andExpect(view().name("wishlists/wishlist-list"))
                .andExpect(model().attributeExists("wishlists"));
    }

    // Verificerer at en ikke-logget ind bruger bliver sendt videre til login
    @Test
    void getList_notLoggedIn_shouldRedirectToLogin() throws Exception {
        mockMvc.perform(get("/profile/list"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    // GET /profile/list/create

    // Verificerer at opret-formularen vises korrekt med de rigtige model-attributter
    @Test
    void getCreateForm_loggedIn_shouldReturnFormView() throws Exception {
        mockMvc.perform(get("/profile/list/create")
                        .session(sessionWithProfile()))
                .andExpect(status().isOk())
                .andExpect(view().name("wishlists/wishlist-form"))
                .andExpect(model().attributeExists("wishlist"))
                .andExpect(model().attribute("formTitle", "Opret ønskeliste"))
                .andExpect(model().attribute("submitLabel", "Opret"));
    }

    // Verificerer at en ikke-logget ind bruger bliver sendt videre til login
    @Test
    void getCreateForm_notLoggedIn_shouldRedirectToLogin() throws Exception {
        mockMvc.perform(get("/profile/list/create"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    // POST /profile/list

    // Verificerer at en gyldig ønskeliste oprettes og brugeren sendes videre til listen
    @Test
    void postCreate_success_shouldRedirect() throws Exception {
        mockMvc.perform(post("/profile/list")
                        .session(sessionWithProfile())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", "Min liste"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile/list"));    }

    // Verificerer at en ikke-logget ind bruger bliver sendt videre til login
    @Test
    void postCreate_notLoggedIn_shouldRedirectToLogin() throws Exception {
        mockMvc.perform(post("/profile/list")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", "Min liste"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    // Verificerer at formularen vises igen med fejlbesked ved ugyldigt input
    @Test
    void postCreate_invalidWishList_shouldReturnFormWithError() throws Exception {
        doThrow(new InvalidWishListException("En ønskeliste skal have et navn."))
                .when(wishListService).create(any(WishList.class), eq(1));

        mockMvc.perform(post("/profile/list")
                        .session(sessionWithProfile())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("wishlists/wishlist-form"))
                .andExpect(model().attribute("errorMessage", "En ønskeliste skal have et navn."))
                .andExpect(model().attribute("formTitle", "Opret ønskeliste"))
                .andExpect(model().attribute("submitLabel", "Opret"));
    }

    // GET /profile/list/{id}/edit

    // Verificerer at redigér-formularen vises med den eksisterende ønskelistes data
    @Test
    void getEditForm_loggedIn_shouldReturnFormWithWishList() throws Exception {
        WishList existing = new WishList();
        existing.setWishListId(5);
        when(wishListService.findById(5)).thenReturn(existing);

        mockMvc.perform(get("/profile/list/5/edit")
                        .session(sessionWithProfile()))
                .andExpect(status().isOk())
                .andExpect(view().name("wishlists/wishlist-form"))
                .andExpect(model().attribute("wishlist", existing))
                .andExpect(model().attribute("formTitle", "Rediger ønskeliste"))
                .andExpect(model().attribute("submitLabel", "Opdater"));
    }

    // Verificerer at en ikke-logget ind bruger bliver sendt videre til login
    @Test
    void getEditForm_notLoggedIn_shouldRedirectToLogin() throws Exception {
        mockMvc.perform(get("/profile/list/5/edit"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    // POST /profile/list/{id}

    // Verificerer at en gyldig opdatering gemmes og brugeren sendes videre til listen
    @Test
    void postUpdate_success_shouldRedirect() throws Exception {
        mockMvc.perform(post("/profile/list/5")
                        .session(sessionWithProfile())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", "Opdateret navn"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile/list"));    }

    // Verificerer at en ikke-logget ind bruger bliver sendt videre til login
    @Test
    void postUpdate_notLoggedIn_shouldRedirectToLogin() throws Exception {
        mockMvc.perform(post("/profile/list/5")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", "Opdateret navn"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    // Verificerer at formularen vises igen med fejlbesked ved ugyldigt input
    @Test
    void postUpdate_invalidWishList_shouldReturnFormWithError() throws Exception {
        doThrow(new InvalidWishListException("Navnet kan maksimalt være 50 tegn."))
                .when(wishListService).update(eq(5), any(WishList.class));

        mockMvc.perform(post("/profile/list/5")
                        .session(sessionWithProfile())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", "X".repeat(51)))
                .andExpect(status().isOk())
                .andExpect(view().name("wishlists/wishlist-form"))
                .andExpect(model().attribute("errorMessage", "Navnet kan maksimalt være 50 tegn."))
                .andExpect(model().attribute("formTitle", "Rediger liste"))
                .andExpect(model().attribute("submitLabel", "Opdater"));
    }

    // POST /profile/list/{id}/delete

    // Verificerer at en ønskeliste slettes og brugeren sendes videre til listen
    @Test
    void postDelete_success_shouldRedirect() throws Exception {
        mockMvc.perform(post("/profile/list/5/delete")
                        .session(sessionWithProfile()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile/list"));    }

    // Verificerer at en ikke-logget ind bruger bliver sendt videre til login
    @Test
    void postDelete_notLoggedIn_shouldRedirectToLogin() throws Exception {
        mockMvc.perform(post("/profile/list/5/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }
}