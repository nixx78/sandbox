package lv.nixx.poc.concurrency.simple;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

class ThreadStartExamples {

    @Test
    void startRunnableSample() {

        Runnable r1 = new ProcessorAsRunnable("v1");
        Runnable r2 = new ProcessorAsRunnable("v2");

        new Thread(r1, "thread1").start();
        new Thread(r2, "thread2").start();
    }

    @Test
    void startThreadSample() throws InterruptedException {
        ProcessorExtendsThread v1t = new ProcessorExtendsThread("v1t");
        ProcessorExtendsThread v2t = new ProcessorExtendsThread("v2t");

        v1t.start();
        v2t.start();

        v1t.join();
        v2t.join();

        System.out.println("Both thread finished, current thread [" + Thread.currentThread().getName() + "]");
    }


    static class ProcessorExtendsThread extends Thread {

        private final String value;

        public ProcessorExtendsThread(String value) {
            this.value = value;
        }

        @Override
        public void run() {
            try {
                TimeUnit.MILLISECONDS.sleep(500);
                System.out.println("Thread:" + Thread.currentThread().getName() + ": process" + value);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

    static class ProcessorAsRunnable implements Runnable {

        private final String value;

        public ProcessorAsRunnable(String value) {
            this.value = value;
        }

        @Override
        public void run() {
            System.out.println("Thread:" + Thread.currentThread().getName() + ": process" + value);
        }

    }
}
