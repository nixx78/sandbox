package lv.nixx.poc.datastruct.map;

import org.hamcrest.Matchers;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class MapMergeSample {

    @Test
    public void merge() {

        Map<String, BigDecimal> m1 = Map.of("1", BigDecimal.ONE, "2", BigDecimal.valueOf(2));
        Map<String, BigDecimal> m2 = Map.of("1", BigDecimal.valueOf(9), "3", BigDecimal.valueOf(3));

        Map<String, BigDecimal> resultMap = Stream.of(m1, m2)
                .flatMap(map -> map.entrySet().stream())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        BigDecimal::add));

        System.out.println(resultMap);

        assertEquals(3, resultMap.size());
        assertThat(BigDecimal.valueOf(10), Matchers.comparesEqualTo(resultMap.get("1")));
        assertThat(BigDecimal.valueOf(2), Matchers.comparesEqualTo(resultMap.get("2")));
        assertThat(BigDecimal.valueOf(3), Matchers.comparesEqualTo(resultMap.get("3")));
    }

    @Test
    public void mergeWithResultMap() {

        Map<String, BigDecimal> m1 = Map.of("1", BigDecimal.ONE, "2", BigDecimal.valueOf(2));
        Map<String, BigDecimal> m2 = Map.of("1", BigDecimal.valueOf(9), "3", BigDecimal.valueOf(3));

        Map<String, BigDecimal> collect = Stream.of(m1, m2)
                .flatMap(map -> map.entrySet().stream())
                .collect(
                        Collectors.toMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue,
                                BigDecimal::add));

        Map<String, String> finalResult = collect.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, t -> t.getValue() + "Value"));

        System.out.println(finalResult);

        assertEquals(3, finalResult.size());

    }


}
