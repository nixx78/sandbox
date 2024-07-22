package lv.nixx.poc.collection.reduce;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;


class SimpleReduceTest {


    @Test
    void sumOfElement() {
        final Optional<Integer> sum = Stream.of(1, 2, 3, 4, 5, 6, 7).reduce(Integer::sum);
        assertEquals(Integer.valueOf(28), sum.get());
    }

    @Test
    void theLongestWord_ReduceMethod() {
        List<String> str = List.of("123", "1", "12", "12345");

        final Optional<String> collect = str.stream().reduce((t, u) -> t.length() > u.length() ? t : u);

        assertEquals("12345", collect.get());
    }

    @Test
    void findElementWithoutPair() {
        // Найти элемент у которых нет пары
        // XOR x ^ y
        final int r = Stream.of(1, 2, 5, 3, 2, 1, 3).reduce(0, (x, y) -> x ^ y);

        assertEquals(5, r);
    }

    @Test
    void findSumOfBigDecimals() {

        BigDecimal total = Stream.of(BigDecimal.valueOf(1), BigDecimal.valueOf(2), BigDecimal.valueOf(3), BigDecimal.valueOf(4))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        assertEquals(BigDecimal.valueOf(10), total);
    }

    @Test
    void reduceWithCombinerTest() {
        Collection<BigDecimal> values = List.of(BigDecimal.valueOf(1), BigDecimal.valueOf(2), BigDecimal.valueOf(3), BigDecimal.valueOf(4));

        //Эта функция объединяет два частичных результата, которые могут быть получены при параллельной обработке.
        BinaryOperator<BigDecimal> combiner = BigDecimal::add;

        BigDecimal total = values.parallelStream().reduce(BigDecimal.ZERO, BigDecimal::add, combiner);

        assertEquals(BigDecimal.valueOf(10), total);
    }


}
