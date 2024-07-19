package lv.nixx.poc.features.java17;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class NewSwitchSample {

    //TODO https://habr.com/ru/post/577924/

    @Test
    void sample() {
        assertAll("",
                () -> assertEquals("int 100", switchByType(100)),
                () -> assertEquals("long 777", switchByType(777L)),
                () -> assertEquals("double 10,200000", switchByType(10.20)),
                () -> assertEquals("String 'Text'", switchByType("Text"))
        );
    }

    private String switchByType(Object o) {
        return switch (o) {
            case Integer i -> String.format("int %d", i);
            case Long l -> String.format("long %d", l);
            case Double d -> String.format("double %f", d);
            case String s -> String.format("String '%s'", s);
            default -> o.toString();
        };
    }

    @Test
    void caseWithNullCheckSample() {

        Object obj = "S";
        switch (obj) {
            case Integer t -> System.out.println("Object is Integer");
            case String s -> System.out.println("Object is String:" + s);
            case null, default -> System.out.println("Object is null or default");
        }

    }

}
