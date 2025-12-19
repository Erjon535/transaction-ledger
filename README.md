# Transaction Ledger

A small Java application that records financial transactions (credits/debits), calculates balance, supports basic querying (search/recent), and persists data to a CSV file.

## Features

- Add transactions (amount + description)
- View current balance
- List all transactions
- List credits only (amount > 0)
- List debits only (amount < 0)
- Search transactions by description keyword
- Show most recent N transactions
- Save/load transactions to/from a CSV file (CSV storage)

## Tech Stack

- Java (JDK 21+)
- Maven Wrapper (Windows: `mvnw.cmd`, macOS/Linux: `./mvnw`)
- JUnit (unit tests)

## Project Structure

- `src/main/java/ledger` — application code
- `src/test/java/ledger` — unit tests

## Getting Started (Windows PowerShell)

### 1) Prerequisites

Check Java is installed:

```powershell
java -version
@
