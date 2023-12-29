package lv.nixx.poc.datastruct.delayedqueue;

import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.DelayQueue;

public class DelayQueueSandbox {

    @Test
    public void test() throws InterruptedException {

        Email e2 = new Email("receiver2", "Body2", 1500);
        Email e3 = new Email("receiver3", "Body3", 1000);
        Email e1 = new Email("receiver1", "Body1", 500);

        DelayQueue<Email> q = new DelayQueue<>(Arrays.asList(e1, e2, e3));
        q.offer(new Email("receiver4", "Body4", 200));

        System.out.println("DelayQueue filled");

        // Сообщения извлекаются в порядке задержки
        while ( !q.isEmpty()) {
            System.out.println(q.take());
        }

    }

}
