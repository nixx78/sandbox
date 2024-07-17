package lv.nixx.poc.collection.txn;

import lv.nixx.poc.collection.domain.Account;
import lv.nixx.poc.collection.domain.AccountType;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class CustomerSortTest {

	@Test
	public void customAccountSort() {

		final List<Account> sortedAccounts = Stream.of(new Account("X", (AccountType) null),
				new Account("D", (AccountType) null),
				new Account("C", (AccountType) null),
				new Account("A", (AccountType) null),
				new Account("B", (AccountType) null),
				new Account("TOP", (AccountType) null))
		.sorted((t1, t2)->t1.getId().equals("TOP") ? -1: t1.getId().compareTo(t2.getId()))
		.collect(Collectors.toList());
		
		sortedAccounts.forEach(System.out::println);
		
		final List<String> ids = sortedAccounts
				.stream()
				.map(Account::getId)
				.collect(Collectors.toList());
		
		assertEquals(Arrays.asList("TOP","A","B","C","D","X"), ids);
		

	}

}
