package com.johanoliverlarsen.wishlist.repository;

import com.johanoliverlarsen.wishlist.model.Wish;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WishRepository {
    private final JdbcTemplate jdbcTemplate;
    private RowMapper<Wish> wishRowMapper = (rs, rowNum) ->
            new Wish(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("password")
            );

    public WishRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Wish> findAll() {
        String sql = """
                SELECT id, name, email, password
                """
    }

    public List<Wish> findById(){
//return null
    }

    public Wish findAllByProfileId() {
        //return null
    }

    public String findAllTags(){
//return null
    }

    public String insert(Wish wish) {

    }

    public boolean update(Wish wish) {

    }

    public boolean deleteById(int id) {

    }

}
