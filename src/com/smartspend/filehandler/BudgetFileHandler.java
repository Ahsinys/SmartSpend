package com.smartspend.filehandler;

import com.smartspend.model.Budget;

import java.util.ArrayList;
import java.util.List;

public class BudgetFileHandler extends BaseFileHandler {

    public BudgetFileHandler(String filePath) {
        super(filePath);
    }

    @Override
    public void add(Object obj) {
        Budget b = (Budget) obj;

        List<String[]> data = readAll();

        data.add(new String[]{
                String.valueOf(b.getId()),
                String.valueOf(b.getUserId()),
                b.getCategory(),
                String.valueOf(b.getLimitAmount()),
                String.valueOf(b.getMonth()),
                String.valueOf(b.getYear())
        });

        writeAll(data);
    }

    @Override
    public void update(Object obj) {
        Budget b = (Budget) obj;

        List<String[]> data = readAll();

        for (String[] row : data) {
            try {
                if (Integer.parseInt(row[0]) == b.getId()) {
                    row[1] = String.valueOf(b.getUserId());
                    row[2] = b.getCategory();
                    row[3] = String.valueOf(b.getLimitAmount());
                    row[4] = String.valueOf(b.getMonth());
                    row[5] = String.valueOf(b.getYear());
                }
            } catch (Exception e) {
                // skip corrupted row safely
                continue;
            }
        }

        writeAll(data);
    }

    @Override
    public void delete(int id) {
        List<String[]> data = readAll();
        List<String[]> updated = new ArrayList<>();

        for (String[] row : data) {
            try {
                if (Integer.parseInt(row[0]) != id) {
                    updated.add(row);
                }
            } catch (Exception e) {
                // skip bad row
            }
        }

        writeAll(updated);
    }

    // =====================================
    // GET ALL BUDGETS FOR USER + MONTH/YEAR
    // =====================================
    public List<Budget> getByUser(int userId, int month, int year) {

        List<String[]> data = readAll();
        List<Budget> result = new ArrayList<>();

        for (String[] row : data) {

            try {
                int rowUserId = Integer.parseInt(row[1]);
                int rowMonth = Integer.parseInt(row[4]);
                int rowYear = Integer.parseInt(row[5]);

                if (rowUserId == userId && rowMonth == month && rowYear == year) {

                    Budget b = new Budget(
                            Integer.parseInt(row[0]),
                            rowUserId,
                            row[2],
                            Double.parseDouble(row[3]),
                            rowMonth,
                            rowYear
                    );

                    result.add(b);
                }

            } catch (Exception e) {
                // ignore corrupted rows safely
            }
        }

        return result;
    }

    // =====================================
    // GET BUDGET BY CATEGORY
    // =====================================
    public Budget getByCategory(int userId, String category, int month, int year) {

        List<String[]> data = readAll();

        for (String[] row : data) {

            try {
                if (Integer.parseInt(row[1]) == userId
                        && row[2].equalsIgnoreCase(category)
                        && Integer.parseInt(row[4]) == month
                        && Integer.parseInt(row[5]) == year) {

                    return new Budget(
                            Integer.parseInt(row[0]),
                            userId,
                            row[2],
                            Double.parseDouble(row[3]),
                            month,
                            year
                    );
                }

            } catch (Exception e) {
                continue;
            }
        }

        return null;
    }
    //////////////////////////////////////////////////////////////////////////
    public List<String[]> getAllRows() {
        return super.readAll();
    }

    public List<Budget> getAllBudgets() {

        List<String[]> data = readAll();
        List<Budget> result = new ArrayList<>();

        for (String[] row : data) {
            try {
                result.add(new Budget(
                        Integer.parseInt(row[0]),
                        Integer.parseInt(row[1]),
                        row[2],
                        Double.parseDouble(row[3]),
                        Integer.parseInt(row[4]),
                        Integer.parseInt(row[5])
                ));
            } catch (Exception ignored) {}
        }

        return result;
    }
}