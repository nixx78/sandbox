package lv.nixx.poc.features.java21.pattern_matching;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CaseWhenSample {

    @Test
    void transformSample() {

        assertAll(
                () -> assertEquals("A.MAPPED", transformString("A")),
                () -> assertEquals("B.MAPPED", transformString("B")),
                () -> assertEquals("Input can't be null", assertThrows(IllegalArgumentException.class, () -> transformString(null)).getMessage()),
                () -> assertEquals("Mapping for [XXX] not supported", assertThrows(IllegalArgumentException.class, () -> transformString("XXX")).getMessage())
        );

    }


    static String transformString(String input) {
        String output;
        switch (input) {
            case null -> throw new IllegalArgumentException("Input can't be null");
            case String s when "A".equalsIgnoreCase(s) -> output = "A.MAPPED";
            case String s when "B".equalsIgnoreCase(s) -> output = "B.MAPPED";
            case String s -> throw new IllegalArgumentException("Mapping for [" + s + "] not supported");
        }
        return output;
    }

}
