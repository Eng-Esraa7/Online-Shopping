package com.example.onlineshopping;

import android.graphics.Bitmap;

public class product {
    private int id;
    private String Name;
    private String Price;
    private String Quantity;
    private Bitmap Image;
    private int catId;
    private String description;

    public product(int id, String name, String price, String quantity, Bitmap image, int catId,String description) {
        this.id = id;
        Name = name;
        Price = price;
        Quantity = quantity;
        Image = image;
        this.catId = catId;
        this.description=description;
    }

    public product(String name, String price, String quantity, Bitmap image, int catId,String description) {
        Name = name;
        Price = price;
        Quantity = quantity;
        Image = image;
        this.catId = catId;
        this.description=description;
    }
    public product() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public Bitmap getImage() {
        return Image;
    }

    public void setImage(Bitmap image) {
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }
}
