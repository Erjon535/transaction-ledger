package ledger;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.List;

/**
 * Application/service layer that coordinates Ledger + storage.
 */
public class LedgerService {

    private final Ledger ledger;
    private final CsvLedgerStore store;

    public LedgerService(Ledger ledger, CsvLedgerStore store) {
        if (ledger == null) throw new IllegalArgumentException("ledger cannot be null");
        if (store == null) throw new IllegalArgumentException("store cannot be null");
        this.ledger = ledger;
        this.store = store;
    }

    // ---- Commands ----

    public void add(BigDecimal amount, String description) {
        ledger.add(amount, description);
    }

    public void save(Path path) {
        try {
            store.save(path, ledger.getAll());
        } catch (IOException e) {
            throw new RuntimeException("Failed to save ledger to: " + path, e);
        }
    }

    public void load(Path path) {
        try {
            List<Transaction> loaded = store.load(path);
            ledger.replaceAll(loaded);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load ledger from: " + path, e);
        }
    }

    // ---- Queries ----

    public List<Transaction> getAll() { return ledger.getAll(); }

    public List<Transaction> getCredits() { return ledger.getCredits(); }

    public List<Transaction> getDebits() { return ledger.getDebits(); }

    public List<Transaction> findByDescription(String keyword) { return ledger.findByDescription(keyword); }

    public List<Transaction> getRecent(int n) { return ledger.getRecent(n); }

    public BigDecimal getBalance() { return ledger.getBalance(); }
}