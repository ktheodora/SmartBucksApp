package com.example.iseeproject;

import java.util.List;

public class User {
    private String username, name, surname, password_raw;
    private double income, rent, bills, insurance;
    private String paymentMethod;

    public User (String Username,String Password_raw, String Name, double Income, double Rent, double Bills, double Insurance) {
        this.username = Username;
        this.password_raw = Password_raw;
        this.name = Name;
      //  this.surname = Surname;
        this.income = Income;
        this.rent = Rent;
        this.bills = Bills;
        this.insurance = Insurance;
    }

    public void setUsername(String Username) {
        this.username = Username;
    }
    public String getUsername() {
        return username;
    }

    public void setPwd(String Password_raw) {
        this.password_raw = Password_raw;
    }
    public String getPwd() {
        return password_raw;
    }

    public void setPaymentMethod(String Paymentmethod)
    {
            this.paymentMethod  = Paymentmethod;
    }

    public void setName(String Name) {
        this.name = Name;
    }
    public String getName() {
        return name;
    }

   /** public void setSurname(String Surname) {
        this.surname = Surname;
    }
    public String getSurname() {
        return surname;
    } **/

    public void setIncome(double Income) {
        this.income = Income;
    }
    public double getIncome() {
        return income;
    }

    public void setRent(double Rent) {
        this.rent= Rent;
    }
    public double getRent() {
        return rent;
    }

    public void setBills(double Bills) {
        this.bills = Bills;
    }
    public double getBills() {
        return bills;
    }

    public void setInsurance(double Insurance) {
        this.insurance = Insurance;
    }
    public double getInsurance() {
        return insurance;
    }




}
