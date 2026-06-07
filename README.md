# SmartSpend

## Student
Name: Ahsin Younas
Roll Number: L1F23BSSE0225


## Project Description
SmartSpend is a Java desktop application for personal finance and budget tracking.

The application stores all data locally using CSV files:
- users.csv
- transactions.csv
- budgets.csv


## Features (Planned)
- User Registration and Login System
- Add, Edit, Delete Transactions (Income & Expenses)
- Monthly Budget Management by Category
- Budget Warning System (OK / WARNING / OVER BUDGET)
- Monthly Financial Reports (Income, Expenses, Savings)
- Advanced Search and Filtering System
- Dashboard with Financial Summary


**Technologies Used**
- Java (JDK 17+)
- Java Swing
- CSV File I/O
- BufferedReader / BufferedWriter
- IntelliJ IDEA
- Git & GitHub

## DAY 1

**Status**
Day 1 Setup Completed 
Day 1 Revised (Project Setup + File System + Model Classes)

**Current Progress**
Day 1 Completed:
- Project setup done
- Folder structure created
- CSV file system implemented
- Model classes created
- FileHandler system implemented

## DAY 2
**Project Structure**

SmartSpend
│
├── data
│   ├── users.csv
│   ├── transactions.csv
│   └── budgets.csv
│
├── src
│   └── com.smartspend
│       ├── model
│       ├── filehandler
│       ├── service
│       ├── ui
│       └── util
│
└── README.md


**How to Run**

1. Open project in IntelliJ IDEA
2. Ensure JDK 17+ is installed
3. Run Main class (SmartSpend application entry point)
4. CSV files will be created automatically in /data folder

**Status**
Day 1 Completed: Project Setup, Folder Structure, CSV System, Model Classes
Day 2 Completed: File Handling System with CRUD Operations implemented using CSV files

**Current Progress**
- Project structure is created
- CSV-based file system implemented
- Model classes completed (User, Transaction, Budget)
- FileHandler layer completed (CRUD operations)
- Core backend foundation is ready

**Day 2 Summary**
Implemented file handling system using CSV files instead of database.
All CRUD operations for User, Transaction, and Budget are completed using FileHandler classes.
