package lv.nixx.poc.features.java13;

import org.junit.jupiter.api.Test;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SwitchTest {

    @Test
    public void test() {

        int choice = 2;
        switch (choice) {
            case 1, 2, 3:
                out.println(choice);
                break;
            default:
                out.println("integer is greater than 3");
        }


    }

    @Test
    public void yieldTest() {
        assertEquals("1c", map(1));
        assertEquals("5n", map(5));
        assertEquals("unknown", map(777));
    }

    @Test
    public void yield1Test() {
        assertEquals("1c", map1("1"));
        assertEquals("5n", map1("5"));
        assertEquals("unknown", map1("777"));
    }

    private String map(int choice) {
        return switch (choice) {
            case 1, 2, 3:
                yield choice + "c";
            case 4, 5, 7:
                yield choice + "n";
            default:
                yield "unknown";
        };
    }

    private String map1(String choice) {
        return switch (choice) {
            case "1", "2", "3" -> choice + "c";
            case "4", "5", "7" -> choice + "n";
            default -> "unknown";
        };
    }


}
