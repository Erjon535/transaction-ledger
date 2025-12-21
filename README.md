# Transaction Ledger

A simple, well-structured Java application that records financial transactions (credits and debits), calculates balances, supports querying, and persists data to a CSV file.

This project demonstrates clean object-oriented design, unit testing, and practical Java tooling using Maven.

---

## Features

- Add transactions (amount + description)
- Automatically assigns timestamps and unique IDs
- View current balance
- List all transactions
- List credits (positive amounts only)
- List debits (negative amounts only)
- Search transactions by description keyword
- View most recent N transactions
- Persist transactions to a CSV file
- Interactive command-line interface (CLI)

---

## Tech Stack

- Java 21+
- Maven (via Maven Wrapper)
- JUnit 5
- CSV-based persistence (no external database)

---

## Project Structure

```text
src/
├─ main/
│  └─ java/
│     └─ ledger/
│        ├─ Transaction.java      # Immutable domain model
│        ├─ Ledger.java           # Core ledger logic
│        ├─ LedgerService.java    # Application/service layer
│        ├─ CsvLedgerStore.java   # CSV persistence
│        ├─ LedgerCli.java        # Command-line interface
│        └─ Main.java             # Application entry point
└─ test/
   └─ java/
      └─ ledger/
         ├─ TransactionTest.java
         └─ LedgerTest.java
```

---

## Getting Started (Windows PowerShell)

### 1. Prerequisites

Verify Java is installed:

```powershell
java -version
```

---

### 2. Run Tests

Run the full test suite using the Maven Wrapper:

```powershell
.\mvnw.cmd test
```

All tests should pass.

---

### 3. Run the Application

Start the interactive CLI:

```powershell
.\mvnw.cmd exec:java
```

This launches the Transaction Ledger CLI and persists data to `ledger.csv` in the project directory.

---

## Example CLI Session

```text
Transaction Ledger CLI
Data file: C:\Users\yourname\transaction-ledger\ledger.csv

1) Add transaction
2) Show balance
3) List all
4) List credits
5) List debits
6) Search by description
7) Show recent (N)
0) Save & Exit
> 1
Amount (e.g. 12.50 or -5.00): 12.50
Description: lunch
Added.

> 2
Balance: 12.50

> 0
Saved. Bye!
```
