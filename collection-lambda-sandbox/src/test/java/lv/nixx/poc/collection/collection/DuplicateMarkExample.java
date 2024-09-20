package lv.nixx.poc.collection.collection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class DuplicateMarkExample {

    @Test
    void markDuplicates() {

        Collection<Item> initialCollection = List.of(
                new Item(10, "V1"),
                new Item(10, "V2"),
                new Item(11, "V3"),
                new Item(12, "V4"),
                new Item(13, "V5"),
                new Item(13, "V6")
        );

        List<Item> markedAsDuplicates = initialCollection.stream()
                .collect(Collectors.groupingBy(Item::getId))
                .values().stream()
                .flatMap(t -> {
                    Stream<Item> stream = t.stream();
                    if (t.size() > 1) {
                        return stream.map(Item::setAsDuplicate);
                    }

                    return stream;
                })
                .toList();


        assertThat(markedAsDuplicates).usingRecursiveComparison().isEqualTo(
                List.of(new Item(10, "V1", true),
                        new Item(10, "V2", true),
                        new Item(11, "V3", false),
                        new Item(12, "V4", false),
                        new Item(13, "V5", true),
                        new Item(13, "V6", true)
                )
        );

    }


    @ToString
    @Getter
    @RequiredArgsConstructor
    @AllArgsConstructor
    static class Item {
        private final int id;
        private final String value;
        private boolean duplicate;

        public Item setAsDuplicate() {
            this.duplicate = true;
            return this;
        }
    }

}
