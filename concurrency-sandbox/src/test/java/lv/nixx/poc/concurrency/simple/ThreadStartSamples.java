package lv.nixx.poc.concurrency.simple;

import org.junit.jupiter.api.Test;

class ThreadStartSamples {

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


}
