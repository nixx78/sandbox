package lv.nixx.poc.collection.txn.stream.statistic;

import lombok.Getter;
import lv.nixx.poc.collection.domain.Transaction;
import org.junit.Test;

import java.text.ParseException;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StatisticByAccountUsingTeeingTest {

    // This feature is available from Java 12

    @Test
    public void statisticByAccountSample() throws ParseException {

        Collection<Transaction> txns = List.of(
                new Transaction("id", 10.10, "ACC1", "GBP", "2016-03-01"),
                new Transaction("id", 7.77, "ACC1", "GBP", "2016-03-01"),
                new Transaction("id2", 20.12, "ACC2", "USD", "2016-08-01"),
                new Transaction("id3", 1.25, "ACC2", "EUR", "2016-10-01"),
                new Transaction("id3", 3.75, "ACC2", "EUR", "2016-10-03"),
                new Transaction("id31", 5.8, "ACC2", "USD", "2016-02-01"),
                new Transaction("id4", 40.14, "ACC3", "EUR", "2016-12-01")
        );

        var amountComparator = Comparator.comparing(Transaction::getAmount);

        Map<String, TxnStatistic> txnStatistics = txns.stream()
                .collect(
                        Collectors.groupingBy(Transaction::getAccount,
                                Collectors.teeing(
                                        Collectors.minBy(amountComparator),
                                        Collectors.maxBy(amountComparator),
                                        (min, max) -> new TxnStatistic(
                                                min.orElse(null),
                                                max.orElse(null)
                                        )
                                ))
                );

        txnStatistics.forEach((key, value) ->
                System.out.println(key + "\t" + value)
        );

    }

    @Getter
    static class TxnStatistic {
        private final Transaction min;
        private final Transaction max;

        public TxnStatistic(Transaction min, Transaction max) {
            this.min = min;
            this.max = max;
        }

        @Override
        public String toString() {
            return "min = " + min.getAmount() + ": max = " + max.getAmount();
        }
    }

}
