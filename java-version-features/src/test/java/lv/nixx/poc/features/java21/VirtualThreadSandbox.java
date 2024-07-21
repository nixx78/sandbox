package lv.nixx.poc.features.java21;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class VirtualThreadSandbox {

    @Test
    void createAndExecuteVirtualThread() {

        Collection<Callable<String>> tasks = IntStream.range(0, 5).mapToObj(
                t -> (Callable<String>) () -> "Processed:" + t
        ).collect(Collectors.toCollection(ArrayList::new));

        try (var es = Executors.newVirtualThreadPerTaskExecutor()) {

            List<Future<String>> futures = es.invokeAll(tasks);

            List<String> responses = futures.stream()
                    .map(t -> {
                        try {
                            return t.get();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        } catch (ExecutionException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .toList();

            System.out.println(responses);

        } catch (Exception e) {
            System.err.println(e);
        }

    }

}
