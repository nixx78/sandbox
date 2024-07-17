package lv.nixx.poc.collection.compare;

import lv.nixx.poc.collection.domain.Transaction;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Comparator;

import static org.junit.Assert.assertEquals;

public class ComparatorSandbox {

    private static final Comparator<BigDecimal> nullSafeBigDecimalComparator = Comparator.nullsFirst(BigDecimal::compareTo);
    private static final Comparator<String> nullSafeStringComparator = Comparator.nullsFirst(String::compareTo);

    @Test
    public void compareTest() {

        assertEquals(0, nullSafeBigDecimalComparator.compare(null, null));
        assertEquals(0, nullSafeBigDecimalComparator.compare(BigDecimal.valueOf(10.00), BigDecimal.valueOf(10.00)));
        assertEquals(-1, nullSafeBigDecimalComparator.compare(null, BigDecimal.valueOf(10.00)));
        assertEquals(1, nullSafeBigDecimalComparator.compare(BigDecimal.valueOf(10.00), null));
        assertEquals(0, nullSafeBigDecimalComparator.compare(BigDecimal.valueOf(10), BigDecimal.valueOf(10)));
    }

    @Test
    public void compareTxn() {
        Transaction t1 = new Transaction("id1", 10.10, "ACC1", null);
        Transaction t2 = new Transaction("id1", 10.10, "ACC1", null);

        Comparator<Transaction> c = Comparator
                .comparing(Transaction::getId)
                .thenComparing(Transaction::getAmount, nullSafeBigDecimalComparator)
                .thenComparing(Transaction::getAccount, nullSafeStringComparator)
                .thenComparing(Transaction::getCurrency, nullSafeStringComparator);

        assertEquals(0, c.compare(t1, t2));
    }

}
