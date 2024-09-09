package lv.nixx.poc.concurrency.parralel;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;

/*
Пример запуска parallelStream в poole потоков, который контролируется пользователем
 */

class ParallelStreamSandbox {


    @Test
    void parallelUsingStandartForkJoinPool() {

        System.out.println("Available CPU count: " + Runtime.getRuntime().availableProcessors());
        System.out.println("ForkJoinPool.commonPool() thread count: " + ForkJoinPool.commonPool().getParallelism());

        IntStream.range(0, 100).parallel().forEach(number -> {
            System.out.println("Thread: " + Thread.currentThread().getName() + ", number: " + number);
        });

    }

    @Test
    void parallelUsingCustomForkJoinPool() {
        System.out.println("Available CPU count: " + Runtime.getRuntime().availableProcessors());

        Collection<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        ForkJoinPool customThreadPool = new ForkJoinPool(2);

        Runnable runnable = () ->
                numbers.parallelStream().forEach(number -> {
                    System.out.println("Thread: " + Thread.currentThread().getName() + ", number: " + number);
                });

        customThreadPool.submit(runnable).join();

        customThreadPool.shutdown();
    }

}
