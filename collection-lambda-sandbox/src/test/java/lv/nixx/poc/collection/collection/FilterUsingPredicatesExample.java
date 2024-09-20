package lv.nixx.poc.collection.collection;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;

class FilterUsingPredicatesExample {

    @Test
    void filterUsingPredicates() {

        Collection<String> value = List.of("10", "N", "XXX", "xxx", "abc", "123", "A", "B");

        Predicate<String> startsWithOne = t -> t.startsWith("1");
        Predicate<String> lengthMoreThatOne = t -> t.length() > 1;
        Predicate<String> secondCharacterIsX = t -> t.charAt(1) == 'X';

        List<String> list = value.stream()
                .filter(lengthMoreThatOne.and(startsWithOne.or(secondCharacterIsX)))
                .toList();

        assertThat(list).isEqualTo(List.of("10", "XXX", "123"));
    }

}
