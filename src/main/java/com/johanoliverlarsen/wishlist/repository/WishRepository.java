package com.johanoliverlarsen.wishlist.repository;

import com.johanoliverlarsen.wishlist.model.Wish;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class WishRepository {
    private final JdbcTemplate jdbcTemplate;
    private RowMapper<Wish> wishRowMapper = (rs, rowNum) -> {
        java.sql.Date sqlDate = rs.getDate("date");
        return new Wish(
                rs.getInt("wish_id"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getString("location"),
                sqlDate != null ? sqlDate.toLocalDate() : null,
                rs.getBigDecimal("price"),
                rs.getString("url"),
                null // tags sættes bagefter
        );
    };

    public WishRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }



    //Er i tvivl om det overhovedet giver mening at have nedenstående - vi kommer ikke til at skulle liste ønsker
    // på tværs af brugere

    public List<Wish> findAllByWishListId(int wishListId) {
        String sql = """
                SELECT * FROM wish w
                WHERE w.wishlist_id = ?
                ORDER BY w.wish_id
                """;
        return jdbcTemplate.query(sql, wishRowMapper, wishListId);
    }

    public Wish findById(int id) {
        String sql = """
                SELECT * FROM wish
                WHERE wish_id = ?
                """;
        Wish w = jdbcTemplate.queryForObject(sql, wishRowMapper, id);
        attachTags(List.of(w));
        return w;
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

    public List<String> findAllTags() {
        String sql = """
                SELECT t.title FROM tag t
                ORDER BY t.tag_id
                """;
        return jdbcTemplate.queryForList(sql, String.class);
    }

    public Wish insert(Wish wish, int wishListId) {
        String sql = """
                INSERT INTO wish (title, description, location, date, price, url, wishlist_id)
                VALUES(?, ?, ?, ?, ?, ?, ?)
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
           PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
           ps.setString(1, wish.getTitle());
           ps.setString(2, wish.getDescription());
           ps.setString(3, wish.getLocation());
           ps.setDate(4, wish.getDate() != null ? java.sql.Date.valueOf(wish.getDate()) : null);
           ps.setBigDecimal(5, wish.getPrice());
           ps.setString(6, wish.getUrl());
           ps.setInt(7, wishListId);
           return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if (key == null) {
            throw new IllegalStateException("Failed to retrieve generated id.");
        }

        insertTags(key.intValue(), wish.getTag());

        return new Wish(
                key.intValue(),
                wish.getTitle(),
                wish.getDescription(),
                wish.getLocation(),
                wish.getDate(),
                wish.getPrice(),
                wish.getUrl(),
                wish.getTag());
    }

    private void insertTags(int id, List<String> tags) {
        if (tags == null || tags.isEmpty()) return;
        String sql = """
                INSERT INTO wish_tag (wish_id, tag_id)
                SELECT ?, tag_id FROM tag WHERE title = ?
                """;

        for (String t : tags) {
            jdbcTemplate.update(sql, id, t);
        }
    }

    private void deleteTags(int id) {
        String sql = """
                DELETE FROM wish_tag WHERE wish_id = ?;
                """;

            jdbcTemplate.update(sql, id);
    }


    public boolean update(Wish wish) {
        String sql = """
                    UPDATE wish w
                        SET w.title = ?,
                         w.description = ?,
                         w.location = ?,
                         w.date = ?,
                         w.price = ?,
                         w.url = ?
                     WHERE w.wish_id = ?
                """;
        int rowsUpdated = jdbcTemplate.update(
                sql,
                wish.getTitle(),
                wish.getDescription(),
                wish.getLocation(),
                wish.getDate(),
                wish.getPrice(),
                wish.getUrl(),
                wish.getWishId()
        );

        deleteTags(wish.getWishId());
        insertTags(wish.getWishId(), wish.getTag());

        return rowsUpdated > 0;
    }

    public boolean deleteById(int id) {
        String sql = """
                DELETE FROM wish w
                WHERE w.wish_id = ?
                """;

        int rowsDeleted = jdbcTemplate.update(sql, id);
        return rowsDeleted > 0;
    }

    //test

}
