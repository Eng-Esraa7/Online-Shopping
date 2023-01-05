package com.example.onlineshopping;

import android.graphics.Bitmap;

public class Orders {
    private int id;
    private String orderDate;
    private String name;
    private String phone;
    private String address;
    private String Feedback;
    private String Rating;
    private int ordDetailsId;

    public Orders() {
    }

//    public Orders(String orderDate, String name, String phone, String address, int ordDetailsId) {
//        this.orderDate = orderDate;
//        this.name = name;
//        this.phone = phone;
//        this.address = address;
//        this.ordDetailsId = ordDetailsId;
//    }

    public Orders(String orderDate, String name, String phone, String address,int ordDetailsId, String feedback, String rating) {
        this.orderDate = orderDate;
        this.name = name;
        this.phone = phone;
        this.address = address;
        Feedback = feedback;
        Rating = rating;
        this.ordDetailsId = ordDetailsId;
    }

    public String getFeedback() {
        return Feedback;
    }

    public void setFeedback(String feedback) {
        Feedback = feedback;
    }

    public String getRating() {
        return Rating;
    }

    public void setRating(String rating) {
        Rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getOrdDetailsId() {
        return ordDetailsId;
    }

    public void setOrdDetailsId(int ordDetailsId) {
        this.ordDetailsId = ordDetailsId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getordDetailsId() {
        return ordDetailsId;
    }

    public void setordDetailsId(int ordDetailsId) {
        this.ordDetailsId = ordDetailsId;
    }
}