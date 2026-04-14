package com.johanoliverlarsen.wishlist.repository;


import com.johanoliverlarsen.wishlist.model.Wish;
import com.johanoliverlarsen.wishlist.model.WishList;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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
//    public WishList insert(WishList wishlist){
//    }
//
//    public boolean update(WishList wishlist){
//    }
//
//    public boolean deleteById(int wishListId){
//    }
//




}
