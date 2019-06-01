package com.example.iseeproject;

import java.util.Date;

public class Expenses {
    private String username, category;
    //timestamp of expense creation will be converted later to a real date type on the homepage
    //when we will want to make comparison of the days that the expenses were created
    private String time;
    private double price;
    private String PaymentMethod;

    public Expenses (String Username, double Price, String Category, String Time, String PaymentMethods) {
        this.username = Username;
        this.category = Category;
        this.price = Price;
        this.time= Time;
        this.PaymentMethod = PaymentMethods;
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

    public String getTime() {
            return time;
    }
    public void setTime(String Time) {
        this.time = Time;
    }

    public String getCategory() {
        return category;
    }
    public void setCategory(String Category) {
        this.category = Category;
    }

    public  String  getPaymentMethod(){
        return PaymentMethod;
    }
    public void setPaymentMethod(String Paymentmethod)
    {
        this.PaymentMethod  = Paymentmethod;
    }



}
