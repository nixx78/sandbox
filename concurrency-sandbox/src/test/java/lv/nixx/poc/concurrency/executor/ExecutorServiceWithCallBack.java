package lv.nixx.poc.concurrency.executor;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Stream;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

public class ExecutorServiceWithCallBack {

    @Test
    void executeTasksWithCallbackInvokeAll() {
        ExecutorService pool = Executors.newFixedThreadPool(3);

        Collection<String> responseHolder = Collections.synchronizedList(new ArrayList<>());

        List<Runner> runners = Stream.of("R1", "R2", "R3", "R4", "R5")
                .map(t -> new Runner(t, responseHolder))
                .toList();

        try {
            List<Future<String>> futures = pool.invokeAll(runners, 100, MILLISECONDS);
            System.out.println("Futures result");
            futures.forEach(System.out::println);

        } catch (InterruptedException e) {
            System.err.println(e);
        }

        System.out.println("Data from response holder");
        responseHolder.forEach(System.out::println);

        pool.shutdown();
    }


    static class Runner implements Callable<String> {

        private final String request;
        private final Collection<String> responseHolder;

        public Runner(String request, Collection<String> responseHolder) {
            this.request = request;
            this.responseHolder = responseHolder;
        }

        @Override
        public String call() throws InterruptedException {
            if (request.equalsIgnoreCase("R3") || request.equalsIgnoreCase("R5")) {
                SECONDS.sleep(2);
            }
            String r = "processed:" + request;
            responseHolder.add(r);
            return r;
        }
    }


}
