package com.johanoliverlarsen.wishlist.service;

import com.johanoliverlarsen.wishlist.exception.DatabaseOperationException;
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

    // 1. arrange our objects and test fixtures
    // 2. act on an object to perform a test
    // 3. assert the result of the action is as expected

    @Test
    void findAll() {
        // Arrange - Vi klargør dummy-data
        Profile p1 = new Profile(1, "Anna", "anna@mail.dk", "kode123");
        Profile p2 = new Profile(2, "Mikkel", "mikkel@mail.dk", "kode456");
        List<Profile> mockProfiles = Arrays.asList(p1, p2);

        // Vi fortæller mock-objektet, hvad det skal returnere, når findAll kaldes
        when(profileRepository.findAll()).thenReturn(mockProfiles);

        // Act - Vi udfører handlingen
        List<Profile> result = profileService.findAll();

        // Assert - Vi tjekker om resultatet er korrekt
        assertEquals(2, result.size(), "Listen bør indeholde 2 profiler");
        assertEquals("Anna", result.get(0).getName());
        assertEquals("Mikkel", result.get(1).getName());

        // Verificer at repository-metoden faktisk blev kaldt én gang
        verify(profileRepository, times(1)).findAll();
    }

    @Test
    void findAll_DatabaseError_ThrowsDatabaseOperationException() {
        // Arrange - Vi simulerer en databasefejl (DataAccessException)
        when(profileRepository.findAll()).thenThrow(new QueryTimeoutException("Timeout"));

        // Act & Assert - Vi tjekker at servicen fanger fejlen og kaster vores egen exception
        assertThrows(DatabaseOperationException.class, () -> {
            profileService.findAll();
        });
    }

    @Test
    void findById() {
        //Bekræfter vi, at servicen sender data korrekt videre fra repository til controller uden at ændre på det.
        // Arrange
        int profileId = 1;
        Profile mockProfile = new Profile(profileId, "Anna", "anna@mail.dk", "kode123");

        // Vi fortæller mock-repository'et, at det skal returnere vores dummy-profil
        when(profileRepository.findById(profileId)).thenReturn(mockProfile);

        // Act
        Profile result = profileService.findById(profileId);

        // Assert
        assertNotNull(result);
        assertEquals(profileId, result.getProfileId());
        assertEquals("Anna", result.getName());

        // Verificer at repository blev spurgt præcis én gang med det rigtige ID
        verify(profileRepository, times(1)).findById(profileId);
    }

    @Test
    void findById_NotFound_ThrowExcption() {
        int nonExistentId = 999;
        when(profileRepository.findById(nonExistentId)).thenReturn(null);
        assertThrows(ProfileNotFoundException.class, () -> {
            profileService.findById(nonExistentId);
        });

        verify(profileRepository, times(1)).findById(nonExistentId);
    }



    @Test
    void login() {
        Profile mockProfile = new Profile(1, "Anna", "anna@mail.dk", "kode123");
        when(profileRepository.findByEmail("anna@mail.dk")).thenReturn(mockProfile);
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void deleteById() {
    }
}