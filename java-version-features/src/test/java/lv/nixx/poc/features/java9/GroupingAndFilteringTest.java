package lv.nixx.poc.features.java9;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

class GroupingAndFilteringTest {

    //TODO https://habr.com/ru/post/554128/

    @Test
    void groupAndFilterTest() {

        Collection<Account> accounts = List.of(
                new Account("usr1", "USD", 100.00, List.of(
                        new Txn("11", 110.11),
                        new Txn("12", 120.12)
                )),
                new Account("usr1", "EUR", 200.00, List.of(
                        new Txn("21", 210.11),
                        new Txn("22", 220.12)
                )),
                new Account("usr2", "USD", 300.00, List.of(
                        new Txn("31", 310.11),
                        new Txn("32", 320.12)
                )),
                new Account("usr3", "USD", 400.00, List.of(
                        new Txn("41", 410.11),
                        new Txn("42", 420.12)
                )),
                new Account("usr3", "USD", 10.00, null),
                new Account("usr4", "USD", 10.00, null)
        );

        accounts.stream()
                .collect(
                        groupingBy(Account::user, TreeMap::new,
                                filtering(c -> c.balance() > 50, Collectors.toList())
                        )
                ).forEach((k, v) -> System.out.println(k + ": " + v));

        // for 'usr4' we expect empty value

        System.out.println("------------------------------------------");

        accounts.stream()
                .collect(
                        groupingBy(Account::user, TreeMap::new,
                                mapping(a -> a.user() + ":" + a.currency + ":" + a.balance, Collectors.toList())
                        )
                ).forEach((k, v) -> System.out.println(k + ": " + v));

        System.out.println("------------------------------------------");

        Map<String, TreeMap<String, List<Txn>>> currencyUserMap = accounts.stream()
                .collect(
                        groupingBy(Account::currency,
                                groupingBy(Account::user, TreeMap::new,
                                        flatMapping(a -> a.txn == null ? Stream.of() : a.txn.stream(), toList()))
                        )
                );

        currencyUserMap.forEach( (a, u) -> {
                        System.out.println(a);
                        u.forEach((key, value) -> System.out.println("\t\t" + key + ": \n\t\t\t" + value));

                }

        );

    }

    record Account(String user, String currency, double balance, Collection<Txn> txn) {
    }

    record Txn(String id, double amount) {
    }

}
