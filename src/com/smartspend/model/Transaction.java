package com.smartspend.model;

import java.time.LocalDate;

public class Transaction {

    private int id;
    private int userId;
    private String type;
    private String category;
    private double amount;
    private String note;
    private LocalDate date;

    public Transaction() {
    }

    public Transaction(int id, int userId, String type, String category,
                       double amount, String note, LocalDate date) {
        this.id = id;
        this.userId = userId;
        this.type = type;
        this.category = category;
        this.amount = amount;
        this.note = note;
        this.date = date;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return category + " - " + amount;
    }

    // CSV conversion method
    public String toCsvRow() {
        return id + "," +
                userId + "," +
                type + "," +
                category + "," +
                amount + "," +
                note + "," +
                date;
    }
}