package com.johanoliverlarsen.wishlist.service;

import com.johanoliverlarsen.wishlist.model.WishList;
import com.johanoliverlarsen.wishlist.repository.WishListRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/*
- wishListRepository: WishListRepository

+ findAll(): List<WishList>
+ findById(id: int): WishList
+ findAllByProfileId(ProfileId: int): List<WishList>
+ create(wishList: WishList): WishList
+ update(id: int, wishList: WishList)
+ deleteById(id: int)

//private metoder til data validering
- validateId(id: int)
- valdateWishList(wishList: WishList)
 */



@Service

public class WishListService {

    private final WishListRepository wishListRepository;

    public WishListService(WishListRepository wishListRepository) {
        this.wishListRepository = wishListRepository;
    }

    public List<WishList> findAll() {

    }

    public WishList findById(int id) {

    }

    public List<WishList> findAllByProfileId(int profileId) {

    }


    public WishList create(WishList wishList) {

    }

    public WishList update(int id, WishList wishList) {

    }

    public void deleteById(int id) {

    }

    //private metoder til datavalidering
    private void validateId(int id) {

    }

    private void validateWishList(WishList WishList) {

    }





}
