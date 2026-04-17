package com.johanoliverlarsen.wishlist.repository;
import com.johanoliverlarsen.wishlist.model.Wish;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;


@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = "classpath:h2init.sql", executionPhase = BEFORE_TEST_METHOD)

public class WishRepositoryTest {


    @Autowired
    private WishRepository repo;

    @Test
    void readAllByWishListId() {
        List<Wish> all = repo.findAllByWishListId(1);

        assertThat(all).isNotNull();
        assertThat(all.size()).isEqualTo(4);
        assertThat(all.get(0).getTitle()).isEqualTo("Keramik-workshop");
        assertThat(all.get(1).getTitle()).isEqualTo("Trædrejning for begyndere");

        all = repo.findAllByWishListId(2);

        assertThat(all).isNotNull();
        assertThat(all.size()).isEqualTo(3);
        assertThat(all.get(0).getTitle()).isEqualTo("Ramen fra bunden");
        assertThat(all.get(1).getTitle()).isEqualTo("Fermentering for nybegyndere");

        all = repo.findAllByWishListId(3);

        assertThat(all).isNotNull();
        assertThat(all.size()).isEqualTo(3);
        assertThat(all.get(0).getTitle()).isEqualTo("Svøm i Kastrup Søbad");
        assertThat(all.get(1).getTitle()).isEqualTo("Cykeltur langs Amager Strandpark");
    }



    @Test
    void insertAndReadBack() {
        Wish insertWish = new Wish(
                null,
                "Chokoladetempering-workshop",
                "Lav praliner og trøfler med en chocolatier",
                "Ro Chokolade, Frederiksberg",
                LocalDate.of(2026, 10, 4),
                new BigDecimal("475.00"),
                "test.dk",
                List.of( "Indendørs", "Keramik"));
        Wish savedWish = repo.insert(insertWish, 2);

        Wish lookupWish = repo.findById(savedWish.getWishId());
        assertThat(lookupWish).isNotNull();
        assertThat(lookupWish.getWishId()).isEqualTo(savedWish.getWishId());
        assertThat(lookupWish.getTitle()).isEqualTo(savedWish.getTitle());
        assertThat(lookupWish.getDescription()).isEqualTo(savedWish.getDescription());
        assertThat(lookupWish.getLocation()).isEqualTo(savedWish.getLocation());
        assertThat(lookupWish.getDate()).isEqualTo(savedWish.getDate());
        assertThat(lookupWish.getPrice()).isEqualTo(savedWish.getPrice());
        assertThat(lookupWish.getUrl()).isEqualTo(savedWish.getUrl());
        assertThat(lookupWish.getTag()).isEqualTo(savedWish.getTag());
    }

//
//    void updateAndReadBack() {
//        Wish updateWish = new Wish(
//                null,
//                "Chokoladetempering-workshop",
//                "Lav praliner og trøfler med en chocolatier",
//                "Ro Chokolade, Frederiksberg",
//                LocalDate.of(2026, 10, 4),
//                new BigDecimal("475.00"),
//                "test.dk",
//                List.of( "Indendørs", "Keramik"));
//        boolean updated = repo.update(updateWish);
//
//        if (updated) {
//
//        }
//
//        Wish lookupWish = repo.findById(savedWish.getWishId());
//        assertThat(lookupWish).isNotNull();
//        assertThat(lookupWish.getWishId()).isEqualTo(savedWish.getWishId());
//        assertThat(lookupWish.getTitle()).isEqualTo(savedWish.getTitle());
//        assertThat(lookupWish.getDescription()).isEqualTo(savedWish.getDescription());
//        assertThat(lookupWish.getLocation()).isEqualTo(savedWish.getLocation());
//        assertThat(lookupWish.getDate()).isEqualTo(savedWish.getDate());
//        assertThat(lookupWish.getPrice()).isEqualTo(savedWish.getPrice());
//        assertThat(lookupWish.getUrl()).isEqualTo(savedWish.getUrl());
//        assertThat(lookupWish.getTag()).isEqualTo(savedWish.getTag());
//    }

    //test

}


