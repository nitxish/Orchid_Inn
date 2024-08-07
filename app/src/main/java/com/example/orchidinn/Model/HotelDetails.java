package com.example.orchidinn.Model;

public class HotelDetails {
    private String name, email, address, image, description, phone, rating;

    public HotelDetails() {
    }

    public HotelDetails(String name, String email, String address, String image, String description, String phone, String rating) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.image = image;
        this.description = description;
        this.phone = phone;
        this.rating = rating;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
