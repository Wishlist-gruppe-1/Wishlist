package com.johanoliverlarsen.wishlist.service;


import com.johanoliverlarsen.wishlist.exception.DatabaseOperationException;
import com.johanoliverlarsen.wishlist.exception.DuplicateProfileException;
import com.johanoliverlarsen.wishlist.exception.InvalidProfileException;
import com.johanoliverlarsen.wishlist.exception.ProfileNotFoundException;
import com.johanoliverlarsen.wishlist.model.Profile;
import com.johanoliverlarsen.wishlist.repository.ProfileRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class ProfileService {
    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public List<Profile> findAll() {
        try {
            return profileRepository.findAll();
        } catch (DataAccessException ex) {
            throw new DatabaseOperationException("Fejl, kunne ikke hente profilerne", ex);
        }

    }

    public Profile findById(int id) {
        validateId(id);

        Profile profile;
        try {
            profile = profileRepository.findById(id);
        } catch (DataAccessException ex) {
            throw  new DatabaseOperationException("Fejl, kunne ikke hente profilen" ,ex);
        }
        if(profile == null) {
            throw new ProfileNotFoundException(id);
        }
        return profile;

    }

    public Profile login(String email, String password) {
        validateEmail(email);

        Profile profile = profileRepository.findByEmail(email);
        if (profile != null && profile.getPassword().equals(password)) {
            return profile;
        }
        throw new InvalidProfileException("Forkert email eller adgangskode.");

    }

    @Transactional
    public Profile create(Profile profile) {
        validateProfil(profile);
        try {
            return profileRepository.insert(profile);
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateProfileException("Navn eller email findes allerede");
        }catch (DataAccessException ex) {
            throw new DatabaseOperationException("Fejl, kunne ikke oprette bruger", ex);
        }
    }

    @Transactional
    public Profile update(int id, Profile profile) {
        validateId(id);
        validateProfil(profile);
        profile.setProfileId(id);
        try {
            boolean updated = profileRepository.update(profile);
            if (!updated) {
                throw new ProfileNotFoundException(id);
            } return profile;
        }catch (DataIntegrityViolationException ex) {
            throw new DuplicateProfileException("Navn eller email findes allerede");
        }catch (DataAccessException ex) {
            throw new DatabaseOperationException("Fejl, kunne ikke opdatere bruger", ex);
        }
    }




    public void deleteById(int id) {
        validateId(id);
        try {
            boolean deleted = profileRepository.deleteById(id);
            if (!deleted) {
                throw new ProfileNotFoundException(id);
            }
        } catch (DataAccessException ex) {
            throw new DatabaseOperationException("Fejl, kunne ikke slette profilen.", ex);
        }

    }

    //private metoder til datavalidering
    private void validateId(int id) {
        if(id <= 0) {
            throw new InvalidProfileException("Id kan kun være positivt.");
        }

    }

    private void validateProfil(Profile profile) {
        if (profile == null) {
            throw new InvalidProfileException("Profil er nødvendigt.");
        }

        String name = profile.getName();
        if (name == null || name.isBlank()) {
            throw new InvalidProfileException("Navn erl nødvendigt.");
        }
        if (name.length()>50) {
            throw new InvalidProfileException("Navn må ikke overskride 50 karakter.");
        }

        validateEmail(profile.getEmail());

    }

    private void validateEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new InvalidProfileException("Email er nødvendigt.");
        }
        if (email.length() > 100) {
            throw new InvalidProfileException("Email må ikke overskride 100 karakter.");
        }
        final Pattern EMAIL_PATTERN =
                Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new InvalidProfileException("Email format er forkert.");
        }

    }

}
