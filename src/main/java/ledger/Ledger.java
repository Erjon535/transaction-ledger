package ledger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<Transaction> getCredits() {
        return transactions.stream()
                .filter(t -> t.getAmount().compareTo(BigDecimal.ZERO) > 0)
                .collect(Collectors.toUnmodifiableList());
    }

    public List<Transaction> getDebits() {
        return transactions.stream()
                .filter(t -> t.getAmount().compareTo(BigDecimal.ZERO) < 0)
                .collect(Collectors.toUnmodifiableList());
    }

    public List<Transaction> findByDescription(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            throw new IllegalArgumentException("Keyword cannot be empty");
        }
        String k = keyword.toLowerCase();
        return transactions.stream()
                .filter(t -> t.getDescription().toLowerCase().contains(k))
                .collect(Collectors.toUnmodifiableList());
    }

    public List<Transaction> getRecent(int n) {
        if (n < 0) throw new IllegalArgumentException("n must be >= 0");
        return transactions.stream()
                .sorted(Comparator.comparing(Transaction::getTimestamp).reversed())
                .limit(n)
                .collect(Collectors.toUnmodifiableList());
    }
}
