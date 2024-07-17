package lv.nixx.poc.collection.set;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SetPlayground {

    @Test
    void linkedHashSetSample() {

        // Collection with insert order
        LinkedHashSet<String> set = new LinkedHashSet<>();
        set.add("F");
        set.add("S");
        set.add("F1");

        assertThat(set.stream().toList()).isEqualTo(List.of("F","S","F1"));
    }

    @Test
    void treeSetSample() {

        // Collection with order defined by iterator. If we don't define iterator, order are not predicted
        TreeSet<String> set = new TreeSet<>(Comparator.reverseOrder());
        set.add("A");
        set.add("C");
        set.add("E");
        set.add("1");

        assertThat(set.stream().toList()).isEqualTo(List.of("E", "C", "A", "1"));
    }

    @Test
    void nullToTreeSet() {
        Set<String> set = new TreeSet<>();
        set.add("s1");

        assertThrows(NullPointerException.class, () -> set.add(null));
    }


    @Test
    void nullToLinkedHashSet() {
        // Тоже самое будет работать с HashSet
        Set<String> set = new LinkedHashSet<>();
        set.add("s1");
        set.add("s2");
        set.add(null);
        set.add("s4");
        // Все хорошо, null можно добавлять в LinkedHashSet
        set.forEach(System.out::println);
    }


}
