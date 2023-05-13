package lv.nixx.poc.datastruct.map;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.TreeMap;

import static lv.nixx.poc.datastruct.map.BelongsToRange.RangeLabel.*;
import static org.junit.jupiter.api.Assertions.assertAll;

class BelongsToRange {

    @Test
    public void belongsToRangeWithoutGaps() {

        NavigableMap<Integer, RangeLabel> rangeMap = new TreeMap<>();
        rangeMap.put(1, Low);
        rangeMap.put(1000, Medium);
        rangeMap.put(2000, High);
        rangeMap.put(3000, ExtraHigh);

        assertAll(
                () -> Assertions.assertEquals(Low, rangeMap.floorEntry(2).getValue()),
                () -> Assertions.assertEquals(Low, rangeMap.floorEntry(500).getValue()),
                () -> Assertions.assertEquals(Low, rangeMap.floorEntry(999).getValue()),
                () -> Assertions.assertEquals(Medium, rangeMap.floorEntry(1000).getValue()),
                () -> Assertions.assertEquals(ExtraHigh, rangeMap.floorEntry(7777).getValue())
        );

    }

    @Test
    public void belongsToRangeWithGaps() {

        NavigableMap<Integer, Range> rangeMap = new TreeMap<>();

        rangeMap.put(1, new Range(999, Low));
        rangeMap.put(1500, new Range(3000, Medium));
        rangeMap.put(4000, new Range(5000, High));

        assertAll(
                () -> Assertions.assertEquals(OutOfRange, getLabel(rangeMap, 0)),
                () -> Assertions.assertEquals(Low, getLabel(rangeMap, 500)),
                () -> Assertions.assertEquals(OutOfRange, getLabel(rangeMap, 1200)),
                () -> Assertions.assertEquals(Medium, getLabel(rangeMap, 1500)),
                () -> Assertions.assertEquals(High, getLabel(rangeMap, 4500)),
                () -> Assertions.assertEquals(OutOfRange, getLabel(rangeMap, 7777))
        );
    }

    private RangeLabel getLabel(NavigableMap<Integer, Range> rangeMap, int number) {
        final Entry<Integer, Range> floorEntry = rangeMap.floorEntry(number);

        return floorEntry == null || number > floorEntry.getValue().to ? OutOfRange : floorEntry.getValue().label;
    }


    static class Range {
        int to;
        RangeLabel label;

        Range(int to, RangeLabel label) {
            this.to = to;
            this.label = label;
        }
    }

    enum RangeLabel {
        Low, Medium, High, ExtraHigh, OutOfRange
    }

}
