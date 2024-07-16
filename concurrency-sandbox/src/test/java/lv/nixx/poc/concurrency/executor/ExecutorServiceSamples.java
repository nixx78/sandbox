package lv.nixx.poc.concurrency.executor;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Supplier;

class ExecutorServiceSamples {

    // Лучше установить для пула потоков свое имя, это помогает при DEBUG / Support
    final ThreadFactory threadFactory = new ThreadFactoryBuilder()
            .setNameFormat("MyRequestPool#%d")
            .setDaemon(true)
            .build();

    private final ExecutorService pool = Executors.newFixedThreadPool(3, threadFactory);

    private final List<Request> requestsToExecute = List.of(
            new Request("req1", "request1", 0L),
            new Request("req2", "request2", 100L),
            new Request("req3", "request3", 1000L),
            new Request("req4", "request4", 10L),
            new Request("req5", "request5", 30L),
            new Request("req6", "request6", 50L)
    );

    @Test
    void runBatchInThreadPool() {

        int numOfCores = Runtime.getRuntime().availableProcessors();
        System.out.println("Number of cores: " + numOfCores);

        List<Future<String>> futures = requestsToExecute.stream()
                .map(pool::submit)
                .toList();

        Collection<String> responses = new ArrayList<>(requestsToExecute.size());
        futures.forEach(
                f -> {
                    try {
                        responses.add(f.get(1000L, TimeUnit.MILLISECONDS));
                    } catch (InterruptedException | RuntimeException | ExecutionException | TimeoutException e) {
                        throw new RuntimeException(e);
                    }
                }
        );

        System.out.println("Batch response:");
        responses.forEach(System.out::println);
    }

    @Test
    void invokeAllSample() throws InterruptedException {

        List<Future<String>> futures = pool.invokeAll(requestsToExecute, 500, TimeUnit.MILLISECONDS);

        Collection<String> responses = new ArrayList<>(requestsToExecute.size());
        futures.forEach(
                f -> {
                    try {
                        if (f.isDone()) {
                            responses.add(f.get(10L, TimeUnit.MILLISECONDS));
                        } else {
                            System.err.println("Future: " + f + " not done");
                        }

                    } catch (InterruptedException | CancellationException | ExecutionException | TimeoutException e) {
                        System.err.println("Exception during get:" + e);
                        e.printStackTrace();
                    }
                }
        );

        System.out.println("Batch response:");
        responses.forEach(System.out::println);
    }

    @Test
    void invokeAnySample() {
        try {
            // Получаем один результат, первого выполненого потока, остальные запросы - отменяются
            String s = pool.invokeAny(requestsToExecute);
            System.out.println("InvokeAny result:" + s);
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error during 'InvokeAny' " + e);
        }
    }

    @Test
    void shutdownNowSample() {
        requestsToExecute.forEach(pool::submit);
        List<Runnable> notExecuted = pool.shutdownNow();

        System.out.println("Not executed tasks:");
        notExecuted.forEach(System.out::println);
    }

    @Test
    public void completableFutureTest() throws ExecutionException, InterruptedException {

        Supplier<String> requestSupplier = () -> {
            try {
                return requestsToExecute.get(0).call();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };

        CompletableFuture<String> requestCompletableFuture = CompletableFuture.supplyAsync(requestSupplier, pool);
        String response = requestCompletableFuture.get();

        System.out.println("Response from Thread:" + response);
    }

    @RequiredArgsConstructor
    static class Request implements Callable<String> {

        private static final Logger log = LoggerFactory.getLogger(ExecutorServiceSamples.class);

        private final String id;

        private final String request;
        private final long delay;

        @Override
        public String call() throws Exception {
            final Thread currentThread = Thread.currentThread();
            final String oldName = currentThread.getName();

            String prefix = oldName.substring(0, oldName.indexOf("#"));
            currentThread.setName(prefix + "#Process-" + id);
            try {
                long stTime = System.currentTimeMillis();
                TimeUnit.MILLISECONDS.sleep(delay);
                String s = "[" + request + "] processed by Thread:" + Thread.currentThread().getName() + " time:" + (System.currentTimeMillis() - stTime);
                log.info("Call: {}", s);
                return s;
            } finally {
                currentThread.setName(oldName);
            }
        }
    }


}
