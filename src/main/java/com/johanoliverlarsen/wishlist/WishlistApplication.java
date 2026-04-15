package com.johanoliverlarsen.wishlist;

import com.johanoliverlarsen.wishlist.model.WishList;
import com.johanoliverlarsen.wishlist.repository.WishListRepository;
//import com.johanoliverlarsen.wishlist.repository.WishRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WishlistApplication {

    public static void main(String[] args) {
        SpringApplication.run(WishlistApplication.class, args);
    }

    @Bean
    CommandLineRunner testDb(WishListRepository repo) {
        return args -> {
            System.out.println("=== TEST: findTagsById() ===");
            System.out.println(repo.findById(1) + "\n");

            System.out.println("=== TEST: insert() ===");
            WishList inserted = repo.insert(new WishList(null, "Keramik", "Kursus"), 1);
            System.out.println(inserted + "\n");

            System.out.println("=== TEST: update() ===");
            WishList toUpdate = new WishList(inserted.getWishListId(), "Keramik opdateret", "Kursus opdateret");
            boolean success = repo.update(toUpdate);
            System.out.println("Opdatering lykkedes: " + success + "\n");

            System.out.println("=== TEST: deleteById() ===");
            boolean deleted = repo.deleteById(inserted.getWishListId());
            System.out.println("Sletning lykkedes: " + deleted);
        };
    }



}
