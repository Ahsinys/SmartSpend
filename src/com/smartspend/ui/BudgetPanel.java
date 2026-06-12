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

        // ================= HEADING =================
        JLabel headingLabel = new JLabel(
                "Budget Management",
                SwingConstants.CENTER
        );

        headingLabel.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        headingLabel.setBorder(
                BorderFactory.createEmptyBorder(
                        10, 0, 20, 0
                )
        );

        // ================= FORM =================
        JPanel formPanel = new JPanel(
                new GridLayout(2, 4, 10, 10)
        );

        categoryCombo = new JComboBox<>(new String[]{
                "Food",
                "Transport",
                "Rent",
                "Education",
                "Health",
                "Entertainment",
                "Other"
        });

        limitField = new JTextField();

        LocalDate now = LocalDate.now();

        monthSpinner = new JSpinner(
                new SpinnerNumberModel(
                        now.getMonthValue(),
                        1,
                        12,
                        1
                )
        );

        yearSpinner = new JSpinner(
                new SpinnerNumberModel(
                        now.getYear(),
                        2020,
                        2100,
                        1
                )
        );

        // Remove commas in year (2026 instead of 2,026)
        yearSpinner.setEditor(
                new JSpinner.NumberEditor(
                        yearSpinner,
                        "#"
                )
        );

        saveButton = new JButton("Save Budget");

        formPanel.add(new JLabel("Category"));
        formPanel.add(categoryCombo);

        formPanel.add(new JLabel("Limit Amount"));
        formPanel.add(limitField);

        formPanel.add(new JLabel("Month"));
        formPanel.add(monthSpinner);

        formPanel.add(new JLabel("Year"));
        formPanel.add(yearSpinner);

        // ================= TOP PANEL =================
        JPanel topPanel = new JPanel(
                new BorderLayout()
        );

        topPanel.add(
                headingLabel,
                BorderLayout.NORTH
        );

        topPanel.add(
                formPanel,
                BorderLayout.CENTER
        );

        add(
                topPanel,
                BorderLayout.NORTH
        );

        // ================= TABLE =================
        tableModel = new DefaultTableModel(
                new String[]{
                        "Category",
                        "Limit",
                        "Spent",
                        "Remaining",
                        "Status"
                },
                0
        ) {
            @Override
            public boolean isCellEditable(
                    int row,
                    int column
            ) {
                return column == 2;
            }
        };

        budgetTable = new JTable(tableModel);

        tableModel.addTableModelListener(e -> {

            int row = e.getFirstRow();
            int column = e.getColumn();

            if (column == 2) {

                try {

                    double limit =
                            Double.parseDouble(
                                    tableModel.getValueAt(row, 1)
                                            .toString()
                            );

                    double spent =
                            Double.parseDouble(
                                    tableModel.getValueAt(row, 2)
                                            .toString()
                            );

                    double remaining =
                            limit - spent;

                    tableModel.setValueAt(
                            remaining,
                            row,
                            3
                    );

                    String status;

                    if (spent < limit * 0.80) {

                        status = "OK";

                    } else if (spent <= limit) {

                        status = "WARNING";

                    } else {

                        status = "OVER BUDGET";
                    }

                    tableModel.setValueAt(
                            status,
                            row,
                            4
                    );

                } catch (Exception ex) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Invalid spent amount"
                    );
                }
            }
        });

        budgetTable.getColumnModel()
                .getColumn(4)
                .setCellRenderer(
                        new StatusCellRenderer()
                );

        add(
                new JScrollPane(budgetTable),
                BorderLayout.CENTER
        );

        add(
                saveButton,
                BorderLayout.SOUTH
        );

        // ================= SAVE =================
        saveButton.addActionListener(e -> {

            try {

                Budget budget = new Budget(
                        generateBudgetId(),
                        Session.currentUser.getId(),
                        categoryCombo
                                .getSelectedItem()
                                .toString(),
                        Double.parseDouble(
                                limitField.getText()
                        ),
                        (Integer) monthSpinner.getValue(),
                        (Integer) yearSpinner.getValue()
                );

                budgetService.saveBudget(budget);

                JOptionPane.showMessageDialog(
                        this,
                        "Budget Saved Successfully"
                );

                loadBudgets();

                limitField.setText("");

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Invalid Budget Data"
                );
            }
        });

        // ================= AUTO REFRESH =================
        monthSpinner.addChangeListener(
                e -> loadBudgets()
        );

        yearSpinner.addChangeListener(
                e -> loadBudgets()
        );

        loadBudgets();
    }

    // ================= ID GENERATOR =================
    private int generateBudgetId() {

        List<String[]> data =
                budgetFileHandler.getAllRows();

        int maxId = 0;

        for (String[] row : data) {

            try {

                maxId = Math.max(
                        maxId,
                        Integer.parseInt(row[0])
                );

            } catch (Exception ignored) {
            }
        }

        return maxId + 1;
    }

    // ================= LOAD TABLE =================
    private void loadBudgets() {

        tableModel.setRowCount(0);

        List<Budget> budgets =
                budgetFileHandler.getAllBudgets();

        for (Budget b : budgets) {

            double spent = 0;

            double remaining =
                    budgetService.getRemaining(
                            b,
                            spent
                    );

            String status =
                    budgetService.getStatus(
                            b.getLimitAmount(),
                            spent
                    );

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