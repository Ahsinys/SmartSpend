package com.smartspend;

import com.smartspend.database.Database.DatabaseConnection;
import java.sql.Connection;

public class TestConnection {

    public static void main(String[] args) {
        try {
            Connection conn = DatabaseConnection.getInstance();
            System.out.println("Database Connected Successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}