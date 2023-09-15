package lv.nixx.poc.datastruct.collection;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public class GroupingWithSortingTest {

    final Collection<RawData> rawData = List.of(
            new RawData("Name1", BigDecimal.valueOf(10), "group1", BigDecimal.valueOf(1), BigDecimal.valueOf(10)),
            new RawData("Name1", BigDecimal.valueOf(10), "group1", BigDecimal.valueOf(10), BigDecimal.valueOf(11)),
            new RawData("Name2", BigDecimal.valueOf(5), "group2", BigDecimal.valueOf(10), BigDecimal.valueOf(20)),
            new RawData("Name3", BigDecimal.valueOf(1), "group2", BigDecimal.valueOf(5), BigDecimal.valueOf(30)),
            new RawData("Name4", BigDecimal.valueOf(20), "group1", BigDecimal.valueOf(5), BigDecimal.valueOf(40)),
            new RawData("Name4", BigDecimal.valueOf(20), "group2", BigDecimal.valueOf(1), BigDecimal.valueOf(41))
    );

    @Test
    void doubleGroupingSampleWithSorting() {
        Map<ComparableHolder, Map<String, List<RawData>>> collect = rawData.stream().collect(
                groupingBy(t -> new ComparableHolder(t.getName(), t.getNamePos()), TreeMap::new,
                        groupingBy(RawData::getGroup, TreeMap::new, Collectors.toList()))
        );

        collect.forEach((k, v) -> {
            System.out.println("Position:" + k.pos() + " : " + k.name());
            v.forEach((k1, v1) -> v1.forEach(t-> System.out.println("\t\t" + t)));
        });
    }


    @Test
    void groupWithSorting_ComparableHolder() {

        Map<ComparableHolder, Set<RawData>> collect = rawData.stream().collect(
                groupingBy(t -> new ComparableHolder(t.getName(), t.getNamePos()), TreeMap::new, Collectors.toSet())
        );

        collect.entrySet().forEach(System.out::println);
    }

    @Test
    void groupWithSorting_Comparator() {

        Map<Holder, Set<RawData>> collect = rawData.stream().collect(
                groupingBy(t -> new Holder(t.getName(), t.getNamePos()),
                        () -> new TreeMap<>(Comparator.comparing(o -> o.pos))
                        , Collectors.toSet())
        );

        collect.entrySet().forEach(System.out::println);
    }

    record ComparableHolder(String name, BigDecimal pos) implements Comparable<ComparableHolder> {
        @Override
        public int compareTo(ComparableHolder o) {
            return this.pos.compareTo(o.pos());
        }
    }

    record Holder(String name, BigDecimal pos) {
    }


    @RequiredArgsConstructor
    @Getter
    @ToString
    static class RawData {
        final String name;
        final BigDecimal namePos;

        final String group;
        final BigDecimal groupPos;
        final BigDecimal amount;
    }


}
