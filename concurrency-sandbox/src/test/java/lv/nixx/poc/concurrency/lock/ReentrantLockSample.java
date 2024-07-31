package lv.nixx.poc.concurrency.lock;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ReentrantLockSample {

    /*
    Пример синхронизации методов используя объект Lock
     */


    @Test
    void test() {
        ExecutorService executorService = Executors.newFixedThreadPool(4);

        CounterWithLock counter = new CounterWithLock();

        executorService.submit(counter::increment);
        executorService.submit(counter::increment);
        executorService.submit(counter::increment);
        executorService.submit(counter::increment);

        try {
            if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }

        System.out.println(counter.getCount());
    }


    static class CounterWithLock {
        private int count = 0;
        private final Lock lock = new ReentrantLock();

        public void increment() {
            lock.lock(); // Захват mutex
            try {
                System.out.println("Count++ by: " + Thread.currentThread().getName());
                TimeUnit.MILLISECONDS.sleep(500);
                count++;
                System.out.println("Count++ by: " + Thread.currentThread().getName() + " result:" + count);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock(); // Освобождение mutex
            }
        }

        public int getCount() {
            lock.lock();
            try {
                return count;
            } finally {
                lock.unlock();
            }
        }
    }


}
