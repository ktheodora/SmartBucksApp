package com.example.iseeproject;

import java.util.Date;

public class Expenses {
    private String username, category;
    //timestamp of expense creation will be converted later to a real date type on the homepage
    //when we will want to make comparison of the days that the expenses were created
    private String additionTime, expenseTime;
    private double price;
    private String PaymentMethod;

    public Expenses (String AdditionTime, String ExpenseTime, String Username, double Price, String Category, String PaymentMethods) {
        this.additionTime = AdditionTime;
        this.expenseTime = ExpenseTime;
        this.username = Username;
        this.category = Category;
        this.price = Price;
        this.PaymentMethod = PaymentMethods;
    }

    public String getAdditionTime() {
        return additionTime;
    }
    public void setAdditionTime(String AdditionTime) {
        this.additionTime= AdditionTime;
    }

    public String getExpenseTime() {
        return expenseTime;
    }
    public void setExpenseTime(String ExpenseTime) {
        this.expenseTime= ExpenseTime;
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

    public  String  getPaymentMethod(){
        return PaymentMethod;
    }
    public void setPaymentMethod(String Paymentmethod)
    {
        this.PaymentMethod  = Paymentmethod;
    }



}
