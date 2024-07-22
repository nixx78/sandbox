package lv.nixx.poc.collection;

import lv.nixx.poc.collection.domain.Transaction;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

import static java.util.Collections.disjoint;
import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.*;

class CollectionPlayground {

    @Test
    void effectiveFinal() {
        int[] numbers = {1, 2, 20, 40, 77};
        int threshold = 10;
		/* If we try to set: threshold = 20; 
		We get compilation error: 
		"Local variable threshold defined in an enclosing scope must be final or effectively final"
		*/
        final long count = Arrays.stream(numbers).filter(t -> t > threshold).count();
        assertEquals(3, count);
    }

    @Test
    void putTest() {
        Map<String, Object> map = new HashMap<>();
        String key = "key";
        String key1 = "key1";

        map.put(key, new Object());

        int hc1 = map.get(key).hashCode();
        map.put(key, new Object());
        int hc2 = map.get(key).hashCode();

        assertNotSame(hc1, hc2);

        Object v = new Object();
        map.put(key1, v);

        hc1 = map.get(key1).hashCode();
        map.put(key1, v);
        hc2 = map.get(key1).hashCode();

        assertEquals(hc1, hc2);
    }

    @Test
    void listTraverse() {
        List<String> lst = new ArrayList<>(Arrays.asList("10", "20", null, "30", "40"));

        lst.removeIf(s -> s != null && s.equals("30"));
        assertEquals(Arrays.asList("10", "20", null, "40"), lst);
    }

    @Test
    void linkedListVsArrayList() {
        long st = System.currentTimeMillis();
        final int iterationCount = 100000;

        ArrayList<String> c1 = new ArrayList<>();
        for (int i = 0; i < iterationCount; i++) {
            c1.add(0, String.valueOf(i));
        }

        System.out.println("ArrayList time: " + (System.currentTimeMillis() - st));
        st = System.currentTimeMillis();

        LinkedList<String> c2 = new LinkedList<>();
        for (int i = 0; i < iterationCount; i++) {
            c2.add(0, String.valueOf(i));
        }
        System.out.println("LinkedList time: " + (System.currentTimeMillis() - st));

        st = System.currentTimeMillis();
        LinkedHashSet<String> s1 = new LinkedHashSet<>();
        for (int i = 0; i < iterationCount; i++) {
            s1.add(String.valueOf(i));
        }
        System.out.println("HashSet time: " + (System.currentTimeMillis() - st));
    }

    @Test
    void mapTest() {

        ConcurrentHashMap<String, ReentrantReadWriteLock> m = new ConcurrentHashMap<>();
        ReentrantReadWriteLock lock = m.computeIfAbsent("cusip.settleDate", t -> new ReentrantReadWriteLock());
        try {
            lock.readLock().lock();
            System.out.println(lock.getReadHoldCount());
        } finally {
            lock.readLock().unlock();
            System.out.println(lock.getReadHoldCount());
        }
    }



    @Test
    void linkedHashMap() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("key1", "value1");
        map.put(null, null);
        map.put(null, "nullValue");
        map.put("key3", "value3");
        map.put("key4", "value4");
        // тут тоже все хорошо
        assertEquals(4, map.size());

