package com.johanoliverlarsen.wishlist.service;


import com.johanoliverlarsen.wishlist.model.Profile;
import com.johanoliverlarsen.wishlist.repository.ProfileRepository;
import org.springframework.stereotype.Service;
import java.util.List;

/*
- ProfileRepository: ProfileRepository

+ findAll(): List<Profile>
+ findById(id: int): Profile
+ login(email: string, password: string): Profile

//private metoder til datavalidering
- validateId(id: int)
- validateProfile(profile: Profile)
- validateEmail(email: string)

+ create(profile: Profile): Profile
+ update(id: int, profile: Profile)
+ deleteById(id: int)
*/

@Service
public class ProfileService {
    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public List<Profile> findAll() {

    }

    public Profile findById(int id) {

    }

    public Profile login(String email, String password) {

    }

    public Profile create(Profile profile) {

    }

    public Profile update(int id, Profile profile) {

    }

    public void deleteById(int id) {

    }

    //private metoder til datavalidering
    private void validateId(int id) {

    }

    private void validateProfil(Profile profile) {

    }

    private void validateEmail(String email) {

    }

}
