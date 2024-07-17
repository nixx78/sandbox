package lv.nixx.poc.collection.map;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class DuplicateKeySample {

    @Test
    public void addDuplicateKeySample() {

        Map<Key, String> m = new HashMap<>();

        Key key1 = new Key("key1");
        Key key2 = new Key("key2");

        m.put(key1, "Value1");
        m.put(key2, "Value2");

        key1.setValue("key2");

        System.out.println(m);
        System.out.println(m.get(key1));
    }

    @Data
    @AllArgsConstructor
    static class Key {
        private String value;
    }

}
