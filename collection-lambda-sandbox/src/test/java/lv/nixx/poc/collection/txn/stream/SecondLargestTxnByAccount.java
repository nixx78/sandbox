package lv.nixx.poc.collection.txn.stream;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;
import static org.assertj.core.api.Assertions.assertThat;

class SecondLargestTxnByAccount {

    @Test
    void getMapWithSecondLargestTxn() {

        List<Txn> elements = List.of(
                new Txn(1L, 10.10, "ACC1", LocalDateTime.parse("2016-01-03T10:12:10")),
                new Txn(2L, 20.10, "ACC1", LocalDateTime.parse("2016-01-03T11:12:10")),
                new Txn(3L, 30.10, "ACC1", LocalDateTime.parse("2016-01-03T00:12:10")),

                new Txn(4L, 100.00, "ACC2", LocalDateTime.parse("2016-01-03T10:12:10")),
                new Txn(5L, 200.00, "ACC2", LocalDateTime.parse("2016-01-03T11:12:10")),

                new Txn(6L, null, "ACC3", LocalDateTime.parse("2016-01-03T10:12:10")),
                new Txn(7L, 1.00, "ACC3", LocalDateTime.parse("2016-01-03T11:12:10")),

                new Txn(8L, 1.00, "ACC4", LocalDateTime.parse("2016-01-03T10:12:10")),
                new Txn(9L, null, "ACC4", LocalDateTime.parse("2016-01-03T11:12:10")),

                new Txn(10L, 7.00, "ACC5", LocalDateTime.parse("2016-01-03T11:12:10"))
        );

        Map<String, Optional<Double>> resultMap = elements.stream()
                .collect(groupingBy(Txn::getAccount))
                .entrySet()
                .stream()
                .filter(t -> t.getValue().size() > 1)
                .collect(toMap(
                        Map.Entry::getKey,
                        e -> e.getValue()
                                .stream()
                                .sorted(comparing(Txn::getDateTime).reversed())
                                .skip(1)
                                .findFirst()
                                .map(Txn::getPrice)
                ));

        assertThat(resultMap).usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(Map.of(
                   "ACC1", Optional.of(10.1),
                   "ACC2", Optional.of(100.0),
                   "ACC3", Optional.empty(),
                   "ACC4", Optional.of(1.0)
                ));

    }

    @AllArgsConstructor
    @Getter
    static class Txn {
        Long id;
        Double price;
        String account;
        LocalDateTime dateTime;
    }

}
