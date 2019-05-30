package com.example.iseeproject;

import java. util. Date;
import java. sql. Timestamp;

public class Expenses {
    private String username, category;
    private double price;
    private Timestamp time;
   // private String PaymentMethod;

    //TODO Add Payment Method
    public Expenses (String Username, double Price,  Timestamp Time, String Category) {
        this.username = Username;
        this.category = Category;
        this.price = Price;
        this.time= Time;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String Username) {
        this.username = Username;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double Price) {
        this.price = Price;
    }

    public Timestamp getTime() {
        return time;
    }
    public void setTime(Timestamp Time) {
        this.time = Time;
    }

    public String getCategory() {
        return category;
    }
    public void setCategory(String Category) {
        this.category = Category;
    }

}
