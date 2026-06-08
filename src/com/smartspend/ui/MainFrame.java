package com.smartspend.ui;

import com.smartspend.model.User;
import com.smartspend.util.Session;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private User currentUser;

    private CardLayout cardLayout;
    private JPanel contentPanel;

    private JButton dashboardBtn;
    private JButton transactionsBtn;
    private JButton budgetBtn;
    private JButton searchBtn;
    private JButton reportsBtn;
    private JButton logoutBtn;

    public MainFrame() {

        currentUser = Session.currentUser;

        setTitle("SmartSpend");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        // LEFT NAVIGATION PANEL
        JPanel navPanel = new JPanel();
        navPanel.setLayout(new GridLayout(6, 1));

        dashboardBtn = new JButton("Dashboard");
        transactionsBtn = new JButton("Transactions");
        budgetBtn = new JButton("Budget");
        searchBtn = new JButton("Search");
        reportsBtn = new JButton("Reports");
        logoutBtn = new JButton("Logout");

        navPanel.add(dashboardBtn);
        navPanel.add(transactionsBtn);
        navPanel.add(budgetBtn);
        navPanel.add(searchBtn);
        navPanel.add(reportsBtn);
        navPanel.add(logoutBtn);

        add(navPanel, BorderLayout.WEST);

        // CARD LAYOUT PANEL
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        JPanel dashboardPanel = new JPanel();
        dashboardPanel.add(new JLabel("Dashboard Panel"));

        JPanel transactionPanel = new JPanel();
        transactionPanel.add(new JLabel("Transaction Panel"));

        JPanel budgetPanel = new JPanel();
        budgetPanel.add(new JLabel("Budget Panel"));

        JPanel searchPanel = new JPanel();
        searchPanel.add(new JLabel("Search Panel"));

        JPanel reportPanel = new JPanel();
        reportPanel.add(new JLabel("Report Panel"));

        contentPanel.add(dashboardPanel, "Dashboard");
        contentPanel.add(transactionPanel, "Transactions");
        contentPanel.add(budgetPanel, "Budget");
        contentPanel.add(searchPanel, "Search");
        contentPanel.add(reportPanel, "Reports");

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

    public void showPanel(String name) {
        cardLayout.show(contentPanel, name);
    }
}