package lv.nixx.poc.concurrency;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class CyclicBarrierSandbox {

    CyclicBarrier sb = new CyclicBarrier(3, new FinalizationThread());
    Collection<String> data = Collections.synchronizedList(new ArrayList<>());

    @Test
    void run() throws InterruptedException {

        IntStream.of(1000, 100, 200, 0, 1000, 20, 90)
                .mapToObj(t -> new DataInsertThread(t, data, sb))
                .forEach(t -> new Thread(t).start());

        TimeUnit.SECONDS.sleep(10);


    }

    record DataInsertThread(int sleepTime, Collection<String> data, CyclicBarrier sb) implements Runnable {
        @Override
        public void run() {
            String tName = Thread.currentThread().getName();

            try {
                System.out.println("+ Thread: " + tName + " started, sleepTime:"
                        + sleepTime + " numberWaiting: " + sb.getNumberWaiting());

                TimeUnit.MILLISECONDS.sleep(sleepTime);
                data.add(tName + ".value");

                System.out.println(" - Thread: " + tName + " data added");


                sb.await();
            } catch (InterruptedException | BrokenBarrierException ex) {
                System.err.println("Error in thread: " + tName + " sleepTime: " + sleepTime);
            }
        }
    }

    class FinalizationThread implements Runnable {

        @Override
        public void run() {
            System.out.println("=== Finalization in thread:" + Thread.currentThread().getName() + " ===");
            System.out.println("Data, size: " + data.size() + ":" + data.stream()
                    .sorted()
                    .toList());
            System.out.println("======");
        }
    }


}
