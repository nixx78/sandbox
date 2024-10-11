package lv.nixx.poc.collection.map;

import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

class BelongsToRangeExample {

    @Test
    void rangeTest() {

        Range range = new Range(Map.of(
                10, 20,
                30, 40,
                50, 60
        ));

        assertAll(
                () -> assertTrue(range.isWithinRanges(10, true)),
                () -> assertTrue(range.isWithinRanges(15, true)),
                () -> assertFalse(range.isWithinRanges(10, false)),
                () -> assertFalse(range.isWithinRanges(25, true))
        );

    }

    static class Range {

        private final NavigableMap<Integer, Integer> ranges;

        public Range(Map<Integer, Integer> p) {
            this.ranges = new TreeMap<>(p);
        }

        public boolean isWithinRanges(int p, boolean includeFrom) {

            Map.Entry<Integer, Integer> entry = ranges.floorEntry(p);

            if (entry != null) {
                int from = entry.getKey();
                int to = entry.getValue();

                if (includeFrom) {
                    return p >= from && p <= to;
                } else {
                    return p > from && p <= to;
                }
            }

            return false;
        }


    }

}
