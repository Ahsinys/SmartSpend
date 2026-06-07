package com.smartspend.filehandler;

import com.smartspend.util.FileManager;

import java.util.List;

public abstract class BaseFileHandler {

    protected String filePath;

    public BaseFileHandler(String filePath) {
        this.filePath = filePath;
    }

    // Must be implemented by child classes
    public abstract void add(Object obj);
    public abstract void update(Object obj);
    public abstract void delete(int id);

    // Shared methods for all handlers
    protected List<String[]> readAll() {
        return FileManager.readLines(filePath);
    }

    protected void writeAll(List<String[]> data) {
        FileManager.writeLines(filePath, data);
    }
}