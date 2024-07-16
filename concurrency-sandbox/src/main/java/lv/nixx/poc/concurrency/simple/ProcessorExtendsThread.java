package lv.nixx.poc.concurrency.simple;

import java.util.concurrent.TimeUnit;

public class ProcessorExtendsThread extends Thread {

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
