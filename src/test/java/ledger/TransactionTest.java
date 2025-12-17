package ledger;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {

    @Test
    void constructorSetsFields() {
        Transaction t = new Transaction(new BigDecimal("12.50"), "Coffee");
        assertNotNull(t.getId());
        assertEquals(new BigDecimal("12.50"), t.getAmount());
        assertEquals("Coffee", t.getDescription());
        assertNotNull(t.getTimestamp());
    }

    @Test
    void rejectsBlankDescription() {
        assertThrows(IllegalArgumentException.class,
                () -> new Transaction(new BigDecimal("1.00"), "  "));
    }

    @Test
    void rejectsNullAmount() {
        assertThrows(IllegalArgumentException.class,
                () -> new Transaction(null, "Anything"));
    }
}
