package lv.nixx.poc.datastruct.compare;

import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class StringSorting {

    @Test
    public void sortByDefaultOrder() {

        List<String> sorted = Stream.of("X", "xx", "1", "AC", "a", "B",  "AbC", "ADC", "XXX", "AD", "M")
                .sorted()
                .collect(Collectors.toList());

        System.out.println(sorted);

        assertEquals("[1, AC, AD, ADC, AbC, B, M, X, XXX, a, xx]", sorted.toString());
    }

}
