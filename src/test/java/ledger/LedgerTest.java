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
    void getAll_returnsUnmodifiableList() {
        Ledger ledger = new Ledger();
        ledger.add(new Transaction(new BigDecimal("10.00"), "Salary"));

        List<Transaction> all = ledger.getAll();
        assertEquals(1, all.size());

        assertThrows(UnsupportedOperationException.class, () ->
                all.add(new Transaction(new BigDecimal("1.00"), "Should fail"))
        );
    }

    @Test
    void getBalance_sumsAllAmounts() {
        Ledger ledger = new Ledger();
        ledger.add(new Transaction(new BigDecimal("100.00"), "Income"));
        ledger.add(new Transaction(new BigDecimal("-40.50"), "Groceries"));
        ledger.add(new Transaction(new BigDecimal("-10.00"), "Coffee"));

        assertEquals(new BigDecimal("49.50"), ledger.getBalance());
    }

    @Test
    void getCredits_returnsOnlyPositiveAmounts() {
        Ledger ledger = new Ledger();
        Transaction t1 = new Transaction(new BigDecimal("100.00"), "Income");
        Transaction t2 = new Transaction(new BigDecimal("-5.00"), "Snack");
        Transaction t3 = new Transaction(new BigDecimal("20.00"), "Refund");

        ledger.add(t1);
        ledger.add(t2);
        ledger.add(t3);

        List<Transaction> credits = ledger.getCredits();
        assertEquals(2, credits.size());
        assertTrue(credits.stream().allMatch(t -> t.getAmount().compareTo(BigDecimal.ZERO) > 0));
    }

    @Test
    void getDebits_returnsOnlyNegativeAmounts() {
        Ledger ledger = new Ledger();
        Transaction t1 = new Transaction(new BigDecimal("100.00"), "Income");
        Transaction t2 = new Transaction(new BigDecimal("-5.00"), "Snack");
        Transaction t3 = new Transaction(new BigDecimal("-20.00"), "Bills");

        ledger.add(t1);
        ledger.add(t2);
        ledger.add(t3);

        List<Transaction> debits = ledger.getDebits();
        assertEquals(2, debits.size());
        assertTrue(debits.stream().allMatch(t -> t.getAmount().compareTo(BigDecimal.ZERO) < 0));
    }

    @Test
    void findByDescription_isCaseInsensitive_andFindsMatches() {
        Ledger ledger = new Ledger();
        Transaction t1 = new Transaction(new BigDecimal("10.00"), "Coffee at Nero");
        Transaction t2 = new Transaction(new BigDecimal("25.00"), "Groceries");
        Transaction t3 = new Transaction(new BigDecimal("5.00"), "COFFEE beans");

        ledger.add(t1);
        ledger.add(t2);
        ledger.add(t3);

        List<Transaction> found = ledger.findByDescription("coffee");
        assertEquals(2, found.size());
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

        Transaction t1 = new Transaction(new BigDecimal("1.00"), "Oldest");
        Thread.sleep(2);
        Transaction t2 = new Transaction(new BigDecimal("2.00"), "Middle");
        Thread.sleep(2);
        Transaction t3 = new Transaction(new BigDecimal("3.00"), "Newest");

        ledger.add(t1);
        ledger.add(t2);
        ledger.add(t3);

        List<Transaction> recent2 = ledger.getRecent(2);

        assertEquals(2, recent2.size());
        assertEquals("Newest", recent2.get(0).getDescription());
        assertEquals("Middle", recent2.get(1).getDescription());
    }

    @Test
    void getRecent_negative_throws() {
        Ledger ledger = new Ledger();
        assertThrows(IllegalArgumentException.class, () -> ledger.getRecent(-1));
    }
}