package com.johanoliverlarsen.wishlist.model;

public class WishList {
    private Integer wishListId;
    private String title;
    private String description;

    public WishList() {}

    public WishList(Integer wishListId, String title, String description) {
        this.wishListId = wishListId;
        this.title = title;
        this.description = description;
    }

    public Integer getWishListId() { return wishListId; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }

    public void setWishListId(Integer wishListId) { this.wishListId = wishListId; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }

    @Override
    public String toString(){
        return "WishListId: " + wishListId + "Title: " + title + ", Description: " + description;
    }
}
