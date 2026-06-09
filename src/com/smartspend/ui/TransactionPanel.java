package com.smartspend.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TransactionPanel extends JPanel {

    private JComboBox<String> typeCombo;
    private JComboBox<String> categoryCombo;

    private JTextField amountField;
    private JTextField noteField;
    private JTextField dateField;

    private JButton addButton;
    private JButton clearButton;

    private JTable transactionTable;
    private DefaultTableModel tableModel;

    public TransactionPanel() {

        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(3, 4, 10, 10));

        typeCombo = new JComboBox<>(
                new String[]{"Income", "Expense"}
        );

        categoryCombo = new JComboBox<>(
                new String[]{
                        "Food",
                        "Transport",
                        "Rent",
                        "Education",
                        "Health",
                        "Entertainment",
                        "Other"
                }
        );

        amountField = new JTextField();
        noteField = new JTextField();
        dateField = new JTextField();

        addButton = new JButton("Add");
        clearButton = new JButton("Clear");

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

        add(formPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(
                new String[]{
                        "ID",
                        "Type",
                        "Category",
                        "Amount",
                        "Note",
                        "Date"
                },
                0
        );

        transactionTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(transactionTable);

        add(scrollPane, BorderLayout.CENTER);
    }
}