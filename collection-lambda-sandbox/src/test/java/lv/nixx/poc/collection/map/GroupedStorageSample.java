package lv.nixx.poc.collection.map;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class GroupedStorageSample {

    @Test
    void testCrudOperations() {
        GroupedStorage<String> storage = new GroupedStorage<>();

        storage.put("g1", "e1", "A");
        storage.put("g1", "e2", "B");
        storage.put("g2", "e1", "C");

        assertAll("READ: full key",
                () -> assertEquals("A", storage.get("g1", "e1")),
                () -> assertEquals("B", storage.get("g1", "e2")),
                () -> assertEquals("C", storage.get("g2", "e1")),
                () -> assertNull(storage.get("g1", "xxx"))
        );

        assertAll("READ: by group",
                () -> assertThat(storage.getByGroup("g1")).containsExactly("A", "B"),
                () -> assertThat(storage.getByGroup("g2")).containsExactly("C")
        );

        // UPDATE the same as add
        storage.put("g1", "e1", "A2");
        assertEquals("A2", storage.get("g1", "e1"));

        // DELETE: by full key
        String removed = storage.remove("g1", "e2");
        assertEquals("B", removed);

        Collection<String> g1 = storage.getByGroup("g1");
        assertThat(g1).containsExactly("A2");

        // DELETE: delete by group
        var removedGroup = storage.removeGroup("g2");
        assertEquals(1, removedGroup.size());

        assertAll(
                () -> assertNull(storage.get("g2", "e1")),
                () -> assertTrue(storage.getByGroup("g2").isEmpty())
        );

    }

    static class GroupedStorage<T> {

        private final Map<String, Map<String, T>> storage = new ConcurrentHashMap<>();

        public void put(String groupId, String entityId, T value) {
            storage
                    .computeIfAbsent(groupId, g -> new HashMap<>())
                    .put(entityId, value);
        }

        public T get(String groupId, String entityId) {
            return storage
                    .getOrDefault(groupId, Map.of())
                    .get(entityId);
        }

        public Collection<T> getByGroup(String groupId) {
            return storage
                    .getOrDefault(groupId, Map.of())
                    .values();
        }

        public T remove(String groupId, String entityId) {
            Map<String, T> map = storage.get(groupId);
            if (map == null) return null;

            T removed = map.remove(entityId);

            if (map.isEmpty()) {
                storage.remove(groupId);
            }

            return removed;
        }

        public Map<String, T> removeGroup(String groupId) {
            return storage.remove(groupId);
        }
    }

}
