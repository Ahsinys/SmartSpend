# SmartSpend

**Student**  
Name: Ahsin Younas  
Roll Number: L1F23BSSE0225  

---

## Project Description
SmartSpend is a Java desktop application for personal finance and budget tracking.

The application stores all data locally using CSV files:
- users.csv
- transactions.csv
- budgets.csv

---

## Features (Planned)
- User Registration and Login System
- Add, Edit, Delete Transactions (Income & Expenses)
- Monthly Budget Management by Category
- Budget Warning System (OK / WARNING / OVER BUDGET)
- Monthly Financial Reports (Income, Expenses, Savings)
- Advanced Search and Filtering System
- Dashboard with Financial Summary

---

## Technologies Used
- Java (JDK 17+)
- Java Swing
- CSV File I/O
- BufferedReader / BufferedWriter
- IntelliJ IDEA
- Git & GitHub

---

# DAY 1
**Status**  
Day 1 Setup Completed  
Day 1 Revised (Project Setup + File System + Model Classes)

**Current Progress**
- Project setup done
- Folder structure created
- CSV file system implemented
- Model classes created
- FileHandler system implemented

---

## Status
Day 2 Completed: File Handling System with CRUD Operations implemented using CSV files

---

## Current Progress
- Project structure is created
- CSV-based file system implemented
- Model classes completed (User, Transaction, Budget)
- FileHandler layer completed (CRUD operations)
- Core backend foundation is ready

---

## Day 2 Summary
Implemented file handling system using CSV files instead of database.
All CRUD operations for User, Transaction, and Budget are completed using FileHandler classes.

---

# DAY 3
## Status
Day 3 Completed: UI + Authentication + Navigation System

## Current Progress
- Login and Registration UI completed (Swing)
- Session management implemented
- AuthService created for login/register logic
- MainFrame created with BorderLayout
- CardLayout navigation system implemented
- Sidebar navigation added (Dashboard, Transactions, Budget, Search, Reports)
- Logout functionality implemented
- Application flow: Login → Main Dashboard → Modules

<img width="320" height="213" alt="image" src="https://github.com/user-attachments/assets/20d8d2b7-23ba-4577-939c-1bf3242f82e5" />

<img width="324" height="214" alt="image" src="https://github.com/user-attachments/assets/485a5e56-35f4-46ca-bcf7-8521a928fc21" />

<img width="737" height="438" alt="image" src="https://github.com/user-attachments/assets/b2c32bce-7b02-46e1-a147-6f42861d5ab7" />


## Day 3 Summary
Implemented full application UI shell with authentication system.
After login, user is redirected to MainFrame which uses CardLayout for switching between modules.

---

# DAY 4
## Status
Day 4 Completed: Transaction Management Module

## Current Progress
- TransactionPanel UI built (form + table layout)
- Add Transaction functionality implemented
- Delete Transaction functionality implemented
- Row selection fills form data
- Transaction ID auto-generation implemented
- CSV integration via TransactionService + FileHandler
- Table auto-refresh after every operation
- Session-based user transactions (user-specific data)

<img width="733" height="437" alt="image" src="https://github.com/user-attachments/assets/49cff8b8-b7e1-47bb-a8e6-b445ae3d7537" />


## Day 4 Summary
Implemented complete Transaction module with full CRUD support (Add + Delete currently active).
Data is stored and managed using CSV files with proper service-layer architecture.

---

## Current Project Status
- Authentication system working
- Main UI navigation complete
- Transaction module fully functional (Add/Delete working)
- CSV persistence fully integrated
- Core architecture (Model → FileHandler → Service → UI) established

## DAY 5
Status

**Day 5 Completed:** Budget Management Module

## Current Progress
BudgetService created with full calculation logic
BudgetPanel UI implemented (form + table layout)
Budget save and update functionality implemented
Budget linked with transaction data for spent calculation
Budget status system implemented (OK / WARNING / OVER BUDGET)
Color coded table added using custom renderer

## Day 5 Summary
Implemented complete budget management system where users can set category-wise limits and track spending with automatic status updates.


<img width="734" height="439" alt="image" src="https://github.com/user-attachments/assets/4eb4cfff-7a79-475f-b446-dd04365b03cb" />


---

## DAY 6
Status

**Day 6 Completed:** Search and Reports Module

## Current Progress
SearchPanel implemented with multi-filter system
Transaction search by category, type, date range, and keyword
Reset and result counter functionality added
ReportService created for financial calculations
ReportPanel UI implemented for monthly reports
Income, expense, and savings calculation added
Category-wise spending breakdown implemented

## Day 6 Summary

Implemented advanced search and reporting system. Users can now filter transactions and view monthly financial summaries with category-wise analysis.


<img width="734" height="438" alt="image" src="https://github.com/user-attachments/assets/bdf0a6a6-13fc-4d7d-8e2d-5d9f247a0802" />


<img width="731" height="439" alt="image" src="https://github.com/user-attachments/assets/0884e33c-4568-4c85-8ac2-44aa2d80bbe1" />
