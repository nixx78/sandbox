package lv.nixx.poc.features.java15.npe;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class NpeTest {

    @Test
    public void npeWithDescriptionSample() {
        Customer c = null;
        NullPointerException npe = assertThrows(NullPointerException.class, ()-> c.getName());
        assertEquals("Cannot invoke \"lv.nixx.poc.features.java15.npe.NpeTest$Customer.getName()\" because \"c\" is null",
                npe.getMessage());
    }

    @Test
    public void npeInCallChainSample() {
        NullPointerException npe = assertThrows(NullPointerException.class, () -> getCustomerInstance().getName().length());
        assertEquals("Cannot invoke \"String.length()\" because the return value of \"lv.nixx.poc.features.java15.npe.NpeTest$Customer.getName()\" is null",
                npe.getMessage());
    }


    private Customer getCustomerInstance() {
        return new Customer();
    }

    static class Customer {
        String getName() {
            return null;
        }
    }

}


