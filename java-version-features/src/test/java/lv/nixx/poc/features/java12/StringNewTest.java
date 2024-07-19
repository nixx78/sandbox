package lv.nixx.poc.features.java12;

import org.junit.jupiter.api.Test;

public class StringNewTest {

    @Test
    public void sandbox() {

        String transform = "String".transform(t -> t + "Transformed");
        System.out.println(transform);
    }

}
