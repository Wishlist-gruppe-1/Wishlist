package com.johanoliverlarsen.wishlist.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Wish {
    private int wishId;
    private String title;
    private String description;
    private String location;
    private LocalDate date;
    private BigDecimal price;
    private String url;
    private List<String> tag;

    public Wish() {
        //tom konstruktør
    }

    public Wish(int wishId, String title, String description, String location,
                LocalDate date, BigDecimal price, String url, List<String> tag) {
        this.wishId = wishId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.date = date;
        this.price = price;
        this.url = url;
        this.tag = tag;
    }

    public int getWishId() { return wishId; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getLocation() { return location; }
    public LocalDate getDate() { return date; }
    public BigDecimal getPrice() { return price; }
    public String getUrl() { return url; }
    public List<String> getTag() { return tag; }

    public void setWishId(int wishId) { this.wishId = wishId; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setLocation(String location) { this.location = location; }
    public void setDate(LocalDate date) { this.date = date; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public void setUrl(String url) { this.url = url; }
    public void setTag(List<String> tag) { this.tag = tag; }

    @Override
    public String toString() {
        return "WishId: " + wishId + ", Title: " + title + ", Description: " + description + ", Location: " + location +
                ", Date: " + date + ", Price: " + price + ", URL: " + url + ", Tag: " + tag;
    }
}
