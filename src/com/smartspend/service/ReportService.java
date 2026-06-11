package com.smartspend.service;

import com.smartspend.filehandler.TransactionFileHandler;
import com.smartspend.model.Transaction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportService {

    private TransactionFileHandler transactionFileHandler;

    public ReportService() {
        transactionFileHandler =
                new TransactionFileHandler("data/transactions.csv");
    }

    // ================= TOTAL INCOME =================
    public double getTotalIncome(int userId, int month, int year) {

        double total = 0;

        List<Transaction> transactions =
                transactionFileHandler.getByUser(userId);

        for (Transaction t : transactions) {

            try {
                if (t.getType().equalsIgnoreCase("Income")
                        && t.getDate().toLocalDate().getMonthValue() == month
                        && t.getDate().toLocalDate().getYear() == year) {

                    total += t.getAmount();
                }

            } catch (Exception ignored) {
            }
        }

        return total;
    }

    // ================= TOTAL EXPENSES =================
    public double getTotalExpenses(int userId, int month, int year) {

        double total = 0;

        List<Transaction> transactions =
                transactionFileHandler.getByUser(userId);

        for (Transaction t : transactions) {

            try {
                if (t.getType().equalsIgnoreCase("Expense")
                        && t.getDate().toLocalDate().getMonthValue() == month
                        && t.getDate().toLocalDate().getYear() == year) {

                    total += t.getAmount();
                }

            } catch (Exception ignored) {
            }
        }

        return total;
    }

    // ================= SAVINGS =================
    public double getSavings(int userId, int month, int year) {

        double income = getTotalIncome(userId, month, year);
        double expense = getTotalExpenses(userId, month, year);

        return income - expense;
    }

    // ================= CATEGORY BREAKDOWN =================
    public Map<String, Double> getCategoryBreakdown(int userId, int month, int year) {

        Map<String, Double> map = new HashMap<>();

        List<Transaction> transactions =
                transactionFileHandler.getByUser(userId);

        for (Transaction t : transactions) {

            try {
                if (t.getType().equalsIgnoreCase("Expense")
                        && t.getDate().toLocalDate().getMonthValue() == month
                        && t.getDate().toLocalDate().getYear() == year) {

                    String category = t.getCategory();
                    double amount = t.getAmount();

                    map.put(category,
                            map.getOrDefault(category, 0.0) + amount);
                }

            } catch (Exception ignored) {
            }
        }

        return map;
    }
}