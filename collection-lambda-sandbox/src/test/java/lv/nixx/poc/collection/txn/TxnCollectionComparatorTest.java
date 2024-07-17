package lv.nixx.poc.collection.txn;

import lv.nixx.poc.collection.domain.Transaction;
import lv.nixx.poc.collection.domain.TxnCollectionComparator;
import lv.nixx.poc.collection.domain.TxnHolder;
import org.junit.Ignore;
import org.junit.Test;

import java.text.ParseException;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

public class TxnCollectionComparatorTest {

    @Test
    @Ignore
    public void performanceTest() {

        final int updatedCount = 2000000;
        final int newCount = 100000;
        final int deletedCount = 30000;

        TxnHolder existingTxn = new TxnHolder();
        TxnHolder newTxn = new TxnHolder();

        long stime = System.currentTimeMillis();

        for (int i = 0; i < updatedCount; i++) {
            existingTxn.add(new Transaction("id" + i, 10.10, "ACC1", "USD", "2016-09-01"));
            newTxn.add(new Transaction("id" + i, 10.10, "ACC2", "USD", "2016-09-01"));
        }

        for (int i = 0; i < newCount; i++) {
            newTxn.add(new Transaction("nid" + i, 10.10, "ACC2", "USD", "2016-09-01"));
        }

        for (int i = 0; i < deletedCount; i++) {
            existingTxn.add(new Transaction("did" + i, 10.10, "ACC2", "USD", "2016-09-01"));
        }

        System.out.println("Existing txn element count: " + existingTxn.size());
        System.out.println("New txn element count: " + newTxn.size());
        System.out.println("Test data creation time: " + (System.currentTimeMillis() - stime));

        stime = System.currentTimeMillis();

        TxnCollectionComparator comparator = new TxnCollectionComparator();
        comparator.compareCollections(existingTxn, newTxn);

        System.out.println("Comparator processing time: " + (System.currentTimeMillis() - stime));

        assertEquals(updatedCount, comparator.getUpdatedRecords().size());
        assertEquals(newCount, comparator.getNewRecords().size());
        assertEquals(deletedCount, comparator.getDeletedRecordsKeys().size());
    }


    @Test
    public void getChangedRecords() throws ParseException {
        TxnHolder existingTxn = new TxnHolder();
        existingTxn.add(new Transaction("id1", 10.10, "ACC1", "USD", "2016-09-01"));
        existingTxn.add(new Transaction("id2", 20.12, "ACC2", "USD", "2016-09-01"));
        existingTxn.add(new Transaction("id4", 20.12, "ACC2.DELETED", "USD", "2016-09-01"));

        TxnHolder newTxn = new TxnHolder();
        newTxn.add(new Transaction("id1", 10.10, "ACC1.CHANGED", "USD", "2016-09-01"));
        newTxn.add(new Transaction("id2", 20.12, "ACC2", "USD", "2016-08-01"));
        newTxn.add(new Transaction("id3", 20.12, "ACC3", "USD", "2016-09-03"));
        newTxn.add(new Transaction("id6", 20.12, "ACC3.NEW", "USD", "2016-09-03"));

        TxnCollectionComparator comparator = new TxnCollectionComparator();
        comparator.compareCollections(existingTxn, newTxn);


        System.out.println(" -- New --");
        final Collection<Transaction> newRecords = comparator.getNewRecords();
        assertEquals(2, newRecords.size());
        for (Transaction t : newRecords) {
            System.out.println(t);
        }

        System.out.println(" -- Updated --");
        final Collection<Transaction> updatedRecords = comparator.getUpdatedRecords();
        for (Transaction t : updatedRecords) {
            System.out.println(t);
        }
        assertEquals(1, updatedRecords.size());

        System.out.println(" -- Deleted --");
        final Collection<String> deletedRecordsKeys = comparator.getDeletedRecordsKeys();
        for (String t : deletedRecordsKeys) {
            System.out.println(t);
        }

        assertEquals(1, deletedRecordsKeys.size());

    }
}
