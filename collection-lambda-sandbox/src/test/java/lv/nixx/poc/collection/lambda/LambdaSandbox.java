package lv.nixx.poc.collection.lambda;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LambdaSandbox {

    @Test
    void calculateTest() {
        assertEquals(30L, calculate((a, b) -> a + b, 10, 20));
        assertEquals(20L, calculate((a, b) -> a - b, 30, 10));
        assertEquals(300L, calculate((a, b) -> a * b, 10, 30));
    }

    @Test
    void timeProviderTest() {
        TimeProvider tp = () -> 1000L;
        assertEquals(1000L, tp.currentTime());
    }

    @Test
    void functionSandbox() {
        Function<Long, String> f = t -> "[" + t + ": apply]";

        assertEquals("[10: apply]", f.apply(10L));
        assertEquals("[2000: apply]: andThen", f.andThen(t -> t + ": andThen").apply(2000L));
    }

    @Test
    void composeSandbox() {
        Function<Integer, Integer> calculate = x -> x + 1;
        Function<Integer, String> toString = x -> "Result: " + x;

        String result = toString.compose(calculate).apply(5);
        assertEquals("Result: 6", result);
    }

    @Test
    void composeAndThenSandbox() {
        Function<Integer, String> toString = x -> "[Calculation result: " + x + "]";

        String result = toString
                .compose((Function<Integer, Integer>) x -> x + 1)
                .compose((Function<Integer, Integer>) x -> x * 100)
                .andThen(this::send)
                .apply(5);

        assertEquals("sendId: [Calculation result: 501]", result);
    }

    @Test
    void supplierSample() {
        assertThat(addToCollection(ArrayList::new, "V1", "V2", "V3")).isEqualTo(List.of("V1", "V2", "V3"));
        assertThat(addToCollection(ArrayList::new, 1, 2, 3)).isEqualTo(List.of(1, 2, 3));
    }

    @SafeVarargs
    private <T> Collection<T> addToCollection(Supplier<Collection<T>> c, T... value) {
        Collection<T> ts = c.get();
        ts.addAll(Stream.of(value).toList());

        return ts;
    }

    @Test
    void biFunctionSample() {
        BiFunction<BigDecimal, Integer, String> bi = (b, i) -> b + ":" + i;
        assertEquals("100:777", bi.apply(BigDecimal.valueOf(100), 777));
    }

    private String send(String v) {
        return "sendId: " + v;
    }

    private int calculate(Calculator calculator, int a, int b) {
        return calculator.calculate(a, b);
    }

    interface Calculator {
        int calculate(int a, int b);
    }

    interface TimeProvider {
        long currentTime();
    }

}
