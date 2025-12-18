package ledger;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LedgerTest {

    @Test
    void add_nullTransaction_throws() {
        Ledger ledger = new Ledger();
        assertThrows(IllegalArgumentException.class, () -> ledger.add(null));
    }

    @Test
    void getAll_isUnmodifiable() {
        Ledger ledger = new Ledger();
        ledger.add(new Transaction(new BigDecimal("10.00"), "Salary"));

        List<Transaction> all = ledger.getAll();
        assertThrows(UnsupportedOperationException.class,
                () -> all.add(new Transaction(new BigDecimal("1.00"), "Should fail")));
    }

    @Test
    void getBalance_sumsCreditsAndDebits() {
        Ledger ledger = new Ledger();
        ledger.add(new Transaction(new BigDecimal("100.00"), "Deposit"));
        ledger.add(new Transaction(new BigDecimal("-40.50"), "Groceries"));
        ledger.add(new Transaction(new BigDecimal("-10.00"), "Coffee"));

        assertEquals(new BigDecimal("49.50"), ledger.getBalance());
    }

    @Test
    void getCredits_returnsOnlyPositiveAmounts() {
        Ledger ledger = new Ledger();
        ledger.add(new Transaction(new BigDecimal("100.00"), "Deposit"));
        ledger.add(new Transaction(new BigDecimal("-20.00"), "Food"));

        List<Transaction> credits = ledger.getCredits();
        assertEquals(1, credits.size());
        assertTrue(credits.get(0).getAmount().compareTo(BigDecimal.ZERO) > 0);
    }

    @Test
    void getDebits_returnsOnlyNegativeAmounts() {
        Ledger ledger = new Ledger();
        ledger.add(new Transaction(new BigDecimal("100.00"), "Deposit"));
        ledger.add(new Transaction(new BigDecimal("-20.00"), "Food"));

        List<Transaction> debits = ledger.getDebits();
        assertEquals(1, debits.size());
        assertTrue(debits.get(0).getAmount().compareTo(BigDecimal.ZERO) < 0);
    }

    @Test
    void findByDescription_isCaseInsensitive_andFindsMatches() {
        Ledger ledger = new Ledger();
        ledger.add(new Transaction(new BigDecimal("10.00"), "Coffee shop"));
        ledger.add(new Transaction(new BigDecimal("-5.00"), "coffee beans"));
        ledger.add(new Transaction(new BigDecimal("-2.00"), "Bus ticket"));

        List<Transaction> results = ledger.findByDescription("COFFEE");
        assertEquals(2, results.size());
        assertTrue(results.stream().allMatch(t ->
                t.getDescription().toLowerCase().contains("coffee")));
    }

    @Test
    void findByDescription_blank_throws() {
        Ledger ledger = new Ledger();
        assertThrows(IllegalArgumentException.class, () -> ledger.findByDescription(" "));
        assertThrows(IllegalArgumentException.class, () -> ledger.findByDescription(null));
    }

    @Test
    void getRecent_returnsMostRecentFirst_andLimitsToN() throws InterruptedException {
        Ledger ledger = new Ledger();

        Transaction t1 = new Transaction(new BigDecimal("1.00"), "First");
        Thread.sleep(5);
        Transaction t2 = new Transaction(new BigDecimal("2.00"), "Second");
        Thread.sleep(5);
        Transaction t3 = new Transaction(new BigDecimal("3.00"), "Third");

        ledger.add(t1);
        ledger.add(t2);
        ledger.add(t3);

        List<Transaction> recent2 = ledger.getRecent(2);

        assertEquals(2, recent2.size());
        assertEquals(t3.getId(), recent2.get(0).getId());
        assertEquals(t2.getId(), recent2.get(1).getId());
    }

    @Test
    void getRecent_negative_throws() {
        Ledger ledger = new Ledger();
        assertThrows(IllegalArgumentException.class, () -> ledger.getRecent(-1));
    }
}
