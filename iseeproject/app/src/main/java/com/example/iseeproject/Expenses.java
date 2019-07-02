package com.example.iseeproject;

import java.util.Date;

public class Expenses {
    private String username, category;
    //timestamp of expense creation will be converted later to a real date type on the homepage
    //when we will want to make comparison of the days that the expenses were created
    //we add unique id for deletion
    private String expenseTime;
    private double price;
    private String PaymentMethod;
    private int ID;

    public Expenses(String ExpenseTime, int id, String Username, Double Price, String Category, String PaymentMethods) {
        this.expenseTime = ExpenseTime;
        this.ID = id;
        this.username = Username;
        this.category = Category;
        this.price = Price;
        this.PaymentMethod = PaymentMethods;
    }

    public String getExpenseTime() {
        return expenseTime;
    }
    public void setExpenseTime(String ExpenseTime) {
        this.expenseTime= ExpenseTime;
    }

    public int getExpensesID() { return ID;}
    public void setExpensesID(int id) {
        this.ID = id;
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
