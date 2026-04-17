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
import java.util.List;
import java.util.regex.Pattern;

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

    @Bean
    CommandLineRunner testEverything(ProfileRepository repo) {
        return args -> {
            System.out.println("\n===========================================");
            System.out.println("   STARTER TOTAL-TEST AF REPOSITORY");
            System.out.println("===========================================");

            // 1. LÆS EKSISTERENDE DATA (Fra din data.sql)
            System.out.println("\n[1] Tester findAll()...");
            List<Profile> alle = repo.findAll();
            System.out.println("Antal profiler fundet: " + alle.size());
            alle.forEach(p -> System.out.println(" -> " + p.getName() + " (" + p.getEmail() + ")"));

            // 2. FIND SPECIFIK PROFIL (Mikkel fra data.sql)
            System.out.println("\n[2] Tester findById(2)...");
            Profile mikkel = repo.findById(2);
            if (mikkel != null) {
                System.out.println("Succes! Fandt: " + mikkel.getName());
            } else {
                System.out.println("FEJL: Mikkel blev ikke fundet!");
            }

            // 3. OPRET NY PROFIL (Insert)
            System.out.println("\n[3] Tester insert()...");
            Profile ny = new Profile(null, "Testmand", "test@mand.dk", "hemmelig");
            Profile gemt = repo.insert(ny);
            System.out.println("Oprettet profil med genereret ID: " + gemt.getProfileId());

            // 4. OPDATER PROFIL (Update)
            System.out.println("\n[4] Tester update()...");
            gemt.setName("Opdateret Testmand");
            boolean opdateret = repo.update(gemt);
            Profile tjekOpdatering = repo.findById(gemt.getProfileId());
            System.out.println("Opdatering status: " + (opdateret ? "OK" : "FEJLEDE"));
            System.out.println("Nyt navn i DB: " + tjekOpdatering.getName());

            // 5. SLET PROFIL (Delete)
            System.out.println("\n[5] Tester deleteById()...");
            boolean slettet = repo.deleteById(gemt.getProfileId());
            Profile tjekSletning = repo.findById(gemt.getProfileId());
            if (slettet && tjekSletning == null) {
                System.out.println("Succes! Profilen er fjernet helt fra databasen.");
            } else {
                System.out.println("FEJL: Profilen findes stadig!");
            }

            System.out.println("\n===========================================");
            System.out.println("        ALLE TESTS ER GENNEMFØRT");
            System.out.println("===========================================\n");
        };
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
            throw new InvalidProfileException("Navn må ikke overskride 100 karakter.");
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
