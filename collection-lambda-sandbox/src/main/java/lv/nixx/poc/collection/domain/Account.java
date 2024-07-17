package lv.nixx.poc.collection.domain;

import lombok.Getter;
import lombok.ToString;

import java.util.Comparator;
import java.util.List;

import static lv.nixx.poc.collection.domain.AccountType.*;

@Getter
@ToString
public class Account implements Comparable<Account> {

    private static final Comparator<AccountType> accountTypeComparator = AccountTypeComparator.forOrder(DEPOSIT, CURRENT, CARD);

    private final String id;
    private final List<Transaction> txn;
    private final AccountType type;

    public Account(String id, AccountType type) {
        this(id, null, type);
    }

    public Account(String id, List<Transaction> txn) {
        this(id, txn, null);
    }

    public Account(String id, List<Transaction> txn, AccountType type) {
        this.id = id;
        this.txn = txn;
        this.type = type;
    }

    @Override
    public int compareTo(Account account) {
        Comparator<Account> comparator = Comparator.comparing(Account::getType, accountTypeComparator)
                .thenComparingInt(t -> t.getTxn().size());

        return comparator.compare(this, account);
    }
}
