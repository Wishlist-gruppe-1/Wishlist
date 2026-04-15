package com.johanoliverlarsen.wishlist.service;

import com.johanoliverlarsen.wishlist.model.Wish;
import com.johanoliverlarsen.wishlist.repository.WishRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/*
- wishRepository: WishRepository

+ findAll(): List<Wish>
+ findById(id: int): Wish
+ findAllByUserId(userId: int): List<Wish>
+ findAllTags(): List<String>
+ create(wish: Wish): Wish
+ update(id: int, wish: Wish)
+ deleteById(id: int)

//private metoder til data validering
- validateId(id: int)
- valdateWish(wish: Wish)
 */

@Service

public class WishService {
    private WishRepository wishRepository;

    private final WishRepository wishRepository;

    public WishService(WishRepository wishRepository) {
        this.wishRepository = wishRepository;
    }

    public List<Wish> findAll() {

    }

    public Wish findById(int id) {

    }

    public List<Wish> findAllByProfileId(int profileId) {

    }

    public List<String> findAllTags() {

    }


    public Wish create(Wish wish) {

    }

    public Wish update(int id, Wish wish) {

    }

    public void deleteById(int id) {

    }

    //private metoder til datavalidering
    private void validateId(int id) {

    }

    private void validateWish(Wish Wish) {

    }


}
