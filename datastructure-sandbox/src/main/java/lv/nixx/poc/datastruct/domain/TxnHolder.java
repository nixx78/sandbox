package lv.nixx.poc.datastruct.domain;

import java.util.*;

// Содержит только последнюю версию транзакции, версия определяется по LastUpdateDate
public class TxnHolder {

	private final Map<String, Transaction> uniqueRecords = new HashMap<>();
	private final Set<Transaction> duplicatedRecords = new HashSet<>();

	public void add(Transaction newTxn) {
		final String id = newTxn.getId();
		if (uniqueRecords.containsKey(id)) {
			Transaction existing = uniqueRecords.get(id);
			Transaction duplicated = null;
			if (existing.compareTo(newTxn) <= 0) {
				uniqueRecords.put(id, newTxn);
				duplicated = existing;
			} else {
				duplicated = newTxn;
			}
			duplicatedRecords.add(duplicated);
		} else {
			uniqueRecords.put(id, newTxn);
		}
	}
	
	public Collection<Transaction> getValues() {
		return uniqueRecords.values();
	}
	
	public Collection<Transaction> getDuplicatedValues() {
		return duplicatedRecords;
	}
	
	public boolean containsKey(String id) {
		return uniqueRecords.containsKey(id);
	}
	
	public Transaction get(String id) {
		return uniqueRecords.get(id);
	}
	
	public Collection<String> getKeys() {
		return uniqueRecords.keySet();
	}
	
	public int size() {
		return uniqueRecords.size();
	}

}