package com.smartspend.filehandler;

import com.smartspend.model.Transaction;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransactionFileHandler extends BaseFileHandler {

    public TransactionFileHandler(String filePath) {
        super(filePath);
    }

    // ================= ADD =================
    @Override
    public void add(Object obj) {

        Transaction t = (Transaction) obj;

        List<String[]> data = readAll();

        data.add(new String[]{
                String.valueOf(t.getId()),
                String.valueOf(t.getUserId()),
                t.getType(),
                t.getCategory(),
                String.valueOf(t.getAmount()),
                t.getNote(),
                t.getDate() != null ? t.getDate().toString() : ""
        });

        writeAll(data);
    }

    // ================= UPDATE =================
    @Override
    public void update(Object obj) {

        Transaction t = (Transaction) obj;

        List<String[]> data = readAll();

        for (String[] row : data) {

            try {
                if (Integer.parseInt(row[0]) == t.getId()) {

                    row[1] = String.valueOf(t.getUserId());
                    row[2] = t.getType();
                    row[3] = t.getCategory();
                    row[4] = String.valueOf(t.getAmount());
                    row[5] = t.getNote();
                    row[6] = t.getDate() != null ? t.getDate().toString() : "";
                }

            } catch (Exception ignored) {}
        }

        writeAll(data);
    }

    // ================= DELETE =================
    @Override
    public void delete(int id) {

        List<String[]> data = readAll();
        List<String[]> updated = new ArrayList<>();

        for (String[] row : data) {

            try {
                if (Integer.parseInt(row[0]) != id) {
                    updated.add(row);
                }
            } catch (Exception ignored) {}
        }

        writeAll(updated);
    }

    // ================= GET BY USER =================
    public List<Transaction> getByUser(int userId) {

        List<String[]> data = readAll();
        List<Transaction> result = new ArrayList<>();

        for (String[] row : data) {

            try {
                if (Integer.parseInt(row[1]) == userId) {

                    Date date;

                    if (row.length > 6 && row[6] != null && !row[6].isEmpty()) {
                        date = Date.valueOf(row[6].trim());
                    } else {
                        date = new Date(System.currentTimeMillis());
                    }

                    result.add(new Transaction(
                            Integer.parseInt(row[0]),
                            Integer.parseInt(row[1]),
                            row[2],
                            row[3],
                            Double.parseDouble(row[4]),
                            row[5],
                            date
                    ));
                }

            } catch (Exception ignored) {}
        }

        return result;
    }

    // =========================================================
    // 🔥 DAY 6 REQUIRED FEATURE: SEARCH WITH MULTIPLE FILTERS
    // =========================================================
    public List<Transaction> search(
            int userId,
            String category,
            String type,
            String startDate,
            String endDate,
            String keyword
    ) {

        List<Transaction> all = getByUser(userId);
        List<Transaction> result = new ArrayList<>();

        for (Transaction t : all) {

            try {

                // ---------------- CATEGORY FILTER ----------------
                if (!category.equalsIgnoreCase("All")
                        && !t.getCategory().equalsIgnoreCase(category)) {
                    continue;
                }

                // ---------------- TYPE FILTER ----------------
                if (!type.equalsIgnoreCase("All")
                        && !t.getType().equalsIgnoreCase(type)) {
                    continue;
                }

                // ---------------- DATE FILTER ----------------
                LocalDate txDate = t.getDate().toLocalDate();

                if (startDate != null && !startDate.isEmpty()) {
                    LocalDate start = LocalDate.parse(startDate);
                    if (txDate.isBefore(start)) continue;
                }

                if (endDate != null && !endDate.isEmpty()) {
                    LocalDate end = LocalDate.parse(endDate);
                    if (txDate.isAfter(end)) continue;
                }

                // ---------------- KEYWORD FILTER ----------------
                if (keyword != null && !keyword.trim().isEmpty()) {
                    String note = t.getNote() == null ? "" : t.getNote().toLowerCase();

                    if (!note.contains(keyword.toLowerCase())) {
                        continue;
                    }
                }

                result.add(t);

            } catch (Exception ignored) {}
        }

        return result;
    }

    // ================= TOTAL BY CATEGORY =================
    public double getTotalByCategory(
            int userId,
            String category,
            int month,
            int year
    ) {

        double total = 0;

        List<Transaction> transactions = getByUser(userId);

        for (Transaction t : transactions) {

            try {

                if (t.getCategory().equalsIgnoreCase(category)
                        && t.getType().equalsIgnoreCase("Expense")
                        && t.getDate().toLocalDate().getMonthValue() == month
                        && t.getDate().toLocalDate().getYear() == year) {

                    total += t.getAmount();
                }

            } catch (Exception ignored) {}
        }

        return total;
    }
}