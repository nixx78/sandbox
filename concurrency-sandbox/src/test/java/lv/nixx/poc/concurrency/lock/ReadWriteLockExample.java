package lv.nixx.poc.concurrency.lock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/*
ReentrantReadWriteLock Используется для разделения доступа между операциями чтения и записи.
Несколько потоков могут читать ресурс одновременно, так как доступ для чтения не блокируется, если нет операций записи.
Когда один поток записывает данные, все другие потоки (как читающие, так и записывающие) блокируются до завершения операции записи.
В данном примере один поток пишет данные, а два других параллельно читают.
Данных подход удобно использовать, когда операций чтения больше, чем записи, поскольку он увеличивает производительность за счет уменьшения блокировок в операциях чтения.
 */

public class ReadWriteLockExample {

    private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private final ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();
    private final ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();

    private int sharedResource = 0;

    public void readResource() {
        readLock.lock();
        try {
            System.out.println("Thread '" + Thread.currentThread().getName() + "' read from shared resource: " + sharedResource);
        } finally {
            readLock.unlock();
        }
    }

    public void writeResource(int value) {
        writeLock.lock();
        try {
            System.out.println("Thread '" + Thread.currentThread().getName() + "' write to shared resource: " + value);
            sharedResource = value;
        } finally {
            writeLock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReadWriteLockExample example = new ReadWriteLockExample();

        Runnable writer = () -> {
            for (int i = 0; i < 5; i++) {
                example.writeResource(i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };

        Runnable reader = () -> {
            for (int i = 0; i < 5; i++) {
                example.readResource();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };

        Thread writerThread = new Thread(writer, "WriterThread");
        writerThread.start();

        Thread readerThread1 = new Thread(reader, "ReaderThread1");
        Thread readerThread2 = new Thread(reader, "ReaderThread2");

        readerThread1.start();
        readerThread2.start();

        writerThread.join();
        readerThread1.join();
        readerThread2.join();

        example.readResource();
    }

}
