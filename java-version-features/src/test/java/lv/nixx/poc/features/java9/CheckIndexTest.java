package lv.nixx.poc.features.java9;

import org.junit.jupiter.api.Test;

import static java.util.Objects.checkFromIndexSize;
import static java.util.Objects.checkIndex;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CheckIndexTest {

    @Test
    void checkIndexSample() {

        IndexOutOfBoundsException ex =
                assertThrows(IndexOutOfBoundsException.class, () -> checkFromIndexSize(3, 8, 10));
        assertEquals("Range [3, 3 + 8) out of bounds for length 10", ex.getMessage());

        assertEquals(3, checkFromIndexSize(3, 1, 10));

        IndexOutOfBoundsException ex1 =
                assertThrows(IndexOutOfBoundsException.class, () -> checkIndex(-3, 10));

        assertEquals("Index -3 out of bounds for length 10", ex1.getMessage());

        assertEquals(7, checkIndex(7, 10));
    }

}
