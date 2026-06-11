package com.smartspend.ui;

import com.smartspend.filehandler.TransactionFileHandler;
import com.smartspend.model.Transaction;
import com.smartspend.util.Session;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class SearchPanel extends JPanel {

    private JComboBox<String> categoryCombo;
    private JComboBox<String> typeCombo;

    private JTextField startDateField;
    private JTextField endDateField;
    private JTextField keywordField;

    private JButton searchButton;
    private JButton resetButton;

    private JTable table;
    private DefaultTableModel tableModel;

    private JLabel resultLabel;

    private TransactionFileHandler transactionFileHandler;

    public SearchPanel() {

        transactionFileHandler =
                new TransactionFileHandler("data/transactions.csv");

        setLayout(new BorderLayout());

        // ================= FILTER PANEL =================
        JPanel filterPanel = new JPanel(new GridLayout(2, 5, 10, 10));

        categoryCombo = new JComboBox<>(new String[]{
                "All", "Food", "Transport", "Rent",
                "Education", "Health", "Entertainment", "Other"
        });

        typeCombo = new JComboBox<>(new String[]{
                "All", "Income", "Expense"
        });

        startDateField = new JTextField();
        endDateField = new JTextField();
        keywordField = new JTextField();

        searchButton = new JButton("Search");
        resetButton = new JButton("Reset");

        filterPanel.add(new JLabel("Category"));
        filterPanel.add(new JLabel("Type"));
        filterPanel.add(new JLabel("Start Date"));
        filterPanel.add(new JLabel("End Date"));
        filterPanel.add(new JLabel("Keyword"));

        filterPanel.add(categoryCombo);
        filterPanel.add(typeCombo);
        filterPanel.add(startDateField);
        filterPanel.add(endDateField);
        filterPanel.add(keywordField);

        add(filterPanel, BorderLayout.NORTH);

        // ================= TABLE =================
        tableModel = new DefaultTableModel(
                new String[]{"Type", "Category", "Amount", "Note", "Date"},
                0
        );

        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // ================= BOTTOM PANEL =================
        JPanel bottomPanel = new JPanel(new BorderLayout());

        JPanel buttonPanel = new JPanel();

        buttonPanel.add(searchButton);
        buttonPanel.add(resetButton);

        resultLabel = new JLabel("Results: 0");

        bottomPanel.add(buttonPanel, BorderLayout.WEST);
        bottomPanel.add(resultLabel, BorderLayout.EAST);

        add(bottomPanel, BorderLayout.SOUTH);

        // ================= EVENTS =================

        searchButton.addActionListener(e -> performSearch());

        resetButton.addActionListener(e -> {
            clearFilters();
            loadAll();
        });

        // INITIAL LOAD
        loadAll();
    }

    // ================= SEARCH =================
    private void performSearch() {

        String category = (String) categoryCombo.getSelectedItem();
        String type = (String) typeCombo.getSelectedItem();
        String startDate = startDateField.getText().trim();
        String endDate = endDateField.getText().trim();
        String keyword = keywordField.getText().trim();

        List<Transaction> results =
                transactionFileHandler.search(
                        Session.currentUser.getId(),
                        category,
                        type,
                        startDate,
                        endDate,
                        keyword
                );

        updateTable(results);
    }

    // ================= LOAD ALL =================
    private void loadAll() {

        List<Transaction> all =
                transactionFileHandler.getByUser(
                        Session.currentUser.getId()
                );

        updateTable(all);
    }

    // ================= UPDATE TABLE =================
    private void updateTable(List<Transaction> list) {

        tableModel.setRowCount(0);

        for (Transaction t : list) {

            tableModel.addRow(new Object[]{
                    t.getType(),
                    t.getCategory(),
                    t.getAmount(),
                    t.getNote(),
                    t.getDate()
            });
        }

        resultLabel.setText("Results: " + list.size());
    }

    // ================= CLEAR FILTERS =================
    private void clearFilters() {

        categoryCombo.setSelectedIndex(0);
        typeCombo.setSelectedIndex(0);

        startDateField.setText("");
        endDateField.setText("");
        keywordField.setText("");
    }
}