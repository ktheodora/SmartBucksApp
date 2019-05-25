package com.example.iseeproject;

public class Expenses {
    private String username, category;
    private double price;

    public Expenses (String Username, double Price, String Category) {
        this.username = Username;
        this.category = Category;
        this.price = Price;
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

    public String getCategory() {
        return category;
    }
    public void setCategory(String Category) {
        this.category = Category;
    }

}
