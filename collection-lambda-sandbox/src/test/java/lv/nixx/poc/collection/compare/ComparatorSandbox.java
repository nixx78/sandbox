package lv.nixx.poc.collection.compare;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lv.nixx.poc.collection.domain.Transaction;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


class ComparatorSandbox {

    private static final Comparator<BigDecimal> nullSafeBigDecimalComparator = Comparator.nullsFirst(BigDecimal::compareTo);
    private static final Comparator<String> nullSafeStringComparator = Comparator.nullsFirst(String::compareTo);

    @Test
    void compareTest() {

        assertEquals(0, nullSafeBigDecimalComparator.compare(null, null));
        assertEquals(0, nullSafeBigDecimalComparator.compare(BigDecimal.valueOf(10.00), BigDecimal.valueOf(10.00)));
        assertEquals(-1, nullSafeBigDecimalComparator.compare(null, BigDecimal.valueOf(10.00)));
        assertEquals(1, nullSafeBigDecimalComparator.compare(BigDecimal.valueOf(10.00), null));
        assertEquals(0, nullSafeBigDecimalComparator.compare(BigDecimal.valueOf(10), BigDecimal.valueOf(10)));
    }

    @Test
    void compareTxn() {
        Transaction t1 = new Transaction("id1", 10.10, "ACC1", null);
        Transaction t2 = new Transaction("id1", 10.10, "ACC1", null);

        Comparator<Transaction> c = Comparator
                .comparing(Transaction::getId)
                .thenComparing(Transaction::getAmount, nullSafeBigDecimalComparator)
                .thenComparing(Transaction::getAccount, nullSafeStringComparator)
                .thenComparing(Transaction::getCurrency, nullSafeStringComparator);

        assertEquals(0, c.compare(t1, t2));
    }

    @Test
    void sortBigDecimalNullsFirst() {

        List<BigDecimal> sortedList = Stream.of(
                        new Data(BigDecimal.valueOf(10)),
                        new Data(BigDecimal.valueOf(1)),
                        new Data(BigDecimal.valueOf(100)),
                        new Data(null)
                )
                .sorted(Comparator.comparing(Data::getValue, Comparator.nullsFirst(Comparator.naturalOrder())))
                .map(Data::getValue)
                .toList();

        assertThat(sortedList).containsExactly(null, BigDecimal.valueOf(1), BigDecimal.valueOf(10), BigDecimal.valueOf(100));
    }

    @AllArgsConstructor
    @Getter
    static class Data {
        BigDecimal value;
    }

}
