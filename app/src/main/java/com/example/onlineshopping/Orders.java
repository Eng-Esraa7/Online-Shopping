package com.example.onlineshopping;

import android.graphics.Bitmap;

public class Orders {
    private int id;
    private String orderDate;
    private String address;
    private int userid;

    public Orders() {
    }

    public Orders(int id, String orderDate, String address, int userid) {
        this.id = id;
        this.orderDate = orderDate;
        this.address = address;
        this.userid = userid;
    }

    public Orders(String orderDate, String address) {
        this.orderDate = orderDate;
        this.address = address;
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

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
}