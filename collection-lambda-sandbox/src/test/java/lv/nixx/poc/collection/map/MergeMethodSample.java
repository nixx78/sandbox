package lv.nixx.poc.collection.map;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MergeMethodSample {

    @Test
    void mergeSandbox() {

        String keyOne = "k1";

        Map<String, Wrapper> map = new HashMap<>();
        map.put(keyOne, new Wrapper("body", LocalDateTime.parse("2023-04-23T12:00:01")));

        Wrapper updatedValue = new Wrapper("body.updated", LocalDateTime.parse("2023-04-23T13:00:01"));

        map.merge(keyOne, updatedValue, (existing, newValue) -> new Wrapper(newValue.body, existing.timestamp));

        Wrapper v1 = map.get(keyOne);
        assertAll(
                () -> assertEquals("body.updated", v1.body),
                () -> assertEquals(LocalDateTime.parse("2023-04-23T12:00:01"), v1.timestamp)
        );

        Wrapper notExistingKey = map.merge("notExistingKey", updatedValue, (existing, newValue) -> new Wrapper(newValue.body, existing.timestamp));
        System.out.println(notExistingKey);

        System.out.println(map);
    }

    @RequiredArgsConstructor
    @ToString
    static class Wrapper {
        private final String body;
        private final LocalDateTime timestamp;
    }


}

