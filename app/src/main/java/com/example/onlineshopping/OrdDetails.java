package com.example.onlineshopping;

public class OrdDetails {
    private int ordid;
    private int proid;
    private  int quantity;
    private String UserId;
    private int finish;


    public OrdDetails() {
    }


    public OrdDetails(int proid,int quantity,String UserId) {
        this.proid = proid;
        this.quantity = quantity;
        this.UserId=UserId;
    }

    public int getFinish() {
        return finish;
    }

    public void setFinish(int finish) {
        this.finish = finish;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public int getOrdid() {
        return ordid;
    }

    public void setOrdid(int ordid) {
        this.ordid = ordid;
    }

    public int getProid() {
        return proid;
    }

    public void setProid(int proid) {
        this.proid = proid;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}