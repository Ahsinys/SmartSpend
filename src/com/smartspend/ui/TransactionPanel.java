package com.smartspend.ui;

import com.smartspend.model.Transaction;
import com.smartspend.service.TransactionService;
import com.smartspend.util.Session;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.util.List;

public class TransactionPanel extends JPanel {

    private JComboBox<String> typeCombo;
    private JComboBox<String> categoryCombo;

    private JTextField amountField;
    private JTextField noteField;
    private JTextField dateField;

    private JButton addButton;
    private JButton clearButton;
    private JButton deleteButton;

    private JTable transactionTable;
    private DefaultTableModel tableModel;

    private TransactionService transactionService;
    private int selectedTransactionId = -1;

    public TransactionPanel() {

        transactionService = new TransactionService();

        setLayout(new BorderLayout());

        // ================= FORM PANEL =================
        JPanel formPanel = new JPanel(new GridLayout(3, 4, 10, 10));

        typeCombo = new JComboBox<>(new String[]{"Income", "Expense"});

        categoryCombo = new JComboBox<>(new String[]{
                "Food", "Transport", "Rent",
                "Education", "Health", "Entertainment", "Other"
        });

        amountField = new JTextField();
        noteField = new JTextField();
        dateField = new JTextField();

        dateField.setText(new Date(System.currentTimeMillis()).toString());

        addButton = new JButton("Add");
        clearButton = new JButton("Clear");
        deleteButton = new JButton("Delete");

        formPanel.add(new JLabel("Type"));
        formPanel.add(typeCombo);

        formPanel.add(new JLabel("Category"));
        formPanel.add(categoryCombo);

        formPanel.add(new JLabel("Amount"));
        formPanel.add(amountField);

        formPanel.add(new JLabel("Note"));
        formPanel.add(noteField);

        formPanel.add(new JLabel("Date"));
        formPanel.add(dateField);

        formPanel.add(addButton);
        formPanel.add(clearButton);

        JLabel titleLabel = new JLabel(
                "Transaction Management",
                SwingConstants.CENTER
        );

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24
                )
        );

        JPanel topPanel = new JPanel(
                new BorderLayout()
        );

        topPanel.add(
                titleLabel,
                BorderLayout.NORTH
        );

        topPanel.add(
                formPanel,
                BorderLayout.CENTER
        );
        titleLabel.setBorder(
                BorderFactory.createEmptyBorder(10, 0, 30, 0)
        );

        add(
                topPanel,
                BorderLayout.NORTH
        );

        // ================= TABLE =================
        tableModel = new DefaultTableModel(
                new String[]{"ID", "Type", "Category", "Amount", "Note", "Date"},
                0
        );

        transactionTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(transactionTable);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        tablePanel.add(deleteButton, BorderLayout.SOUTH);

        add(tablePanel, BorderLayout.CENTER);

        // ================= EVENTS =================

        clearButton.addActionListener(e -> clearForm());

        addButton.addActionListener(e -> {

            try {

                int id = generateTransactionId();

                Transaction transaction = new Transaction(
                        id,
                        Session.currentUser.getId(),
                        typeCombo.getSelectedItem().toString(),
                        categoryCombo.getSelectedItem().toString(),
                        Double.parseDouble(amountField.getText()),
                        noteField.getText(),
                        Date.valueOf(dateField.getText())
                );

                boolean success = transactionService.addTransaction(transaction);

                if (success) {
                    JOptionPane.showMessageDialog(this, "Transaction Added");
                    loadTable();
                    clearForm();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add transaction");
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input");
            }
        });

        // SINGLE row selection listener (FIXED)
        transactionTable.getSelectionModel().addListSelectionListener(e -> {

            int row = transactionTable.getSelectedRow();

            if (row >= 0) {

                selectedTransactionId =
                        Integer.parseInt(tableModel.getValueAt(row, 0).toString());

                typeCombo.setSelectedItem(tableModel.getValueAt(row, 1));
                categoryCombo.setSelectedItem(tableModel.getValueAt(row, 2));
                amountField.setText(tableModel.getValueAt(row, 3).toString());
                noteField.setText(tableModel.getValueAt(row, 4).toString());
                dateField.setText(tableModel.getValueAt(row, 5).toString());
            }
        });

        deleteButton.addActionListener(e -> {

            if (selectedTransactionId == -1) {
                JOptionPane.showMessageDialog(this, "Select a transaction first");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {

                transactionService.deleteTransaction(selectedTransactionId);

                loadTable();
                clearForm();

                selectedTransactionId = -1;

                JOptionPane.showMessageDialog(this, "Deleted successfully");
            }
        });

        // ================= LOAD =================
        loadTable();
    }

    private void clearForm() {

        amountField.setText("");
        noteField.setText("");

        dateField.setText(new Date(System.currentTimeMillis()).toString());

        typeCombo.setSelectedIndex(0);
        categoryCombo.setSelectedIndex(0);

        transactionTable.clearSelection();
        selectedTransactionId = -1;
    }

    private int generateTransactionId() {

        List<Transaction> transactions =
                transactionService.getAllTransactions(Session.currentUser.getId());

        int maxId = 0;

        for (Transaction t : transactions) {
            if (t.getId() > maxId) {
                maxId = t.getId();
            }
        }

        return maxId + 1;
    }

    private void loadTable() {

        tableModel.setRowCount(0);

        List<Transaction> transactions =
                transactionService.getAllTransactions(Session.currentUser.getId());

        for (Transaction t : transactions) {

            tableModel.addRow(new Object[]{
                    t.getId(),
                    t.getType(),
                    t.getCategory(),
                    t.getAmount(),
                    t.getNote(),
                    t.getDate()
            });
        }
    }
}