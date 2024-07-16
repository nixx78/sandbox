package lv.nixx.poc.concurrency.simple;

public class ProcessorAsRunnable implements Runnable {

    private final String value;

    public ProcessorAsRunnable(String value) {
        this.value = value;
    }

    @Override
    public void run() {
        System.out.println("Thread:" + Thread.currentThread().getName() + ": process" + value);
    }

}
