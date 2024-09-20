package lv.nixx.poc.collection.collection;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SplitCollectionSample {

    public static <T> List<List<T>> splitCollection(Collection<T> collection, int batchSize) {
        return IntStream.range(0, (collection.size() + batchSize - 1) / batchSize)
                .mapToObj(i -> collection.stream()
                        .skip((long) i * batchSize)
                        .limit(batchSize)
                        .collect(Collectors.toList()))
                .toList();
    }

    @Test
    void splitCollectionToBatchesAndProcess() {

        List<Integer> numbers = IntStream.range(1, 13).boxed().toList();

        List<List<Integer>> batches = splitCollection(numbers, 5);

        assertEquals(3, batches.size());

        System.out.println("Batches after split:");
        batches.forEach(System.out::println);

        // Process batch one by one and get combined result
        Collection<String> processedBatches = batches.stream()
                .map(v -> v.stream().map(t -> "Processed:" + t).toList())
                .flatMap(Collection::stream)
                .toList();

        System.out.println("Batches after flatMap & Process:");
        System.out.println(processedBatches);
    }

    @Test
    void collectionOfStringSplitTest() {
        List<List<String>> batches = splitCollection(List.of("V1", "V2", "V3", "V4", "V5", "V6", "V7", "V8", "V9", "V10"), 3);

        System.out.println("Batches after split:");
        batches.forEach(System.out::println);
    }

    @Test
    void splitUsingGroupingBySample() {

        var size = 3;
        List<String> listToSplit = List.of("V1", "V2", "V3", "V4", "V5", "V6", "V7", "V8", "V9", "V10");

        final AtomicInteger counter = new AtomicInteger();

        List<List<String>> groups = new ArrayList<>(
                listToSplit.stream()
                        .collect(Collectors.groupingBy(it -> counter.getAndIncrement() / size))
                        .values());

        System.out.println("Batches after split using groupBy");
        groups.forEach(System.out::println);
    }


}
