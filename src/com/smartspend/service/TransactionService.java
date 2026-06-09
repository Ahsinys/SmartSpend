package com.smartspend.service;

import com.smartspend.filehandler.TransactionFileHandler;
import com.smartspend.model.Transaction;

import java.util.List;

public class TransactionService {

    private TransactionFileHandler transactionFileHandler;

    public TransactionService() {
        transactionFileHandler =
                new TransactionFileHandler("data/transactions.csv");
    }

    public boolean addTransaction(Transaction t) {

        if (!isValid(t)) {
            return false;
        }

        transactionFileHandler.add(t);
        return true;
    }

    public boolean updateTransaction(Transaction t) {

        if (!isValid(t)) {
            return false;
        }

        transactionFileHandler.update(t);
        return true;
    }

    public void deleteTransaction(int id) {
        transactionFileHandler.delete(id);
    }

    public List<Transaction> getAllTransactions(int userId) {
        return transactionFileHandler.getByUser(userId);
    }

    private boolean isValid(Transaction t) {

        if (t == null) {
            return false;
        }

        if (t.getAmount() <= 0) {
            return false;
        }

        if (t.getCategory() == null ||
                t.getCategory().trim().isEmpty()) {
            return false;
        }

        if (t.getType() == null ||
                t.getType().trim().isEmpty()) {
            return false;
        }

        return true;
    }
}