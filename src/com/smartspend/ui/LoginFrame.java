package com.smartspend.ui;

import com.smartspend.model.User;
import com.smartspend.service.AuthService;
import com.smartspend.util.Session;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private AuthService authService;

    // Login Components
    private JTextField loginUsernameField;
    private JPasswordField loginPasswordField;
    private JLabel loginMessageLabel;

    // Register Components
    private JTextField registerUsernameField;
    private JPasswordField registerPasswordField;
    private JTextField registerEmailField;

    public LoginFrame() {

        authService = new AuthService();

        setTitle("SmartSpend - Login");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Login", createLoginPanel());
        tabbedPane.addTab("Register", createRegisterPanel());

        add(tabbedPane);

        setVisible(true);
    }

    private JPanel createLoginPanel() {

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));

        panel.add(new JLabel("Username:"));
        loginUsernameField = new JTextField();
        panel.add(loginUsernameField);

        panel.add(new JLabel("Password:"));
        loginPasswordField = new JPasswordField();
        panel.add(loginPasswordField);

        JButton loginButton = new JButton("Login");
        panel.add(loginButton);

        loginMessageLabel = new JLabel("");
        loginMessageLabel.setForeground(Color.RED);
        panel.add(loginMessageLabel);

        loginButton.addActionListener(e -> login());

        return panel;
    }

    private JPanel createRegisterPanel() {

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));

        panel.add(new JLabel("Username:"));
        registerUsernameField = new JTextField();
        panel.add(registerUsernameField);

        panel.add(new JLabel("Password:"));
        registerPasswordField = new JPasswordField();
        panel.add(registerPasswordField);

        panel.add(new JLabel("Email:"));
        registerEmailField = new JTextField();
        panel.add(registerEmailField);

        JButton registerButton = new JButton("Register");
        panel.add(registerButton);

        registerButton.addActionListener(e -> register());

        return panel;
    }

    private void login() {

        String username = loginUsernameField.getText();
        String password = new String(loginPasswordField.getPassword());

        User user = authService.login(username, password);

        if (user != null) {

            Session.currentUser = user;

            JOptionPane.showMessageDialog(
                    this,
                    "Login Successful!"
            );

            dispose();

            new MainFrame();

        } else {

            loginMessageLabel.setText("Invalid username or password");

        }
    }

    private void register() {

        String username = registerUsernameField.getText();
        String password = new String(registerPasswordField.getPassword());
        String email = registerEmailField.getText();

        boolean success =
                authService.register(username, password, email);

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Registration Successful!"
            );

            registerUsernameField.setText("");
            registerPasswordField.setText("");
            registerEmailField.setText("");

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Registration Failed!"
            );

        }
    }
}