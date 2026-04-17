package com.johanoliverlarsen.wishlist;

import com.johanoliverlarsen.wishlist.repository.WishRepository;
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
    CommandLineRunner testDb(WishRepository repo) {
        return args -> {
            System.out.println("=== TEST: findTagsById() ===");
            System.out.println(repo.findById(2));
        };
    }

}
