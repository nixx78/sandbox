package lv.nixx.poc.features.java15.local;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FormattedStringTest {

    @Test
    void formattedStringTest() {

        String str = """
        Hi user,
        '%s'!""".formatted("UserName");

        assertEquals("Hi user,\n" +
                "'UserName'!", str);

        assertEquals("Hi user 'XXXX'", "Hi user '%s'".formatted("XXXX"));

    }

}
