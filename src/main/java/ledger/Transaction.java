package ledger;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents a single immutable financial transaction.
 */
public class Transaction {

    private final UUID id;
    private final BigDecimal amount;
    private final String description;
    private final LocalDateTime timestamp;

    // Normal "new transaction" constructor
    public Transaction(BigDecimal amount, String description) {
        if (amount == null) throw new IllegalArgumentException("Amount cannot be null");
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description cannot be empty");
        }

        this.id = UUID.randomUUID();
        this.amount = amount;
        this.description = description;
        this.timestamp = LocalDateTime.now();
    }

    // Private constructor used when loading from storage
    private Transaction(UUID id, BigDecimal amount, String description, LocalDateTime timestamp) {
        if (id == null) throw new IllegalArgumentException("Id cannot be null");
        if (amount == null) throw new IllegalArgumentException("Amount cannot be null");
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description cannot be empty");
        }
        if (timestamp == null) throw new IllegalArgumentException("Timestamp cannot be null");

        this.id = id;
        this.amount = amount;
        this.description = description;
        this.timestamp = timestamp;
    }

    // Factory for restoring an existing transaction from CSV / disk
    public static Transaction restore(UUID id, BigDecimal amount, String description, LocalDateTime timestamp) {
        return new Transaction(id, amount, description, timestamp);
    }

    public UUID getId() { return id; }
    public BigDecimal getAmount() { return amount; }
    public String getDescription() { return description; }
    public LocalDateTime getTimestamp() { return timestamp; }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
