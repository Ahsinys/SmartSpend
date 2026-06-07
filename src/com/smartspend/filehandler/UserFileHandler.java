package com.smartspend.filehandler;

import com.smartspend.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserFileHandler extends BaseFileHandler {

    public UserFileHandler(String filePath) {
        super(filePath);
    }

    @Override
    public void add(Object obj) {
        User user = (User) obj;

        List<String[]> data = readAll();
        data.add(new String[]{
                String.valueOf(user.getId()),
                user.getUsername(),
                user.getPassword(),
                user.getEmail()
        });

        writeAll(data);
    }

    @Override
    public void update(Object obj) {
        User user = (User) obj;

        List<String[]> data = readAll();

        for (String[] row : data) {
            if (Integer.parseInt(row[0]) == user.getId()) {
                row[1] = user.getUsername();
                row[2] = user.getPassword();
                row[3] = user.getEmail();
            }
        }

        writeAll(data);
    }

    @Override
    public void delete(int id) {
        List<String[]> data = readAll();
        List<String[]> updated = new ArrayList<>();

        for (String[] row : data) {
            if (Integer.parseInt(row[0]) != id) {
                updated.add(row);
            }
        }

        writeAll(updated);
    }

    // LOGIN METHOD
    public User login(String username, String password) {
        List<String[]> data = readAll();

        for (String[] row : data) {
            if (row[1].equals(username) && row[2].equals(password)) {
                return new User(
                        Integer.parseInt(row[0]),
                        row[1],
                        row[2],
                        row[3]
                );
            }
        }
        return null;
    }

    // CHECK USER EXISTS
    public boolean usernameExists(String username) {
        List<String[]> data = readAll();

        for (String[] row : data) {
            if (row[1].equals(username)) {
                return true;
            }
        }
        return false;
    }
}