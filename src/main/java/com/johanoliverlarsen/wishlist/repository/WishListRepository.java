package com.johanoliverlarsen.wishlist.repository;


import com.johanoliverlarsen.wishlist.model.Wish;
import com.johanoliverlarsen.wishlist.model.WishList;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WishListRepository {
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

//    public List<WishList> findAll(){
//    }

    public WishList findById(int id){
        String sql = """
                SELECT w.wishlist_id, w.title, w.description
                FROM wishlist w
                WHERE w.wishlist_id = ?
                """;
        return jdbcTemplate.queryForObject(sql, wishListRowMapper, id);
    }

//    public List <WishList> findAllByProfileId(int profileId){
//    }
//
    public WishList insert(WishList wishlist, int profile_id){
        String sql = """
         INSERT INTO wishlist (wishlist_id, title, description, profile_id)VALUES 
         (?, ?, ?, ?);
        """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection-> {
            var ps = connection.prepareStatement(sql, new String[]{"profile_id"});
            ps.setString(1, WishList.getTitle());
            ps.setString(2, WishList.getDescription());
            ps.setInt(3, WishList.getWishListId());
            return ps;
        }, keyHolder);

        })


    }
//
//    public boolean update(WishList wishlist){
//    }
//
//    public boolean deleteById(int wishListId){
//    }
//




}
