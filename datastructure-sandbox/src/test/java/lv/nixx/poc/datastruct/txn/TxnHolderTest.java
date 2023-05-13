package lv.nixx.poc.datastruct.txn;

import lv.nixx.poc.datastruct.domain.Transaction;
import lv.nixx.poc.datastruct.domain.TxnHolder;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.assertEquals;

public class TxnHolderTest {

    @Test
    public void onlyLatestTxnShouldBeStored() {

        TxnHolder th = new TxnHolder();
        th.add(new Transaction("id1", 10.10, "ACC1.CHANGED", "USD", "2016-09-01"));
        th.add(new Transaction("id2", 20.12, "ACC2", "USD", "2016-08-01"));
        th.add(new Transaction("id3", 20.12, "ACC3", "USD", "2016-09-03"));
        th.add(new Transaction("id6", 20.12, "ACC3.NEW", "USD", "2016-09-03"));
        th.add(new Transaction("id3", 20.12, "ACC3.CHANGED", "USD", "2016-09-03"));
        th.add(new Transaction("id3", 20.12, "ACC3.CHANGED1", "USD", "2016-08-03"));

        System.out.println("--- Unique values ---");
        final Collection<Transaction> values = th.getValues();
        assertEquals(4, values.size());

        for (Transaction t : values) {
            System.out.println(t);
        }

        System.out.println("--- Duplicated values ---");
        final Collection<Transaction> duplicatedValues = th.getDuplicatedValues();
        assertEquals(2, duplicatedValues.size());
        for (Transaction t : duplicatedValues) {
            System.out.println(t);
        }

    }

}
