package lv.nixx.poc.features.java10;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class OptionalTest {

    @Test
    public void sample() {
        assertThrows(NoSuchElementException.class, () -> Optional.empty().orElseThrow());
    }

}
