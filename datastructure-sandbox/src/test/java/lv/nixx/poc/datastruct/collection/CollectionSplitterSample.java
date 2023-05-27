package lv.nixx.poc.datastruct.collection;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CollectionSplitterSample {

    public static <T> List<List<T>> splitCollection(Collection<T> collection, int batchSize) {
        return IntStream.range(0, (collection.size() + batchSize - 1) / batchSize)
                .mapToObj(i -> collection.stream()
                        .skip((long) i * batchSize)
                        .limit(batchSize)
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    @Test
    void splitCollectionToBatchesAndProcess() {

        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 13; i++) {
            numbers.add(i);
        }

        List<List<Integer>> batches = splitCollection(numbers, 5);
        batches.forEach(System.out::println);

        // Process batch one by one and get combined result
        Collection<String> processedBatches = batches.stream()
                .map(this::process)
                .flatMap(Collection::stream)
                .toList();

        System.out.println(processedBatches);
    }

    private Collection<String> process(Collection<Integer> v) {
        return v.stream().map(t -> "Processed:" + t).toList();
    }

}
