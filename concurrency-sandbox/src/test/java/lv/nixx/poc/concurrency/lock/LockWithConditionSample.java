package lv.nixx.poc.concurrency.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class LockWithConditionSample {

    public static void main(String[] args) {

        ClassWithCondition example = new ClassWithCondition();

        Thread firstWaitingThread = new Thread(() -> {
            try {
                example.waitForCondition();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }, "WaitingThread-1");

        Thread secondWaitingThread = new Thread(() -> {
            try {
                example.waitForCondition();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }, "WaitingThread-2");

        // Поток, который сигнализирует об выполнении условия
        Thread signalingThread = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
                example.signalCondition();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }, "SignalingThread");

        firstWaitingThread.start();
        secondWaitingThread.start();
        signalingThread.start();

        try {
            firstWaitingThread.join();
            secondWaitingThread.join();
            signalingThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }

    static class ClassWithCondition {
        private final ReentrantLock lock = new ReentrantLock();
        private final Condition condition = lock.newCondition();

        private boolean conditionMet = false;

        // Метод, который ждет выполнения условия
        public void waitForCondition() throws InterruptedException {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + " : Wait for condition met");
                while (!conditionMet) {
                    condition.await();
                }
                System.out.println(Thread.currentThread().getName() + " : Condition met, continue");
            } finally {
                lock.unlock();
            }
        }

        // Метод, который сигнализирует о выполнении условия
        public void signalCondition() {
            lock.lock();
            try {
                conditionMet = true; // Устанавливаем условие
                condition.signalAll(); // Уведомляем ожидающие потоки
                System.out.println(Thread.currentThread().getName() + " : Send signal to all");
            } finally {
                lock.unlock();
            }
        }

    }
}
