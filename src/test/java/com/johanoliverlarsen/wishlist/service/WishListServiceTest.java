package com.johanoliverlarsen.wishlist.service;

import com.johanoliverlarsen.wishlist.exception.InvalidWishException;
import com.johanoliverlarsen.wishlist.exception.WishListNotFoundException;
import com.johanoliverlarsen.wishlist.model.WishList;
import com.johanoliverlarsen.wishlist.repository.WishListRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WishListServiceTest {

    @Mock
    private WishListRepository wishListRepository;

    @InjectMocks
    private WishListService wishListService;

    @Test
    void findById_ThrowsWishListNotFoundException_WhenNotFound() {
        when(wishListRepository.findById(99)).thenReturn(null);
        assertThrows(WishListNotFoundException.class, () -> wishListService.findById(99));
    }

    @Test
    void create_ThrowsIllegalArgumentException_WhenTitleIsEmpty() {
        WishList wl = new WishList();
        wl.setTitle("");
        wl.setDescription("Beskrivelse");
        assertThrows(IllegalArgumentException.class, () -> wishListService.create(wl, 1));
    }

    @Test
    void create_ThrowsIllegalArgumentException_WhenDescriptionIsNull() {
        WishList wl = new WishList();
        wl.setTitle("Titel");
        wl.setDescription(null);
        assertThrows(IllegalArgumentException.class, () -> wishListService.create(wl, 1));
    }

    @Test
    void deleteById_ThrowsInvalidWishException_WhenIdIsNegative() {
        assertThrows(InvalidWishException.class, () -> wishListService.deleteById(-1));
    }
}