package com.johanoliverlarsen.wishlist.service;

import com.johanoliverlarsen.wishlist.exception.InvalidWishException;
import com.johanoliverlarsen.wishlist.exception.WishNotFoundException;
import com.johanoliverlarsen.wishlist.model.Wish;
import com.johanoliverlarsen.wishlist.repository.WishRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WishServiceTest {

    @Mock
    private WishRepository wishRepository;

    @InjectMocks
    private WishService wishService;

    @Test
    void create_ThrowsInvalidWishException_WhenTitleIsTooLong() {
        Wish w = new Wish();
        w.setTitle("a".repeat(51));
        assertThrows(InvalidWishException.class, () -> wishService.create(w, 1));
    }

    @Test
    void findById_ThrowsWishNotFoundException_WhenIdDoesNotExist() {
        when(wishRepository.findById(500)).thenReturn(null);
        assertThrows(WishNotFoundException.class, () -> wishService.findById(500));
    }

    @Test
    void update_ThrowsWishNotFoundException_WhenDatabaseUpdateReturnsFalse() {
        Wish w = new Wish();
        w.setTitle("Gyldigt Navn");
        // Her returnerer repository false, hvilket betyder rækken ikke findes
        when(wishRepository.update(any())).thenReturn(false);

        assertThrows(WishNotFoundException.class, () -> wishService.update(1, w));
    }

    @Test
    void findAllByWishListId_ThrowsInvalidWishException_WhenIdIsZero() {
        assertThrows(InvalidWishException.class, () -> wishService.findAllByWishListId(0));
    }
}