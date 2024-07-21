package lv.nixx.poc.features.java21;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StringLiteralSandbox {

    @Test
    void sendMessageTest() {
        var code = 100;
        var message = "Error.Message";

        String m = STR."Error, code [\{code + 20}] message [\{message}]";

        assertEquals("Error, code [120] message [Error.Message]", m);
    }

}
