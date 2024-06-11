package com.example.appartmentrentingapp.model;

import java.io.Serializable;

public class ApartmentModel implements Serializable {
    int id;
    String image, location, price, ownerName, ownerPhone, description, status, userId, bookingDate, years;

    public ApartmentModel() {
    }

    public ApartmentModel(String image, String location, String price, String ownerName, String ownerPhone, String description, String status, String userId, String bookingDate, String years) {
        this.image = image;
        this.location = location;
        this.price = price;
        this.ownerName = ownerName;
        this.ownerPhone = ownerPhone;
        this.description = description;
        this.status = status;
        this.userId = userId;
        this.bookingDate = bookingDate;
        this.years = years;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerPhone() {
        return ownerPhone;
    }

    public void setOwnerPhone(String ownerPhone) {
        this.ownerPhone = ownerPhone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getYears() {
        return years;
    }

    public void setYears(String years) {
        this.years = years;
    }
}
