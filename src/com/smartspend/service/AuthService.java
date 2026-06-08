package com.smartspend.service;

import com.smartspend.filehandler.UserFileHandler;
import com.smartspend.model.User;
import com.smartspend.util.FileManager;

public class AuthService {

    private UserFileHandler userFileHandler;

    public AuthService() {
        userFileHandler = new UserFileHandler("data/users.csv");
    }

    // LOGIN
    public User login(String username, String password) {

        if (!isValidInput(username, password)) {
            return null;
        }

        return userFileHandler.login(username, password);
    }

    // REGISTER
    public boolean register(String username,
                            String password,
                            String email) {

        if (!isValidInput(username, password)) {
            return false;
        }

        if (userFileHandler.usernameExists(username)) {
            return false;
        }

        int id = FileManager.getNextId("data/users.csv");

        User user = new User(
                id,
                username,
                password,
                email
        );

        userFileHandler.add(user);

        return true;
    }

    // VALIDATION
    private boolean isValidInput(String username,
                                 String password) {

        if (username == null || username.trim().isEmpty()) {
            return false;
        }

        if (password == null || password.trim().isEmpty()) {
            return false;
        }

        if (password.length() < 6) {
            return false;
        }

        return true;
    }
}