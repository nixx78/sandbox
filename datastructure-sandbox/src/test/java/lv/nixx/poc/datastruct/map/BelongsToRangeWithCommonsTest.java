package lv.nixx.poc.datastruct.map;

import lombok.Getter;
import org.apache.commons.lang3.Range;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.*;

class BelongsToRangeWithCommonsTest {

    @Test
    void getLabelForRange() {

        assertAll(
                () -> assertEquals("First10", getLabelForRange(10)),
                () -> assertEquals("First20", getLabelForRange(20)),
                () -> assertEquals("Last100", getLabelForRange(99)),
                () -> assertEquals("NotInRange", getLabelForRange(21)),
                () -> assertEquals("NotInRange", getLabelForRange(777))
        );

    }

    @Test
    void getColorLabelForRange() {

        assertAll(
                () -> assertThat(getLabelsForRange(1), containsInAnyOrder("Red")),
                () -> assertThat(getLabelsForRange(7), containsInAnyOrder("Red", "White")),
                () -> assertThat(getLabelsForRange(55), containsInAnyOrder("Black")),
                () -> assertTrue(getLabelsForRange(30).isEmpty())
        );

    }

    private String getLabelForRange(int v) {
        return Stream.of(
                        new RangeHolder("First10", 1, 10),
                        new RangeHolder("First20", 11, 20),
                        new RangeHolder("Last100", 91, 100)
                ).filter(t -> t.isBelongsToRange(v) != null)
                .findFirst()
                .orElse(RangeHolder.notFound())
                .label;
    }

    private Collection<String> getLabelsForRange(int v) {
        return Stream.of(
                        new RangeHolder("Red", 1, 10),
                        new RangeHolder("White", 5, 15),
                        new RangeHolder("Black", 50, 100)
                ).filter(t -> t.isBelongsToRange(v) != null)
                .map(RangeHolder::getLabel)
                .collect(Collectors.toList());
    }

    static class RangeHolder {
        @Getter
        String label;
        Range<Integer> range;

        static RangeHolder notFound() {
            return new RangeHolder("NotInRange", -1, -1);
        }

        RangeHolder(String label, int from, int to) {
            this.label = label;
            range = Range.between(from, to);
        }

        RangeHolder isBelongsToRange(int v) {
            return range.contains(v) ? this : null;
        }

    }

}
