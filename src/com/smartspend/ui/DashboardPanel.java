package com.smartspend.ui;

import com.smartspend.filehandler.BudgetFileHandler;
import com.smartspend.model.Budget;
import com.smartspend.service.BudgetService;
import com.smartspend.service.ReportService;
import com.smartspend.util.Session;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

/**
 * Dashboard panel showing monthly summary
 * and current budget status.
 */
public class DashboardPanel extends JPanel {

    private JLabel incomeLabel;
    private JLabel expenseLabel;
    private JLabel savingsLabel;
    private JLabel monthLabel;

    private JTable budgetTable;
    private DefaultTableModel tableModel;

    private ReportService reportService;
    private BudgetService budgetService;
    private BudgetFileHandler budgetFileHandler;

    public DashboardPanel() {

        reportService = new ReportService();
        budgetService = new BudgetService();
        budgetFileHandler = new BudgetFileHandler("data/budgets.csv");

        setLayout(new BorderLayout(30, 30));

        LocalDate now = LocalDate.now();

        monthLabel = new JLabel(
                "Dashboard Summary - "
                        + now.getMonth()
                        + " "
                        + now.getYear(),
                SwingConstants.CENTER
        );

        monthLabel.setFont(new Font("Arial", Font.BOLD, 20)
        );

        // =======================
        // SUMMARY CARDS
        // =======================
        JPanel cardsPanel = new JPanel(new GridLayout(1, 3, 10, 10));

        incomeLabel = new JLabel("0.00", SwingConstants.CENTER);
        expenseLabel = new JLabel("0.00", SwingConstants.CENTER);
        savingsLabel = new JLabel("0.00", SwingConstants.CENTER);

        cardsPanel.add(createCard(
                "Total Income",
                incomeLabel
        ));

        cardsPanel.add(createCard(
                "Total Expenses",
                expenseLabel
        ));

        cardsPanel.add(createCard(
                "Savings",
                savingsLabel
        ));

        JPanel topPanel =
                new JPanel(new BorderLayout());

        topPanel.add(
                monthLabel,
                BorderLayout.NORTH
        );

        topPanel.add(
                cardsPanel,
                BorderLayout.CENTER
        );

        add(topPanel, BorderLayout.NORTH);

        // =======================
        // BUDGET TABLE
        // =======================
        tableModel = new DefaultTableModel(
                new String[]{
                        "Category",
                        "Limit",
                        "Spent",
                        "Remaining",
                        "Status"
                },
                0
        );

        budgetTable = new JTable(tableModel);

        budgetTable.getColumnModel()
                .getColumn(4)
                .setCellRenderer(
                        new StatusCellRenderer()
                );

        add(
                new JScrollPane(budgetTable),
                BorderLayout.CENTER
        );

        loadDashboard();
    }

    /**
     * Creates summary card panel.
     */
    private JPanel createCard(
            String title,
            JLabel valueLabel
    ) {

        JPanel card =
                new JPanel(new BorderLayout());

        card.setBorder(
                BorderFactory.createTitledBorder(title)
        );

        valueLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24
                )
        );

        card.add(
                valueLabel,
                BorderLayout.CENTER
        );

        return card;
    }

    /**
     * Loads dashboard data.
     */
    private void loadDashboard() {

        if (Session.currentUser == null) {
            return;
        }

        int userId =
                Session.currentUser.getId();

        LocalDate now =
                LocalDate.now();

        int month =
                now.getMonthValue();

        int year =
                now.getYear();

        // =======================
        // REPORT DATA
        // =======================
        double income =
                reportService.getTotalIncome(
                        userId,
                        month,
                        year
                );

        double expenses =
                reportService.getTotalExpenses(
                        userId,
                        month,
                        year
                );

        double savings =
                reportService.getSavings(
                        userId,
                        month,
                        year
                );

        incomeLabel.setText(
                String.format("%.2f", income)
        );

        expenseLabel.setText(
                String.format("%.2f", expenses)
        );

        savingsLabel.setText(
                String.format("%.2f", savings)
        );

        // =======================
        // BUDGET TABLE
        // =======================
        tableModel.setRowCount(0);

        List<Budget> budgets =
                budgetFileHandler.getByUser(
                        userId,
                        month,
                        year
                );

        for (Budget budget : budgets) {

            double spent =
                    budgetService.getSpent(
                            userId,
                            budget.getCategory(),
                            month,
                            year
                    );

            double remaining =
                    budgetService.getRemaining(
                            budget,
                            spent
                    );

            String status =
                    budgetService.getStatus(
                            budget.getLimitAmount(),
                            spent
                    );

            tableModel.addRow(
                    new Object[]{
                            budget.getCategory(),
                            budget.getLimitAmount(),
                            spent,
                            remaining,
                            status
                    }
            );
        }
    }

    /**
     * Refresh dashboard whenever
     * panel becomes visible.
     */
    @Override
    public void setVisible(boolean visible) {

        super.setVisible(visible);

        if (visible) {
            loadDashboard();
        }
    }
}