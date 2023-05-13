package lv.nixx.poc.datastruct.compare;

import lv.nixx.poc.datastruct.domain.Account;
import lv.nixx.poc.datastruct.domain.AccountType;
import lv.nixx.poc.datastruct.domain.AccountTypeComparator;
import lv.nixx.poc.datastruct.domain.Transaction;
import org.junit.Test;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static lv.nixx.poc.datastruct.domain.AccountType.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

public class AccountSorterSandbox {

    @Test
    public void sortUsingComparable() {

        List<Account> sortedList = createAccounts().stream()
                .sorted() // There we use Comparable implementation inside Account class
                .collect(Collectors.toList());

        System.out.println(sortedList);

        assertThat(sortedList.stream().map(Account::getId).collect(Collectors.toList()),
                contains("AccountID4", "AccountID1", "AccountID3", "AccountID2"));

    }

    @Test
    public void sortAccountsNaturalEnumOrder() {
        List<Account> sortedList = createAccounts().stream()
                .sorted(Comparator.comparing(Account::getType).thenComparingInt(t -> t.getTxn().size())).toList();

        assertThat(sortedList.stream().map(Account::getId).collect(Collectors.toList()),
                contains("AccountID3", "AccountID2", "AccountID1", "AccountID4"));
    }

    @Test
    public void sortAccountsCustomEnumOrder() {

        Comparator<AccountType> accountTypeComparator = AccountTypeComparator.forOrder(DEPOSIT, CURRENT, CARD);
        Comparator<Account> comparator = Comparator.comparing(Account::getType, accountTypeComparator)
                .thenComparingInt(t -> t.getTxn().size());

        List<Account> sortedList = createAccounts().stream()
                .sorted(comparator) // Outside created comparator
                .collect(Collectors.toList());

        System.out.println(sortedList);

        assertThat(sortedList.stream().map(Account::getId).collect(Collectors.toList()),
                contains("AccountID4", "AccountID1", "AccountID3", "AccountID2"));
    }

    private Collection<Account> createAccounts() {
        Account acc1 = new Account("AccountID1", List.of(
                new Transaction("_1txn1", 40.00, "AccountID1", "USD")
        ), CURRENT);

        Account acc2 = new Account("AccountID2", List.of(
                new Transaction("_2txn4", 5.0, "AccountID2", "USD"),
                new Transaction("2txn2", 10.0, "AccountID2", "USD"),
                new Transaction("_2txn4", 5.0, "AccountID2", "USD")
        ), CARD);

        Account acc3 = new Account("AccountID3", List.of(
                new Transaction("_3txn4", 5.0, "AccountID2", "USD")
        ), CARD);

        Account acc4 = new Account("AccountID4", List.of(
                new Transaction("_4txn1", 40.00, "AccountID4", "USD"),
                new Transaction("_4txn2", 10.00, "AccountID4", "USD"),
                new Transaction("_4txn4", 5.00, "AccountID4", "USD")
        ), DEPOSIT);

        return List.of(acc1, acc2, acc3, acc4);
    }

}
