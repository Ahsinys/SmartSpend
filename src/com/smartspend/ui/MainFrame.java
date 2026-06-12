package com.smartspend.ui;

import com.smartspend.util.Session;

import javax.swing.*;
import java.awt.*;
import com.smartspend.ui.SearchPanel;
import com.smartspend.ui.ReportPanel;

public class MainFrame extends JFrame {

    private CardLayout cardLayout;
    private JPanel contentPanel;

    public MainFrame() {

        setTitle("SmartSpend");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        // LEFT NAVIGATION PANEL
        JPanel navPanel = new JPanel();
        navPanel.setLayout(new GridLayout(6, 1));

        JButton dashboardBtn = new JButton("Dashboard");
        JButton transactionsBtn = new JButton("Transactions");
        JButton budgetBtn = new JButton("Budget");
        JButton searchBtn = new JButton("Search");
        JButton reportsBtn = new JButton("Reports");
        JButton logoutBtn = new JButton("Logout");

        navPanel.add(dashboardBtn);
        navPanel.add(transactionsBtn);
        navPanel.add(budgetBtn);
        navPanel.add(searchBtn);
        navPanel.add(reportsBtn);
        navPanel.add(logoutBtn);

        add(navPanel, BorderLayout.WEST);

        // CARD LAYOUT
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        DashboardPanel dashboardPanel = new DashboardPanel();

        TransactionPanel transactionPanel = new TransactionPanel();

        BudgetPanel budgetPanel = new BudgetPanel();

        SearchPanel searchPanel = new SearchPanel();

        ReportPanel reportsPanel = new ReportPanel();

        contentPanel.add(dashboardPanel, "Dashboard");
        contentPanel.add(transactionPanel, "Transactions");
        contentPanel.add(budgetPanel, "Budget");
        contentPanel.add(searchPanel, "Search");
        contentPanel.add(reportsPanel, "Reports");

        add(contentPanel, BorderLayout.CENTER);

        // BUTTON EVENTS
        dashboardBtn.addActionListener(e -> showPanel("Dashboard"));
        transactionsBtn.addActionListener(e -> showPanel("Transactions"));
        budgetBtn.addActionListener(e -> showPanel("Budget"));
        searchBtn.addActionListener(e -> showPanel("Search"));
        reportsBtn.addActionListener(e -> showPanel("Reports"));

        logoutBtn.addActionListener(e -> {
            Session.currentUser = null;
            dispose();
            new LoginFrame();
        });

        showPanel("Dashboard");

        setVisible(true);
    }

    private void showPanel(String panelName) {
        cardLayout.show(contentPanel, panelName);
    }
}