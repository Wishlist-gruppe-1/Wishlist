package com.johanoliverlarsen.wishlist.model;

public class Profile {
    private int profileId;
    private String name;
    private String email;
    private String password;

    public Profile(int profileId, String name, String email, String password) {
        this.profileId = profileId;
        this.name = name;
        this.email = email;
        this.password = password;
    }


    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "profileId=" + profileId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

