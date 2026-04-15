package com.johanoliverlarsen.wishlist.service;


import com.johanoliverlarsen.wishlist.model.Profile;
import com.johanoliverlarsen.wishlist.repository.ProfileRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
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
}



/*
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
*/