        map.entrySet().forEach(System.out::println);
    }

    @Test
    void addNullToTreeMap() {
        Map<String, String> map = new TreeMap<>();
        map.put("key1", "value1");

        assertThrows(NullPointerException.class, () -> map.put(null, "nullValue"));
    }


    @Test
    void collectionDisjoint() {
        // Коллекции должны быть отсортированы
        // Возвращает true - если нет общих элементов
        assertAll(
                () -> assertTrue(disjoint(List.of("1", "2", "3"), List.of("4", "5", "6")), "Collections not contains common elements"),
                () -> assertFalse(disjoint(List.of("1", "2", "3"), List.of("1", "2", "4", "5", "6")), "Collections contains common elements"),
                () -> assertTrue(disjoint(emptyList(), List.of("4", "5", "6")), "Collections not contains common elements"),
                () -> assertTrue(disjoint(List.of("1", "2", "3"), emptyList()), "Collections not contains common elements")
        );
    }

    @Test
    void arrayStreamProcessing() {
        int[] intArray = new int[]{5, 99, 60, 12, 7, 5, 100, 777};

        final int[] res = Arrays.stream(intArray)
                .filter(t -> !(t < 10))
                .sorted()
                .toArray();

        Arrays.stream(res).forEach(System.out::println);
        assertArrayEquals(new int[]{12, 60, 99, 100, 777}, res);
    }

    @Test
    void remove() {
        Collection<String> old = List.of("1", "2", "3");
        Collection<String> changed = List.of("4", "2", "3");

        Collection<String> result = new ArrayList<>(changed);
        result.removeAll(old);
        // 4 - new element in collection
        result.forEach(System.out::println);
    }


    @Test
    void findCommonElementInCollection() {

        Collection<String> collectionOne = new ArrayList<>(List.of("1", "2", "3"));
        // common elements in two collections
        collectionOne.retainAll(List.of("4", "2", "3"));
        assertEquals(List.of("2", "3"), collectionOne);

        // try to find in different order
        collectionOne = new ArrayList<>(List.of("4", "2", "3"));
        collectionOne.retainAll(List.of("1", "2", "3"));
        assertEquals(List.of("2", "3"), collectionOne);
    }

    @Test
    void computeIfPresent() {

        Map<Integer, String> map = new HashMap<>();
        map.put(1, "One");
        map.put(2, "Two");
        map.put(3, "Three");
        map.put(4, "Four");

        System.out.println(map.computeIfPresent(2, (k, v) -> k + ":" + v + " mapped"));

        assertEquals("2:Two mapped", map.get(2));
    }

    @Test
    void computeAtomicInteger() {

        Map<String, AtomicInteger> map = new HashMap<>();
        map.put("One", new AtomicInteger(1));
        map.put("Two", new AtomicInteger(2));

        map.get("One").addAndGet(10);

        System.out.println(map);
    }


    @Test
    void createCharacterStatistic() {
        String text = "aaaBBbbCC11233546556";

        Collection<Character> collection = new ArrayList<>();
        for (Character c : text.toCharArray()) {
            collection.add(c);
        }

        final Map<Character, Long> statistic =
                collection.stream()
                        .map(Character::toLowerCase)
                        .collect(
                                Collectors.groupingBy(t -> t,
                                        Collectors.counting())
                        );

        statistic.entrySet().forEach(System.out::println);
    }

    @Test
    void createCharacterStatisticGroup() {
        String text = "aaaBBbbCC11233546556";
        Collection<Character> collection = new ArrayList<>();
        for (Character c : text.toCharArray()) {
            collection.add(c);
        }

        final Map<Group, Long> collect = collection.stream().map(Holder::new)
                .collect(
                        Collectors.groupingBy(t -> t.group,
                                Collectors.counting())
                );
        collect.entrySet().forEach(System.out::println);
    }

    @Test
    void theLongestWord_CollectMethod() {
        List<String> str = Arrays.asList("123", "1", "12", "12345");

        final Optional<String> collect = str.stream().max(Comparator.comparingInt(String::length));

        assertTrue(collect.isPresent());
        assertEquals("12345", collect.get());
    }

    @Test
    void peekTest() {

        List<String> str = Arrays.asList("10", "1", "12", "22");

        final int sum = str.stream()
                .sorted()
                .peek(System.out::println)
                .map(Integer::parseInt)
                .filter(t -> t > 10)
                .mapToInt(t -> t).sum();

        assertEquals(34, sum);

        System.out.println("Sum:" + sum);
    }

    @Test
    void listToString() {
        Collection<String> lst = Arrays.asList("1", "2", "3");
        final String c = lst.stream().collect(Collectors.joining(",", "<e>", "</e>"));
        System.out.println(c);
    }

    @Test
    void computeIfAbsent() {

        Map<String, String> map = new HashMap<>();

        String firstVal = map.computeIfAbsent("1", t -> "First.Value");
        String secondVal = map.computeIfAbsent("1", t -> "Second.Value");

        map.forEach((k, v) -> System.out.println(k + ":" + v));

        assertEquals(firstVal, secondVal);
    }




    @Test
    void initArray() {
        int[] a = new int[100];
        Arrays.parallelSetAll(a, i -> i);

        System.out.println(Arrays.toString(a));

        assertEquals(100, a.length);
    }





    @Test
    void hashedMapModification() {

        Map<String, String> map = new ConcurrentHashMap<>();
        map.put("V1", "V1");
        map.put("V2", "V2");
        map.put("V3", "V3");
        map.put("V4", "V4");

        Collection<String> values = map.values();
        for (String v : values) {
            map.remove(v);
        }

        assertEquals(0, map.size());

    }

    @Test
    void listMapping() {

        Collection<String> origs = Arrays.asList("1", "2", "3");
        Map<String, String> map = new HashMap<>();
        map.put("1", "One");
        map.put("2", "Two");

        List<String> mappedList = origs.stream()
                .map(t -> map.getOrDefault(t, null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        mappedList.forEach(System.out::println);

        assertEquals(Arrays.asList("One", "Two"), mappedList);

    }

    @Test
    void arrayBlockingQueueSample() {

        Collection<String> abq = new ArrayBlockingQueue<>(3);
        abq.add("1");
        abq.add("2");
        abq.add("3");

        IllegalStateException e = assertThrows(IllegalStateException.class, () -> abq.add("1"));
        assertEquals("Queue full", e.getMessage());
    }


    @Test
    void sortByList() {

        Map<String, Integer> positions = new HashMap<>();
        positions.put("id1", 3);
        positions.put("id2", 0);
        positions.put("id3", 1);
        positions.put("id4", 2);

        Collection<Transaction> txnSet = new HashSet<>();
        txnSet.add(new Transaction("id1", 10.10, "ACC1", "USD"));
        txnSet.add(new Transaction("id2", 20.12, "ACC2", "USD"));
        txnSet.add(new Transaction("id3", 30.13, "ACC2", "EUR"));
        txnSet.add(new Transaction("id4", 40.14, "ACC3", "EUR"));

        Transaction[] result = new Transaction[txnSet.size()];

        for (Transaction transaction : txnSet) {
            Integer index = positions.get(transaction.getId());
            result[index] = transaction;
        }

        System.out.println(Arrays.toString(result));

        assertEquals("id2", result[0].getId());
        assertEquals("id3", result[1].getId());
        assertEquals("id4", result[2].getId());
        assertEquals("id1", result[3].getId());
    }

    static class Holder {
        Group group;
        Character c;

        Holder(Character c) {
            this.c = c;
            if (Character.isLetter(c)) {
                group = Group.CHARACTER;
            } else if (Character.isDigit(c)) {
                group = Group.NUMBER;
            }
        }

        @Override
        public String toString() {
            return "Holder [group=" + group + ", c=" + c + "]";
        }

    }

    enum Group {
        CHARACTER,
        NUMBER
    }

}



