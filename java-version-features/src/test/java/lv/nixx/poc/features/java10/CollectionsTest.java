package lv.nixx.poc.features.java10;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class CollectionsTest {

    @Test
    public void sample() {

        var lst =  new ArrayList<>(List.of("1", "2", "3"));
        List<String> copy = List.copyOf(lst);

        lst.add("x");

        System.out.println("Original list: " + lst);
        System.out.println("List copy: " + copy);
    }

}
