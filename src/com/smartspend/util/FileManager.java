package com.smartspend.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    // Reads all lines from a CSV file
    public static List<String[]> readLines(String filePath) {
        List<String[]> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            String line;

            while ((line = br.readLine()) != null) {
                lines.add(line.split(","));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }

    // Writes all data back to a CSV file
    public static void writeLines(String filePath, List<String[]> lines) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {

            for (String[] row : lines) {
                bw.write(String.join(",", row));
                bw.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Returns the next available ID
    public static int getNextId(String filePath) {

        List<String[]> lines = readLines(filePath);
        int maxId = 0;

        for (String[] row : lines) {

            if (row.length > 0) {

                try {
                    int id = Integer.parseInt(row[0]);

                    if (id > maxId) {
                        maxId = id;
                    }

                } catch (NumberFormatException e) {
                    // Ignore invalid rows
                }
            }
        }

        return maxId + 1;
    }
}