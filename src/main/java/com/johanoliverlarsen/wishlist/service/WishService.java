package com.johanoliverlarsen.wishlist.service;

import com.johanoliverlarsen.wishlist.exception.DatabaseOperationException;
import com.johanoliverlarsen.wishlist.exception.InvalidWishException;
import com.johanoliverlarsen.wishlist.exception.WishListNotFoundException;
import com.johanoliverlarsen.wishlist.exception.WishNotFoundException;
import com.johanoliverlarsen.wishlist.model.Wish;
import com.johanoliverlarsen.wishlist.model.WishList;
import com.johanoliverlarsen.wishlist.repository.WishListRepository;
import com.johanoliverlarsen.wishlist.repository.WishRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class WishService {
    private final WishRepository wishRepository;
    private final WishListRepository wishListRepository;

    public WishService(WishRepository wishRepository, WishListRepository wishListRepository) {
        this.wishRepository = wishRepository;
        this.wishListRepository = wishListRepository;
    }

    public Wish findById(int id) {
        validateId(id);

        Wish wish = wishRepository.findById(id);

        if (wish == null) {
            throw new WishNotFoundException(id);
        }

        return wish;
    }

    public List<Wish> findAllByWishListId(int wishListId) {
        validateId(wishListId);

        WishList wishList = wishListRepository.findById(wishListId);

        if (wishList == null) {
            throw new WishListNotFoundException(wishListId);
        }

        try {
            return wishRepository.findAllByWishListId(wishListId);
        } catch (DataAccessException ex) {
            throw new DatabaseOperationException("Ønskerne kunne ikke hentes.", ex);
        }
    }

    public Wish create(Wish wish, int wishListId) {
        validateWish(wish);
        validateId(wishListId);

        try {
            return wishRepository.insert(wish, wishListId);
        } catch (DataAccessException ex) {
            throw new DatabaseOperationException("Fejl: Ønsket blev ikke oprettet.", ex);
        }
    }

    public List<String> findAllTags() {
        return wishRepository.findAllTags();
    }

    public void update(int id, Wish wish) {
        validateId(id);
        validateWish(wish);
        wish.setWishId(id);

        try {
            boolean updated = wishRepository.update(wish);
            if (!updated) {
                throw new WishNotFoundException(id);
            }
        } catch (DatabaseOperationException ex) {
            throw new DatabaseOperationException("Fejl: Ønsket blev ikke oprettet.", ex);
        }
    }

    public void deleteById(int id) {
        validateId(id);
        try {
            boolean deleted = wishRepository.deleteById(id);
            if (!deleted) {
                throw new WishNotFoundException(id);
            }
        } catch (DataAccessException ex) {
            throw new DatabaseOperationException("Fejl: Ønsket blev ikke slettet.", ex);
        }
    }

    //private metoder til datavalidering
    private void validateId(int id) {
        if (id <= 0) {
            throw new InvalidWishException("Id skal være et positivt tal.");
        }
    }

    private void validateWish(Wish wish) {
        if (wish == null) {
            throw new InvalidWishException("Ønske er obligatorisk.");
        }

        String title = wish.getTitle();
        if (title == null || title.isBlank()) {
            throw new InvalidWishException("Et ønske skal have en titel.");
        }
        if (title.length() > 50) {
            throw new InvalidWishException("Titlen på ønsket kan maksimalt være 50 tegn");
        }
    }

}
