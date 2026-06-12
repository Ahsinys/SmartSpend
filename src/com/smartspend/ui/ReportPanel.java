package com.smartspend.ui;

import com.smartspend.service.ReportService;
import com.smartspend.util.Session;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Map;

public class ReportPanel extends JPanel {

    private JSpinner monthSpinner;
    private JSpinner yearSpinner;

    private JButton generateButton;

    private JLabel incomeLabel;
    private JLabel expenseLabel;
    private JLabel savingsLabel;

    private JTable table;
    private DefaultTableModel tableModel;

    private ReportService reportService;

    public ReportPanel() {

        reportService = new ReportService();

        setLayout(new BorderLayout());

        // ================= HEADING =================
        JLabel headingLabel = new JLabel(
                "Monthly Financial Reports",
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

        // ================= TOP FILTER =================
        JPanel topPanel = new JPanel(new FlowLayout());

        monthSpinner = new JSpinner(
                new SpinnerNumberModel(1, 1, 12, 1)
        );

        yearSpinner = new JSpinner(
                new SpinnerNumberModel(2026, 2015, 2100, 1)
        );

        // Remove comma formatting (2,026 → 2026)
        yearSpinner.setEditor(
                new JSpinner.NumberEditor(yearSpinner, "#")
        );

        generateButton = new JButton("Generate Report");

        topPanel.add(new JLabel("Month:"));
        topPanel.add(monthSpinner);

        topPanel.add(new JLabel("Year:"));
        topPanel.add(yearSpinner);

        topPanel.add(generateButton);

        // ================= HEADER SECTION =================
        JPanel headerPanel = new JPanel(
                new BorderLayout()
        );

        headerPanel.add(
                headingLabel,
                BorderLayout.NORTH
        );

        headerPanel.add(
                topPanel,
                BorderLayout.CENTER
        );

        add(
                headerPanel,
                BorderLayout.NORTH
        );

        // ================= SUMMARY PANEL =================
        JPanel summaryPanel = new JPanel(
                new GridLayout(3, 1, 5, 5)
        );

        summaryPanel.setBorder(
                BorderFactory.createTitledBorder(
                        "Summary"
                )
        );

        incomeLabel = new JLabel("Total Income: 0");
        expenseLabel = new JLabel("Total Expenses: 0");
        savingsLabel = new JLabel("Savings: 0");

        incomeLabel.setFont(
                new Font("Arial", Font.BOLD, 16)
        );

        expenseLabel.setFont(
                new Font("Arial", Font.BOLD, 16)
        );

        savingsLabel.setFont(
                new Font("Arial", Font.BOLD, 16)
        );

        summaryPanel.add(incomeLabel);
        summaryPanel.add(expenseLabel);
        summaryPanel.add(savingsLabel);

        add(
                summaryPanel,
                BorderLayout.WEST
        );

        // ================= TABLE =================
        tableModel = new DefaultTableModel(
                new String[]{
                        "Category",
                        "Amount"
                },
                0
        );

        table = new JTable(tableModel);

        add(
                new JScrollPane(table),
                BorderLayout.CENTER
        );

        // ================= ACTION =================
        generateButton.addActionListener(
                e -> generateReport()
        );
    }

    // ================= GENERATE REPORT =================
    private void generateReport() {

        int userId = Session.currentUser.getId();

        int month =
                (Integer) monthSpinner.getValue();

        int year =
                (Integer) yearSpinner.getValue();

        double income =
                reportService.getTotalIncome(
                        userId,
                        month,
                        year
                );

        double expense =
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

        // Update labels
        incomeLabel.setText(
                "Total Income: " + income
        );

        expenseLabel.setText(
                "Total Expenses: " + expense
        );

        savingsLabel.setText(
                "Savings: " + savings
        );

        // Load category breakdown
        Map<String, Double> map =
                reportService.getCategoryBreakdown(
                        userId,
                        month,
                        year
                );

        tableModel.setRowCount(0);

        for (Map.Entry<String, Double> entry : map.entrySet()) {

            tableModel.addRow(
                    new Object[]{
                            entry.getKey(),
                            entry.getValue()
                    }
            );
        }
    }
}