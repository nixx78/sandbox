package lv.nixx.poc.collection.txn.stream.statistic;

import lombok.NoArgsConstructor;
import lombok.ToString;
import lv.nixx.poc.collection.domain.Transaction;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.Month;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import static java.util.stream.Collectors.groupingBy;

public class TxnByMonthStatistic {

    @Test
    public void createTxnByMonthStatistic() {

        Collection<Transaction> txns = List.of(
                new Transaction("id", 10.10, "ACC1", "GBP", "2016-03-01"),
                new Transaction("id2", 20.12, "ACC2", "USD", "2016-08-01"),
                new Transaction("id3", 1.25, "ACC2", "EUR", "2016-10-01"),
                new Transaction("id3", 3.75, "ACC2", "EUR", "2016-10-03"),
                new Transaction("id31", 5.8, "ACC2", "USD", "2016-02-10"),
                new Transaction("id4", 40.14, "ACC3", "EUR", "2016-12-01")
        );

        Comparator<String> monthComparator = (d1, d2) -> {
            Integer x1 = Month.valueOf(d1).getValue();
            Integer x2 = Month.valueOf(d2).getValue();
            return x1.compareTo(x2);
        };

        Map<String, Map<String, StatisticAccumulator>> res = txns.stream()
                .collect(
                        groupingBy(t -> t.getLastUpdateDate().getMonth().toString(), () -> new TreeMap<>(monthComparator),
                                groupingBy(Transaction::getCurrency, new StaticsticCollector()))
                );

        for (Map.Entry<String, Map<String, StatisticAccumulator>> e : res.entrySet()) {
            String month = e.getKey();
            System.out.println(month);
            for (Map.Entry<String, StatisticAccumulator> monthStats : e.getValue().entrySet()) {
                String currency = monthStats.getKey();
                System.out.println("\t" + currency + "\n \t\t" + monthStats.getValue());
            }
        }


    }

    static class StaticsticCollector implements Collector<Transaction, StatisticAccumulator, StatisticAccumulator> {

        @Override
        public Supplier<StatisticAccumulator> supplier() {
            return StatisticAccumulator::new;
        }

        @Override
        public BiConsumer<StatisticAccumulator, Transaction> accumulator() {
            return StatisticAccumulator::increaseByTransaction;
        }

        @Override
        public BinaryOperator<StatisticAccumulator> combiner() {
            return (s1, s2) -> s1;
        }

        @Override
        public Function<StatisticAccumulator, StatisticAccumulator> finisher() {
            return Function.identity();
        }

        @Override
        public Set<Characteristics> characteristics() {
            return new HashSet<>(List.of(Characteristics.IDENTITY_FINISH));
        }

    }

    @NoArgsConstructor
    @ToString
    static class StatisticAccumulator {
        private String currency;
        private int txnCount = 0;
        private BigDecimal amount = BigDecimal.ZERO;

        void increaseByTransaction(Transaction txn) {
            this.currency = txn.getCurrency();
            this.txnCount++;
            this.amount = this.amount.add(Optional.ofNullable(txn.getAmount()).orElse(BigDecimal.ZERO));
        }
    }


}
