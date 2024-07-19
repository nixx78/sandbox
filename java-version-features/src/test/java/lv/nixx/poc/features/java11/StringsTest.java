package lv.nixx.poc.features.java11;

import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class StringsTest {

    @Test
    public void isBlankSample() {
        assertTrue(" ".isBlank());
        assertTrue("".isBlank());
        assertFalse("XYZ".isBlank());
    }

    @Test
    public void linesSample() {
        String str = "line1\nline2\nline3";
        System.out.println(str);
        System.out.println(str.lines().collect(Collectors.toList()));
    }

    @Test
    public void repeatSample() {
        System.out.println("X.repeat(10):" + "X".repeat(10));
    }

}
