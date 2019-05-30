package com.example.iseeproject;

import java. util. Date;

public class Expenses {
    private String username, category;
    private double price;
    private Date time ;
    private String PaymentMethod;

    public Expenses (String Username, double Price,  Date Time, String Category, String PaymentMethods) {
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

    public Date getTime() {
            return time;
    }
    public void setTime(Date Time) {
        this.time = Time;
    }

    public String getCategory() {
        return category;
    }
    public void setCategory(String Category) {
        this.category = Category;
    }


    public void setPaymentMethod(String Paymentmethod)
    {
        this.PaymentMethod  = Paymentmethod;
    }

    public  String  getPaymentMethod(){
        return PaymentMethod;
    }


}
