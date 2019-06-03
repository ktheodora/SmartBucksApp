package com.example.iseeproject;

import java.util.List;

public class User {

    private String username, name, password_raw, email;
    private double income, budget, rent, bills, insurance;

    public User (String Username,String Password_raw, String Name, String Email, double Income, double Budget, double Rent, double Bills, double Insurance) {
        this.username = Username;
        this.password_raw = Password_raw;
        this.name = Name;
        this.email = Email;
        this.income = Income;
        this.budget = Budget;
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

    public void setName(String Name) {
        this.name = Name;
    }
    public String getName() {
        return name;
    }

    public void setEmail(String Email) {
        this.email = Email;
    }
    public String getEmail() {
        return email;
    }

    public void setBudget(double Budget) {
        this.budget = Budget;
    }
    public double getBudget() {
        return budget;
    }

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
