package com.smartspend.ui;

import com.smartspend.filehandler.BudgetFileHandler;
import com.smartspend.model.Budget;
import com.smartspend.service.BudgetService;
import com.smartspend.util.Session;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class BudgetPanel extends JPanel {

    private JComboBox<String> categoryCombo;
    private JTextField limitField;

    private JSpinner monthSpinner;
    private JSpinner yearSpinner;

    private JButton saveButton;

    private JTable budgetTable;
    private DefaultTableModel tableModel;

    private BudgetService budgetService;
    private BudgetFileHandler budgetFileHandler;

    public BudgetPanel() {

        budgetService = new BudgetService();
        budgetFileHandler = new BudgetFileHandler("data/budgets.csv");

        setLayout(new BorderLayout());

        // ================= FORM =================
        JPanel formPanel = new JPanel(new GridLayout(2, 4, 10, 10));

        categoryCombo = new JComboBox<>(new String[]{
                "Food", "Transport", "Rent",
                "Education", "Health", "Entertainment", "Other"
        });

        limitField = new JTextField();

        LocalDate now = LocalDate.now();

        monthSpinner = new JSpinner(new SpinnerNumberModel(
                now.getMonthValue(), 1, 12, 1
        ));

        yearSpinner = new JSpinner(new SpinnerNumberModel(
                now.getYear(), 2020, 2100, 1
        ));

        // FIX: remove grouping commas like 2,026
        yearSpinner.setEditor(new JSpinner.NumberEditor(yearSpinner, "#"));

        saveButton = new JButton("Save Budget");

        formPanel.add(new JLabel("Category"));
        formPanel.add(categoryCombo);

        formPanel.add(new JLabel("Limit Amount"));
        formPanel.add(limitField);

        formPanel.add(new JLabel("Month"));
        formPanel.add(monthSpinner);

        formPanel.add(new JLabel("Year"));
        formPanel.add(yearSpinner);

        add(formPanel, BorderLayout.NORTH);

        // ================= TABLE =================
        tableModel = new DefaultTableModel(
                new String[]{"Category", "Limit", "Spent", "Remaining", "Status"}, 0
        );

        budgetTable = new JTable(tableModel);

        budgetTable.getColumnModel()
                .getColumn(4)
                .setCellRenderer(new StatusCellRenderer());

        add(new JScrollPane(budgetTable), BorderLayout.CENTER);
        add(saveButton, BorderLayout.SOUTH);

        // ================= SAVE =================
        saveButton.addActionListener(e -> {

            try {
                Budget budget = new Budget(
                        generateBudgetId(),
                        Session.currentUser.getId(),
                        categoryCombo.getSelectedItem().toString(),
                        Double.parseDouble(limitField.getText()),
                        (Integer) monthSpinner.getValue(),
                        (Integer) yearSpinner.getValue()
                );

                budgetService.saveBudget(budget);

                JOptionPane.showMessageDialog(this, "Budget Saved Successfully");

                System.out.println("Saved Budget -> " + budget.getCategory());

                loadBudgets();
                limitField.setText("");

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Invalid Budget Data");
            }
        });

        // ================= AUTO REFRESH =================
        monthSpinner.addChangeListener(e -> loadBudgets());
        yearSpinner.addChangeListener(e -> loadBudgets());

        loadBudgets();
    }

    // ================= ID GENERATOR =================
    private int generateBudgetId() {

        List<String[]> data = budgetFileHandler.getAllRows();

        int maxId = 0;

        for (String[] row : data) {
            try {
                maxId = Math.max(maxId, Integer.parseInt(row[0]));
            } catch (Exception ignored) {}
        }

        return maxId + 1;
    }

    // ================= LOAD TABLE =================
    private void loadBudgets() {

        tableModel.setRowCount(0);

        int userId = Session.currentUser.getId();
        int month = (Integer) monthSpinner.getValue();
        int year = (Integer) yearSpinner.getValue();

        System.out.println("Loading budgets for: " + month + "/" + year);

        List<Budget> budgets =
                budgetFileHandler.getAllBudgets();

        System.out.println("Found budgets: " + budgets.size());

        for (Budget b : budgets) {

            double spent = budgetService.getSpent(
                    b.getUserId(),
                    b.getCategory(),
                    b.getMonth(),
                    b.getYear()
            );

            double remaining = budgetService.getRemaining(b, spent);

            String status = budgetService.getStatus(b.getLimitAmount(), spent);

            tableModel.addRow(new Object[]{
                    b.getCategory(),
                    b.getLimitAmount(),
                    spent,
                    remaining,
                    status
            });
        }
    }
}
