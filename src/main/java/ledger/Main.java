package ledger;

import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        Path csvPath = Path.of("ledger.csv");

        Ledger ledger = new Ledger();
        CsvLedgerStore store = new CsvLedgerStore();
        LedgerService service = new LedgerService(ledger, store);

        // Try to load existing data (if file doesn't exist, just start empty)
        try {
            service.load(csvPath);
        } catch (RuntimeException ignored) {
            // start with empty ledger
        }

        LedgerCli.run(service, csvPath);
    }
}
