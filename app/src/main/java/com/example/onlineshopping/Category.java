package com.example.onlineshopping;

import android.graphics.Bitmap;

public class Category {
    private int id;
    private String name;
    private Bitmap img;

    public Category() {
    }

    public Category(int id, String name, Bitmap img) {
        this.id = id;
        this.name = name;
        this.img = img;
    }

    public Category(String name, Bitmap img) {
        this.name = name;
        this.img = img;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
