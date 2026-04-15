package com.johanoliverlarsen.wishlist.model;

import java.util.Objects;

public class Profile {
    private Integer profileId;
    private String name;
    private String email;
    private String password;

    public Profile(Integer profileId, String name, String email, String password) {
        this.profileId = profileId;
        this.name = name;
        this.email = email;
        this.password = password;
    }


    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(Integer profileId) {
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


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Profile profile = (Profile) o;
        return Objects.equals(profileId, profile.profileId) && Objects.equals(name, profile.name) && Objects.equals(email, profile.email) && Objects.equals(password, profile.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(profileId, name, email, password);
    }
}

