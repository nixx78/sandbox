package lv.nixx.poc.collection.reduce;

import lv.nixx.poc.collection.domain.Transaction;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static org.assertj.core.api.Assertions.assertThat;

class GroupAndReduceTest {

    @Test
    void totalAmountByCurrency() {

        Collection<Transaction> set = List.of(
                new Transaction("id1", 30.13, "account1", "EUR"),
                new Transaction("id2", 20.12, "account1", "USD"),
                new Transaction("id3", 50.0, "account1", "USD"),
                new Transaction("id4", 150.0, "account1", "GBP"),
                new Transaction("id5", 450.0, "account1", "USD")
        );

        Map<String, BigDecimal> byCurrency = set.stream()
                .collect(
                        groupingBy(Transaction::getCurrency,
                                Collectors.reducing(BigDecimal.ZERO, Transaction::getAmount, BigDecimal::add)
                        )
                );

        assertThat(byCurrency).containsAllEntriesOf(Map.of(
                "GBP", BigDecimal.valueOf(150.0),
                "EUR", BigDecimal.valueOf(30.13),
                "USD", BigDecimal.valueOf(520.12)
        ));
    }

    @Test
    void totalAmountByAccount() {

        Collection<Transaction> set = List.of(
                new Transaction("id1", 30.13, "account1", "EUR"),
                new Transaction("id2", 20.12, "account1", "USD"),
                new Transaction("id3", 50.0, "account1", "USD"),
                new Transaction("id4", 150.0, "account2", "GBP"),
                new Transaction("id5", 450.0, "account2", "USD")
        );

        Map<String, BigDecimal> byAccount = set.stream()
                .collect(
                        groupingBy(Transaction::getAccount,
                                Collectors.reducing(BigDecimal.ZERO, Transaction::getAmount, BigDecimal::add)
                        )
                );

        assertThat(byAccount).containsAllEntriesOf(Map.of(
                "account2", BigDecimal.valueOf(600.0),
                "account1", BigDecimal.valueOf(100.25)
        ));

    }

}
