package lv.nixx.poc.collection.reduce;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.junit.Test;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.function.BinaryOperator.maxBy;
import static java.util.stream.Collectors.*;

public class ReducerSample {

    @Test
    public void reducerSample() throws Exception {

        Collection<Posting> postings = List.of(
                new Posting("post1", toTimestamp("04/26/2019 12:00:01"), toDate("04/26/2019"), BigDecimal.valueOf(10.01)),
                new Posting("post1", toTimestamp("04/26/2019 12:00:02"), toDate("04/26/2019"), BigDecimal.valueOf(20.01)),

                new Posting("post1", toTimestamp("04/25/2019 12:00:03"), toDate("04/25/2019"), BigDecimal.valueOf(30.01)),

                new Posting("post2", toDate("04/25/2019"), toTimestamp("04/26/2019 12:00:01"), BigDecimal.valueOf(30.03)),

                new Posting("post3", toDate("04/25/2019"), toTimestamp("04/26/2019 12:00:01"), BigDecimal.valueOf(33.33))
        );

        Collection<Collection<Optional<Posting>>> collect = postings.stream()
                .collect(
                        collectingAndThen(
                                groupingBy(Posting::getId,
                                        collectingAndThen(
                                                groupingBy(Posting::getSettleDate,
                                                        Collectors.maxBy(Comparator.comparing(Posting::getTimestamp))
                                                ), Map::values
                                        )
                                ), Map::values)
                );


        Collection<Optional<Posting>> result = collect.stream()
                .flatMap(Collection::stream)
                .map(x -> x.orElse(null))
                .collect(
                        collectingAndThen(
                                groupingBy(Posting::getId, reducing(this::calculateBalance))
                                , Map::values
                        )
                );

        result.forEach(System.out::println);

    }

    @Test
    public void maxBySample() throws ParseException {

        Collection<Posting> postings = List.of(
                new Posting("post1", toTimestamp("07/26/2022 12:00:01"), toDate("07/26/2022"), BigDecimal.valueOf(10.01)),
                new Posting("post1", toTimestamp("07/26/2022 12:00:02"), toDate("07/26/2022"), BigDecimal.valueOf(20.01)),

                new Posting("post1", toTimestamp("07/25/2022 12:00:03"), toDate("07/25/2022"), BigDecimal.valueOf(30.01)),

                new Posting("post2", toDate("07/25/2022"), toTimestamp("07/26/2022 12:00:01"), BigDecimal.valueOf(30.03)),

                new Posting("post3", toDate("07/25/2022"), toTimestamp("07/26/2022 12:00:01"), BigDecimal.valueOf(33.33)),
                new Posting("post3", toDate("07/07/2022"), toTimestamp("07/26/2022 12:00:01"), BigDecimal.valueOf(33.33))
        );

        Map<String, Posting> postingsWithMaxTimestamp = postings.stream()
                .collect(toMap(Posting::getId, Function.identity(), maxBy(Comparator.comparing(Posting::getTimestamp)), TreeMap::new));

        postingsWithMaxTimestamp.entrySet().forEach(System.out::println);


    }

    private Posting calculateBalance(Posting t, Posting t1) {
        t.setBalance(t.getBalance().add(t1.getBalance()));
        return t;
    }


    private Date toDate(String date) throws ParseException {
        return new SimpleDateFormat("MM/dd/yyyy").parse(date);
    }

    private Date toTimestamp(String timestamp) throws ParseException {
        return new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(timestamp);
    }


    @Data
    @ToString
    @AllArgsConstructor
    static class Posting implements Comparable<Posting> {
        String id;
        Date timestamp;
        Date settleDate;
        BigDecimal balance;

        @Override
        public int compareTo(Posting o) {
            return id.compareTo(o.id);
        }
    }

}
