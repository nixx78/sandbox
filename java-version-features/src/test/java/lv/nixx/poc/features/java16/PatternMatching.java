package lv.nixx.poc.features.java16;

import org.junit.jupiter.api.Test;

class PatternMatching {


    @Test
    void test() {
        process(100L);
        process("String.value");
    }

    private void process(Object o) {

        if (o instanceof Long v) {
            System.out.printf("Process as Long: %s \n", v);
        }

        if (o instanceof String v) {
            System.out.printf("Process as String: %s \n", v);
        }

    }


}
