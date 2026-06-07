package com.smartspend.model;

public class Budget {

    private int id;
    private int userId;
    private String category;
    private double limitAmount;
    private int month;
    private int year;

    public Budget() {
    }

    public Budget(int id, int userId, String category, double limitAmount, int month, int year)
    {
        this.id = id;
        this.userId = userId;
        this.category = category;
        this.limitAmount = limitAmount;
        this.month = month;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getLimitAmount() {
        return limitAmount;
    }

    public void setLimitAmount(double limitAmount) {
        this.limitAmount = limitAmount;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}