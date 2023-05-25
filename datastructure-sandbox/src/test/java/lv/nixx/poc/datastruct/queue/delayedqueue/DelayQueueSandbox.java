package lv.nixx.poc.datastruct.queue.delayedqueue;

import com.google.common.primitives.Ints;
import lombok.ToString;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

class DelayQueueSandbox {

    @Test
    void test() throws InterruptedException {

        DelayQueue<DelayedEmail> q = new DelayQueue<>(List.of(
                new DelayedEmail("receiver1", "Body1", 500),
                new DelayedEmail("receiver2", "Body2", 2000),
                new DelayedEmail("receiver3", "Body3", 1000),
                new DelayedEmail("receiver5", "Body5", 1500)
        ));
        q.offer(new DelayedEmail("receiver4", "Body4", 200));

        System.out.println("DelayQueue filled");

        // Сообщения извлекаются в порядке задержки
        List<String> recipientIds = new ArrayList<>();
        while (!q.isEmpty()) {
            DelayedEmail em = q.take();
            System.out.println(em);
            recipientIds.add(em.recipient);
        }

        assertThat(recipientIds).isEqualTo(List.of("receiver4", "receiver1", "receiver3", "receiver5", "receiver2"));
    }


    @ToString
    static class DelayedEmail implements Delayed {

        private final String recipient;
        private final String mailBody;
        private final long startTime;

        public DelayedEmail(String recipient, String body, long delay) {
            this.recipient = recipient;
            this.mailBody = body;
            this.startTime = System.currentTimeMillis() + delay;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(this.startTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            return Ints.saturatedCast(this.startTime - ((DelayedEmail) o).startTime);
        }
    }

}
