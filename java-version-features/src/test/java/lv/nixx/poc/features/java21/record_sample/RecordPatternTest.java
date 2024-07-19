package lv.nixx.poc.features.java21.record_sample;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RecordPatternTest {


    @Test
    void recordTest() {

        Customer cust = new Customer("n1", "s1");

        assertAll(
                () -> assertEquals("n1:s1", beforeRecordPattern(cust)),
                () -> assertEquals("n1:s1", afterRecordPattern(new Customer("n1", "s1")))
        );
    }

    @Test
    void getDirectly() {
        Object o = new AdvancedCustomer(new Customer("n1", "s1"));

        if (o instanceof AdvancedCustomer(Customer cp)) {
            System.out.println(cp.info());
        }

    }


    public static String beforeRecordPattern(Object obj) {
        if (obj instanceof Customer p) {
            return p.info();
        }

        return null;
    }

    public static String afterRecordPattern(Object obj) {
        if (obj instanceof Customer(String name, String surname)) {
            return name + ":" + surname;
        }
        return null;
    }


    record Customer(String name, String surname) {
        String info() {
            return name + ":" + surname;
        }
    }

    record AdvancedCustomer(Customer c) {
    }

}
