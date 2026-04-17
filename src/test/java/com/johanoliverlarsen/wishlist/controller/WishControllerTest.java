package com.johanoliverlarsen.wishlist.controller;

import com.johanoliverlarsen.wishlist.model.Wish;
import com.johanoliverlarsen.wishlist.repository.WishRepository;
import com.johanoliverlarsen.wishlist.service.WishService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import com.johanoliverlarsen.wishlist.exception.InvalidWishException;
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

@WebMvcTest(WishController.class)
public class WishControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private WishRepository wishRepository;

    @MockitoBean
    private WishService wishService;

    // GET /profile/list/{wishListId}
    @Test
    void getList_shouldReturnWishListView() throws Exception {
        when(wishService.findAllByWishListId(1))
                .thenReturn(List.of(new Wish(1, "Bakken", null, null, null, null, null, null)));

        mockMvc.perform(get("/profile/list/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("wishes/wish-list"))
                .andExpect(model().attributeExists("wishes"));
    }

    // GET /profile/list/{wishListId}/create-wish
    @Test
    void getCreateForm_shouldReturnFormView() throws Exception {
        mockMvc.perform(get("/profile/list/1/create-wish"))
                .andExpect(status().isOk())
                .andExpect(view().name("wishes/wish-form"))
                .andExpect(model().attributeExists("wish"))
                .andExpect(model().attribute("formTitle", "Opret ønske"))
                .andExpect(model().attribute("submitLabel", "Opret"));
    }


    // POST /profile/list/{wishListId}
    @Test
    void postCreate_success_shouldRedirect() throws Exception {
        when(wishService.create(any(Wish.class), eq(1)))
                .thenReturn(new Wish());

        mockMvc.perform(post("/profile/list/1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("title", "Bakken")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile/list/1"));
    }

    @Test
    void postCreate_invalidWish_shouldReturnFormWithError() throws Exception {
        doThrow(new InvalidWishException("Et ønske skal have en titel."))
                .when(wishService).create(any(Wish.class), eq(1));

        mockMvc.perform(post("/profile/list/1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("title", "")
                )
                .andExpect(status().isOk())
                .andExpect(view().name("wishes/wish-form"))
                .andExpect(model().attribute("errorMessage", "Et ønske skal have en titel."))
                .andExpect(model().attribute("formTitle", "Opret ønske"))
                .andExpect(model().attribute("submitLabel", "Opret"));
    }

    // GET /profile/list/{wishListId}/wish/{id}/edit
    @Test
    void getEditForm_shouldReturnFormWithWish() throws Exception {
        Wish existing = new Wish(42, "Bakken", null, null, null, null, null, null);
        when(wishService.findById(42)).thenReturn(existing);

        mockMvc.perform(get("/profile/list/1/wish/42/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("wishes/wish-form"))
                .andExpect(model().attribute("wish", existing))
                .andExpect(model().attribute("formTitle", "Rediger ønske"))
                .andExpect(model().attribute("submitLabel", "Opdater"));
    }

    // POST /profile/list/{wishListId}/wish/{id}
    @Test
    void postUpdate_success_shouldRedirect() throws Exception {
        // update() er void, så ingen stubbing nødvendig (Mockito gør ingenting som default)

        mockMvc.perform(post("/profile/list/1/wish/42")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("title", "Opdateret titel")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile/list/1"));
    }

    @Test
    void postUpdate_invalidWish_shouldReturnFormWithError() throws Exception {
        doThrow(new InvalidWishException("Titlen på ønsket kan maksimalt være 50 tegn"))
                .when(wishService).update(eq(42), any(Wish.class));

        mockMvc.perform(post("/profile/list/1/wish/42")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("title", "X".repeat(51))
                )
                .andExpect(status().isOk())
                .andExpect(view().name("wishes/wish-form"))
                .andExpect(model().attribute("errorMessage", "Titlen på ønsket kan maksimalt være 50 tegn"))
                .andExpect(model().attribute("formTitle", "Rediger ønske"))
                .andExpect(model().attribute("submitLabel", "Opdater"));
    }

    // POST /profile/list/{wishListId}/wish/{id}/delete
    @Test
    void postDelete_success_shouldRedirect() throws Exception {
        // deleteById() er void – ingen stubbing nødvendig

        mockMvc.perform(post("/profile/list/1/wish/42/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile/list/1"));
    }

}



