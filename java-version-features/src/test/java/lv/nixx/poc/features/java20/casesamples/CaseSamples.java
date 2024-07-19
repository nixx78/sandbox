package lv.nixx.poc.features.java20.casesamples;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CaseSamples {

    @Test
    void caseSamples() {

        assertAll(
                () -> assertEquals("V1 or V2", process(Value.V1)),
                () -> assertEquals("V1 or V2", process(Value.V2)),
                () -> assertEquals("V3", process(Value.V3)),
                () -> assertEquals("Default", process(Value.V4)),
                () -> assertEquals("Unknown", process(null))
        );
    }

    @Test
    void amountProcessorTest() {
        assertAll(
                () -> assertEquals("Bonus amount", amountProcessor(1)),
                () -> assertEquals("Bonus amount", amountProcessor(10)),
                () -> assertEquals("Bonus amount", amountProcessor(100)),
                () -> assertEquals("Amount positive", amountProcessor(200)),
                () -> assertEquals("Amount negative", amountProcessor(-200))
        );
    }

    String process(Value v) {
        return switch (v) {
            case V1, V2 -> "V1 or V2";
            case V3 -> "V3";
            case null -> "Unknown";

            default -> "Default";
            // Also possible to use:  case null, default -> "n/a";
        };
    }

    String amountProcessor(Integer amount) {
        return switch (amount) {
            case null -> "Amount not defined";
            case 1, 10, 100 -> "Bonus amount";

            case Integer i when i > 0 -> "Amount positive";
            default -> "Amount negative";
        };
    }

    enum Value {
        V1, V2, V3, V4
    }

}
