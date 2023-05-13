package lv.nixx.poc.datastruct.map;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.NavigableMap;
import java.util.TreeMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class NavigableMapSandbox {

    @Test
    void mapWithOriginalOrder() {

        NavigableMap<Integer, String> m = new TreeMap<>();
        m.put(1, "One");
        m.put(10, "Ten");
        m.put(2, "Two");
        m.put(5, "Five");
        m.put(3, "Three");
        m.put(4, "Four");
        m.put(7, "Seven");
        m.put(6, "Six");
        m.put(9, "Nine");
        m.put(8, "Eight");

        assertThat(m.keySet(), Matchers.contains(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));

        assertEquals(Integer.valueOf(1), m.firstKey());
        assertEquals(Integer.valueOf(10), m.lastKey());

        System.out.println("Lower:" + m.lowerKey(4) + " Higher: " + m.higherKey(4));

        assertEquals(Integer.valueOf(3), m.lowerKey(4));
        assertEquals(Integer.valueOf(5), m.higherKey(4));

        assertEquals(Integer.valueOf(10), m.lowerKey(777));
        assertNull(m.higherKey(777));

        // Получаем список ключей перед элементом
        assertThat(m.headMap(5, true).keySet(), Matchers.contains(1, 2, 3, 4, 5));

        // Получаем список ключей после элемента
        assertThat(m.tailMap(5, false).keySet(), Matchers.contains(6, 7, 8, 9, 10));

        // Список ключей в инрветированноем порядке
        assertThat(m.descendingKeySet(), Matchers.contains(10, 9, 8, 7, 6, 5, 4, 3, 2, 1));
    }

    @Test
    void mapWithGaps() {

        NavigableMap<Integer, String> m = new TreeMap<>();
        m.put(1, "One");
        m.put(5, "Five");
        m.put(10, "Ten");

        assertEquals(Integer.valueOf(1), m.ceilingKey(1));

        assertEquals(Integer.valueOf(5), m.ceilingKey(4));
        assertEquals(Integer.valueOf(1), m.floorKey(4));

        assertEquals(Integer.valueOf(1), m.pollFirstEntry().getKey());
        assertEquals(Integer.valueOf(10), m.pollLastEntry().getKey());

        assertEquals(1, m.size());
    }




}
