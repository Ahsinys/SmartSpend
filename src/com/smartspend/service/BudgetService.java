package com.smartspend.service;

import com.smartspend.filehandler.BudgetFileHandler;
import com.smartspend.filehandler.TransactionFileHandler;
import com.smartspend.model.Budget;

public class BudgetService {

    private BudgetFileHandler budgetFileHandler;
    private TransactionFileHandler transactionFileHandler;

    public BudgetService() {

        budgetFileHandler =
                new BudgetFileHandler("data/budgets.csv");

        transactionFileHandler =
                new TransactionFileHandler("data/transactions.csv");
    }


    // Save or Update Budget
    public void saveBudget(Budget budget) {

        try {

            Budget existingBudget =
                    budgetFileHandler.getByCategory(
                            budget.getUserId(),
                            budget.getCategory(),
                            budget.getMonth(),
                            budget.getYear()
                    );

            if (existingBudget != null) {

                budget.setId(existingBudget.getId());

                budgetFileHandler.update(budget);

            } else {

                budgetFileHandler.add(budget);
            }

        } catch (Exception e) {

            System.out.println(
                    "Error saving budget: " + e.getMessage()
            );
        }
    }

    // Total spent for category in month
    public double getSpent(
            int userId,
            String category,
            int month,
            int year
    ) {

        try {

            return transactionFileHandler.getTotalByCategory(
                    userId,
                    category,
                    month,
                    year
            );

        } catch (Exception e) {

            return 0;
        }
    }


    // Remaining Budget
    public double getRemaining(
            Budget budget,
            double spent
    ) {

        return budget.getLimitAmount() - spent;
    }

    // Budget Status
    public String getStatus(
            double limit,
            double spent
    ) {

        if (limit <= 0) {
            return "OVER BUDGET";
        }

        double percentage =
                (spent / limit) * 100;

        if (percentage < 80) {

            return "OK";

        } else if (percentage <= 100) {

            return "WARNING";

        } else {

            return "OVER BUDGET";
        }
    }
}