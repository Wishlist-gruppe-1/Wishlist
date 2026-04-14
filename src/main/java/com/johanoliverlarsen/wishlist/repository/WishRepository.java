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
                    rs.getInt("wish_id"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getString("location"),
                    rs.getDate("date").toLocalDate(),
                    rs.getDouble("price"),
                    rs.getString("url"),
                    null // tags sættes bagefter
            );

    public WishRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private List<String> findTagsById(int id) {
        String sql = """
                SELECT t.title
                	FROM wish_tag wt
                    JOIN tag t
                    ON wt.tag_id = t.tag_id
                    WHERE wish_id = ?
                    ORDER BY wt.tag_id
                """;
        return jdbcTemplate.queryForList(sql, String.class, id);
    }

    private void attachTags(List<Wish> wishes) {
        for (Wish w : wishes) {
            List<String> tag = findTagsById(w.getWishId());
            w.setTag(tag);
        }
    }

    //Er i tvivl om det overhovedet giver mening at have nedenstående - vi kommer ikke til at skulle liste ønsker
    // på tværs af brugere
    public List<Wish> findAll() {
        String sql = """
                SELECT * FROM wish
                """;
        return jdbcTemplate.query(sql, wishRowMapper);
    }

    public Wish findById(int id) {
        Wish w = new Wish(1, "Hej Hej", null, null, null, 0.0, null, null);
        return w;
    }

    public String findAllTags() {
        return null;
    }

    public String insert(Wish wish) {
        return null;
    }

    public boolean update(Wish wish) {
        return false;
    }

    public boolean deleteById(int id) {
        return false;
    }

}
