package com.johanoliverlarsen.wishlist.repository;


import com.johanoliverlarsen.wishlist.exception.WishListNotFoundException;
import com.johanoliverlarsen.wishlist.model.Wish;
import com.johanoliverlarsen.wishlist.model.WishList;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class WishListRepository {
    /*
    private final JdbcTemplate jdbcTemplate;
    private RowMapper<WishList> wishListRowMapper = (rs, rowNum) ->
            new WishList(
                    rs.getInt("wishlist_id"),
                    rs.getString("title"),
                    rs.getString("description")
            );

    public WishListRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public WishList findById(int id){
        String sql = """
                SELECT w.wishlist_id, w.title, w.description
                FROM wishlist w
                WHERE w.wishlist_id = ?
                """;
        return jdbcTemplate.queryForObject(sql, wishListRowMapper, id);
    }

    public List <WishList> findAllByProfileId(int profileId){
        String sql = """
               SELECT w.wishlist_id, w.title, w.description
               FROM wishlist w
               WHERE w.profile_id = ?
               """;

        List<WishList> wishLists = jdbcTemplate.query(connection -> {
            var ps = connection.prepareStatement(sql);
            ps.setInt(1, profileId);
            return ps;
        }, wishListRowMapper);

        if (wishLists.isEmpty()) {
            throw new WishListNotFoundException("Ingen wishlists fundet for profil med id " + profileId);
        }

        return wishLists;
    }

    public WishList insert(WishList wishlist, int profile_id){
        String sql = """
         INSERT INTO wishlist (title, description, profile_id)VALUES 
         (?, ?, ?);
        """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection-> {
            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, wishlist.getTitle());
            ps.setString(2, wishlist.getDescription());
            ps.setInt(3, profile_id);
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if (key == null) {
            throw new IllegalStateException("Failed to retrieve generated id.");
        }

        return new WishList(
                key.intValue(),
                wishlist.getTitle(),
                wishlist.getDescription());
    }


    public boolean update(WishList wishlist){
        String sql = """
        UPDATE wishlist
        SET title = ?, description = ?
        WHERE wishlist_id = ?
        """;

        int rowsUpdated = jdbcTemplate.update(connection -> {
            var ps = connection.prepareStatement(sql);
            ps.setString(1, wishlist.getTitle());
            ps.setString(2, wishlist.getDescription());
            ps.setInt(3, wishlist.getWishListId());
            return ps;
        });

        return rowsUpdated > 0;
    }


    public boolean deleteById(int wishListId){
        String sql = """
        DELETE FROM wishlist
        WHERE wishlist_id = ?
        """;

        int rowsDeleted = jdbcTemplate.update(connection -> {
         var ps = connection.prepareStatement(sql);
         ps.setInt(1, wishListId);
         return ps;
        });

        return rowsDeleted > 0;
    }

     */

}
