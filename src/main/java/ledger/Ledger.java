package ledger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Ledger that holds and manages financial transactions.
 */
public class Ledger {

    private final List<Transaction> transactions = new ArrayList<>();

    /**
     * Adds a transaction to the ledger.
     */
    public void add(Transaction transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction cannot be null");
        }
        transactions.add(transaction);
    }

    /**
     * Returns an unmodifiable view of all transactions.
     */
    public List<Transaction> getAll() {
        return Collections.unmodifiableList(transactions);
    }

    /**
     * Calculates the current balance.
     */
    public BigDecimal getBalance() {
        BigDecimal balance = BigDecimal.ZERO;
        for (Transaction t : transactions) {
            balance = balance.add(t.getAmount());
        }
        return balance;
    }
}
