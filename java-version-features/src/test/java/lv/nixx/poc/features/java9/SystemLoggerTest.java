package lv.nixx.poc.features.java9;

import org.junit.jupiter.api.Test;

import static java.lang.System.Logger;

class SystemLoggerTest {

    private static final Logger LOGGER = System.getLogger("TestLogger");

    @Test
    void logTest() {

        LOGGER.log(Logger.Level.ERROR, "Error message", new Exception("My Exception"));

    }
}
