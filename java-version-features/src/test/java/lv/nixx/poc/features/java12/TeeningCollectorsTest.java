package lv.nixx.poc.features.java12;

import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.summingDouble;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TeeningCollectorsTest {

    @Test
    public void sample() {

        double mean = Stream.of(1, 2, 3, 4, 5)
                .collect(Collectors.teeing(
                        summingDouble(i -> i),
                        counting(),
                        (sum, n) -> sum / n));

        assertEquals(3, mean, 0);
    }
}
