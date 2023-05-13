package lv.nixx.poc.datastruct.txn.stream.statistic;

import lv.nixx.poc.datastruct.domain.Transaction;
import org.junit.Test;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInRelativeOrder;

public class StatisticByAccountUsingCollectorTest {

    @Test
    public void statisticByAccountSample() {

        Collection<Transaction> txns = List.of(
                new Transaction("id", 10.10, "ACC1", "GBP", "2016-01-03"),
                new Transaction("id", 7.77, "ACC1", "GBP", "2016-03-01"),
                new Transaction("id2", 20.12, "ACC2", "USD", "2016-08-01"),
                new Transaction("id3", 1.25, "ACC2", "EUR", "2016-10-01"),
                new Transaction("id3", 3.75, "ACC2", "EUR", "2016-10-03"),
                new Transaction("id31", 5.8, "ACC2", "USD", "2016-10-02"),
                new Transaction("id4", 40.14, "ACC3", "EUR", "2016-12-01")
        );

        List<String> collect = txns.stream()
                .collect(
                        collectingAndThen(
                                groupingBy(Transaction::getAccount,
                                        mapping(Transaction::getAmount, CollectionStat.collector())
                                ), t -> t.entrySet()
                                        .stream()
                                        .sorted(Map.Entry.comparingByKey())
                                        .map(e -> e.getKey() + ":" + e.getValue().toString())
                                        .collect(toList())
                        )
                );

        collect.forEach(System.out::println);

        assertThat(collect, containsInRelativeOrder(
                        "ACC1:count:2 min:7.77 max:10.1",
                        "ACC2:count:4 min:1.25 max:20.12",
                        "ACC3:count:1 min:40.14 max:40.14"
                )
        );

    }
}
