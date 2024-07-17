package lv.nixx.poc.collection.domain;

import java.util.Collection;
import java.util.HashSet;

public class TxnCollectionComparator {

	final private Collection<Transaction> newRecords = new HashSet<>();
	final private Collection<Transaction> updatedRecords = new HashSet<>();
	final private Collection<String> deletedRecordKeys = new HashSet<>();

	public void compareCollections(TxnHolder existingTxn, TxnHolder newTxn) {
		
		final Collection<Transaction> existingValues = existingTxn.getValues();
		final Collection<Transaction> newValues = newTxn.getValues();

		// для удаленных записей играем только с ключами
		deletedRecordKeys.addAll(existingTxn.getKeys());
		deletedRecordKeys.removeAll(newTxn.getKeys());

		// мы получим измененные + новые
		Collection<Transaction> changedRecords = new HashSet<>(newValues);
		changedRecords.removeAll(existingValues);

		for (Transaction t : changedRecords) {
			String id = t.getId();
			if (existingTxn.containsKey(id)) {
				Transaction existing = existingTxn.get(id);
				if (t.compareTo(existing)>=0) {
					updatedRecords.add(t);
				}
			} else {
				newRecords.add(t);
			}
		}
	}

	public Collection<Transaction> getNewRecords() {
		return newRecords;
	}

	public Collection<Transaction> getUpdatedRecords() {
		return updatedRecords;
	}

	public Collection<String> getDeletedRecordsKeys() {
		return deletedRecordKeys;
	}

}
