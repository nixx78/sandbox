package lv.nixx.poc.collection.map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class MapWithListKeySample {

    @Test
    void getListOfKeyTest() {

        Map<Key, String> m = Map.of(
                Key.of("k1", "k2", "k3"), "VALUE1-3",
                Key.of("k4"), "VALUE4",
                Key.of("k5"), "VALUE5",
                Key.of("k6", "k7"), "VALUE6-7"
        );

        assertAll(
                () -> assertEquals("VALUE1-3", m.get(Key.of("k2"))),
                () -> assertEquals("VALUE4", m.get(Key.of("k4"))),
                () -> assertEquals("VALUE6-7", m.get(Key.of("k6"))),
                () -> assertNull(m.get(Key.of("NOT_EXISTING_KEY")))
        );

    }

    @AllArgsConstructor
    @Getter
    static class Key {
        Collection<String> value;

        static Key of(String... v) {
            return new Key(List.of(v));
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Key key = (Key) o;
            for (String s : value) {
                if (key.value.contains(s)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }
    }

}
