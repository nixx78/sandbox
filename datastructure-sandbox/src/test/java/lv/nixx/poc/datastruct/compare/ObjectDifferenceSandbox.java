package lv.nixx.poc.datastruct.compare;

import lv.nixx.poc.datastruct.domain.Transaction;
import org.apache.commons.lang3.builder.DiffBuilder;
import org.apache.commons.lang3.builder.DiffResult;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ObjectDifferenceSandbox {

    @Test
    void findDifferenceTest() {

        Transaction t1 = new Transaction("id1", 10.0, "acc1",
                "USD", "2022-12-06");

        Transaction t2 = new Transaction("id1", 10.1, "acc1",
                "USD", "2022-12-07");

        DiffBuilder<Transaction> db = new DiffBuilder<>(t1, t2, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", t1.getId(), t2.getId())
                .append("amount", t1.getAmount(), t2.getAmount())
                .append("account", t1.getAccount(), t2.getAccount())
                .append("currency", t1.getCurrency(), t2.getCurrency())
                .append("date", t1.getLastUpdateDate(), t2.getLastUpdateDate());

        DiffResult<Transaction> diffResult = db.build();
        System.out.println("Difference [" + diffResult + "]");

        assertEquals(2, diffResult.getNumberOfDiffs());

        diffResult.getDiffs().forEach( System.out::println);
    }

    @Test
    void compareTest() {

        Transaction t1 = new Transaction("id1", 10.0, "acc1",
                "USD", "2022-12-06");

        Transaction t2 = new Transaction("id1", 10.0, "acc1",
                "USD", "2022-12-06");

        Comparator<Transaction> txnComparator = Comparator.comparing(Transaction::getId)
                .thenComparing(Transaction::getAmount)
                .thenComparing(Transaction::getAccount)
                .thenComparing(Transaction::getCurrency)
                .thenComparing(Transaction::getLastUpdateDate);

        assertEquals(0, txnComparator.compare(t1, t2));

        Transaction t3 = new Transaction("id1", 10.1, "acc1",
                "USD", "2022-12-06");

        assertEquals(-1, txnComparator.compare(t1, t3));
    }


}
