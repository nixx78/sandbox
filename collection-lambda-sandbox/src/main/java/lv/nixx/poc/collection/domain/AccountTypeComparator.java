package lv.nixx.poc.collection.domain;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class AccountTypeComparator implements Comparator<AccountType> {

    private final Map<AccountType, Integer> pos;

    public static AccountTypeComparator forOrder(AccountType... expectedOrder) {
        return new AccountTypeComparator(expectedOrder);
    }

    private AccountTypeComparator(AccountType... expectedOrder) {
        this.pos = new HashMap<>();
        for (int i = 0; i < expectedOrder.length; i++) {
            pos.put(expectedOrder[i], i);
        }
    }

    @Override
    public int compare(AccountType o1, AccountType o2) {
        Integer p1 = pos.getOrDefault(o1, -1);
        Integer p2 = pos.getOrDefault(o2, -1);
        return p1 - p2;
    }

}
