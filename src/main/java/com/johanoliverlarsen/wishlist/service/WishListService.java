package com.johanoliverlarsen.wishlist.service;

import com.johanoliverlarsen.wishlist.exception.DatabaseOperationException;
import com.johanoliverlarsen.wishlist.exception.InvalidWishException;
import com.johanoliverlarsen.wishlist.exception.WishListNotFoundException;
import com.johanoliverlarsen.wishlist.exception.WishNotFoundException;
import com.johanoliverlarsen.wishlist.model.Wish;
import com.johanoliverlarsen.wishlist.model.WishList;
import com.johanoliverlarsen.wishlist.repository.WishListRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class WishListService {

    private final WishListRepository wishListRepository;

    public WishListService(WishListRepository wishListRepository) {
        this.wishListRepository = wishListRepository;
    }

    public WishList findById(int id) {
        validateId(id);

        WishList wishlist = wishListRepository.findById(id);

        if (wishlist == null) {
            throw new WishListNotFoundException(id);
        }
        return wishlist;
    }

    public List<WishList> findAllByProfileId(int profileId) {
        validateId(profileId);

        List<WishList> wishlistlist;
        try {
            wishlistlist = wishListRepository.findAllByProfileId(profileId);
        } catch (DataAccessException ex) {
            throw new DatabaseOperationException("Listen kunne ikke hentes.", ex);
        }

        return wishlistlist;

    }



    public WishList create(WishList wishList, int profileId) {
      validateWishList(wishList);
      validateId(profileId);

      try {
          return wishListRepository.insert(wishList, profileId);
      } catch (DataAccessException ex) {
          throw new DatabaseOperationException("Fejl: Ønsket blev ikke oprettet.", ex);
      }

    }


    public void update(int id, WishList wishList) {
        validateId(id);
        validateWishList(wishList);


        try {
            boolean updated = wishListRepository.update(wishList);
            if (!updated) {
                throw new WishListNotFoundException(id);
            }
        } catch (DatabaseOperationException ex) {
            throw new DatabaseOperationException("Fejl: Listen blev ikke opdateret.", ex);
        }

    }

    public void deleteById(int id) {
        validateId(id);

        try {
            boolean deleted = wishListRepository.deleteById(id);
            if (!deleted) {
                throw new WishListNotFoundException(id);
            }
        } catch (DataAccessException ex) {
            throw new DatabaseOperationException("Fejl: Listen blev ikke slettet.", ex);
        }
    }


    //private metoder til datavalidering
    private void validateId(int id) {
        if (id <= 0) {
            throw new InvalidWishException("Id skal være et positivt tal.");
        }
    }

    private void validateWishList(WishList wishList) {
        if (wishList.getTitle() == null || wishList.getTitle().isBlank()) {
            throw new IllegalArgumentException("Titel må ikke være tom");
        }
        if (wishList.getDescription() == null || wishList.getDescription().isBlank()) {
            throw new IllegalArgumentException("Beskrivelse må ikke være tom");
        }

    }

}
