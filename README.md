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
src/
├─ main/
│ └─ java/
│ └─ ledger/
│ ├─ Transaction.java # Immutable domain model
│ ├─ Ledger.java # Core ledger logic
│ ├─ LedgerService.java # Application/service layer
│ ├─ CsvLedgerStore.java # CSV persistence
│ ├─ LedgerCli.java # Command-line interface
│ └─ Main.java # Application entry point
└─ test/
└─ java/
└─ ledger/
├─ TransactionTest.java
└─ LedgerTest.java


---

## Getting Started (Windows PowerShell)

### 1. Prerequisites

Verify Java is installed:

```powershell
java -version


