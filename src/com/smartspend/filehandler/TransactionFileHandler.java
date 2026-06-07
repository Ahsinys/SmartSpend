package com.smartspend.filehandler;

import com.smartspend.model.Transaction;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class TransactionFileHandler extends BaseFileHandler {

    public TransactionFileHandler(String filePath) {
        super(filePath);
    }

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

    @Override
    public void update(Object obj) {
        Transaction t = (Transaction) obj;

        List<String[]> data = readAll();

        for (String[] row : data) {
            if (Integer.parseInt(row[0]) == t.getId()) {
                row[1] = String.valueOf(t.getUserId());
                row[2] = t.getType();
                row[3] = t.getCategory();
                row[4] = String.valueOf(t.getAmount());
                row[5] = t.getNote();
                row[6] = t.getDate() != null ? t.getDate().toString() : "";
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

    // =========================
    // GET ALL TRANSACTIONS USER
    // =========================
    public List<Transaction> getByUser(int userId) {

        List<String[]> data = readAll();
        List<Transaction> result = new ArrayList<>();

        for (String[] row : data) {

            if (Integer.parseInt(row[1]) == userId) {

                Date date;

                try {
                    if (row.length > 6 && row[6] != null && !row[6].isEmpty()) {
                        date = Date.valueOf(row[6].trim());
                    } else {
                        date = new Date(System.currentTimeMillis());
                    }
                } catch (Exception e) {
                    date = new Date(System.currentTimeMillis());
                }

                Transaction t = new Transaction(
                        Integer.parseInt(row[0]),
                        Integer.parseInt(row[1]),
                        row[2],
                        row[3],
                        Double.parseDouble(row[4]),
                        row[5],
                        date
                );

                result.add(t);
            }
        }

        return result;
    }
}