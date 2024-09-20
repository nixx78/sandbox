package lv.nixx.poc.collection.collection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.assertj.core.util.BigDecimalComparator;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class GetValuesWithMinimalID {

    @Test
    void test() {
        // Пример показывает как можно извлечь историю изменения Value, считаем, что значение изменяется только,
        // при минимальном ID, остальное, время оно просто копируется.

        Collection<Value> values = Stream.of(
                        new Value(6, BigDecimal.valueOf(5.00)),     // -> rest
                        new Value(7, BigDecimal.valueOf(1.00)),     // -> rest
                        new Value(8, BigDecimal.valueOf(1.00)),
                        new Value(9, BigDecimal.valueOf(10.00)),    // -> rest
                        new Value(1, BigDecimal.valueOf(1.00)),     // -> rest
                        new Value(2, BigDecimal.valueOf(1.00)),
                        new Value(3, BigDecimal.valueOf(2.00)),     // -> rest
                        new Value(4, BigDecimal.valueOf(2.00)),
                        new Value(5, BigDecimal.valueOf(4.00))      // -> rest
                )
                .sorted(Comparator.comparingInt(Value::getId)) // Список обязятельно должен быть отсортирован !
                .collect(ArrayList::new, (acc, value) -> {
                    if (acc.isEmpty() || !acc.get(acc.size() - 1).getValue().equals(value.getValue())) {
                        acc.add(value);
                    }
                }, ArrayList::addAll);

        assertThat(values).usingRecursiveComparison()
                .withComparatorForType(BigDecimalComparator.BIG_DECIMAL_COMPARATOR, BigDecimal.class)
                .isEqualTo(List.of(
                        new Value(1, BigDecimal.valueOf(1.0)),
                        new Value(3, BigDecimal.valueOf(2.0)),
                        new Value(5, BigDecimal.valueOf(4.0)),
                        new Value(6, BigDecimal.valueOf(5.0)),
                        new Value(7, BigDecimal.valueOf(1.0)),
                        new Value(9, BigDecimal.valueOf(10.0))
                ));
    }

    @AllArgsConstructor
    @Getter
    @ToString
    static class Value {
        int id;
        BigDecimal value;
    }

}
