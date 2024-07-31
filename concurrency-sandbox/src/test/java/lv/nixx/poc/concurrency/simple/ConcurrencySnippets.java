package lv.nixx.poc.concurrency.simple;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConcurrencySnippets {

    @Test
    void countDownLatchSample() throws InterruptedException {

        CountDownLatch cd = new CountDownLatch(3);

        IntStream.of(1000, 100, 200).mapToObj(t -> (Runnable) () -> {
                            final String tn = Thread.currentThread().getName();
                            try {
                                System.out.println("Thread [" + tn + "] will sleep [" + t + "]");
                                Thread.sleep(t);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            cd.countDown();
                            System.out.println("Thread [" + tn + "] done");
                        }
                ).map(Thread::new)
                .forEach(Thread::start);

        System.out.println("Waiting for all treads..");

        cd.await(2, TimeUnit.SECONDS);
        System.out.println("All threads are ready");

    }

    @Test
    void executorSampleWithAwaitTermination() throws InterruptedException {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            String threadName = Thread.currentThread().getName();
            System.out.println("HelloStarted:" + threadName);

            try {
                MILLISECONDS.sleep(20000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.printf("Hello processed in thread %s", threadName);
        });
        boolean b = executor.awaitTermination(1, SECONDS);
        System.out.println("Await termination:" + b);
    }

    @Test
    void callableSample() throws Exception {

        ExecutorService exec = Executors.newFixedThreadPool(10);

        Collection<Callable<String>> lst = List.of(
                new Printer(1),
                new Printer(2),
                new Printer(3),
                new Printer(4));

        Collection<Future<String>> futures = exec.invokeAll(lst);

        System.out.println("=== Features: order is equals to callable list order ===");
        for (Future<String> f : futures) {
            if (f.isDone()) {
                System.out.println(f.get(1, SECONDS));
            }
        }

    }

    @Test
    void scheduledExecutorTest() throws InterruptedException {

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        Runnable task = () -> System.out.println("Scheduling: " + System.nanoTime());

        executor.scheduleAtFixedRate(task, 0, 1, SECONDS);
        executor.awaitTermination(5, SECONDS);
    }

    @Test
    void semaphoreUsageSample() {
        Semaphore s = new Semaphore(3);

        assertTrue(s.tryAcquire());
        assertTrue(s.tryAcquire());
        assertTrue(s.tryAcquire());

        assertFalse(s.tryAcquire());
        assertFalse(s.tryAcquire());

        s.release();
        assertFalse(s.tryAcquire(3));
        assertTrue(s.tryAcquire(1));
    }

    static class Printer implements Callable<String> {
        int seqLength;

        Printer(int seqLength) {
            this.seqLength = seqLength;
        }

        @Override
        public String call() {
            String s = Thread.currentThread().getName() + ":" + new String(new char[seqLength]).replace("\0", "*");
            System.out.println("Call:" + s);
            return s;
        }
    }

}
