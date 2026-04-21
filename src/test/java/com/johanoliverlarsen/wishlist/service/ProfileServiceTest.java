package com.johanoliverlarsen.wishlist.service;

import com.johanoliverlarsen.wishlist.exception.DatabaseOperationException;
import com.johanoliverlarsen.wishlist.exception.DuplicateProfileException;
import com.johanoliverlarsen.wishlist.exception.InvalidProfileException;
import com.johanoliverlarsen.wishlist.exception.ProfileNotFoundException;
import com.johanoliverlarsen.wishlist.model.Profile;
import com.johanoliverlarsen.wishlist.repository.ProfileRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.QueryTimeoutException;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

class ProfileServiceTest {


    @Mock
    private ProfileRepository profileRepository;

    @InjectMocks
    private ProfileService profileService;


    @Test
    void findById_ThrowsInvalidProfileException_WhenIdIsZero() {
        assertThrows(InvalidProfileException.class, () -> profileService.findById(0));
    }

    @Test
    void findById_ThrowsProfileNotFoundException_WhenProfileIsNull() {
        when(profileRepository.findById(1)).thenReturn(null);
        assertThrows(ProfileNotFoundException.class, () -> profileService.findById(1));
    }

    @Test
    void create_ThrowsInvalidProfileException_WhenEmailIsInvalid() {
        Profile p = new Profile(null, "Test", "forkert-email", "123");
        assertThrows(InvalidProfileException.class, () -> profileService.create(p));
    }

    @Test
    void create_ThrowsDuplicateProfileException_OnDatabaseConstraint() {
        Profile p = new Profile(null, "Test", "test@test.dk", "123");
        when(profileRepository.insert(any())).thenThrow(DataIntegrityViolationException.class);
        assertThrows(DuplicateProfileException.class, () -> profileService.create(p));
    }

    @Test
    void update_ThrowsInvalidProfileException_WhenNameIsTooLong() {
        String longName = "a".repeat(51);
        Profile p = new Profile(1, longName, "test@test.dk", "123");
        assertThrows(InvalidProfileException.class, () -> profileService.update(1, p));
    }
}