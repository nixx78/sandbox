package lv.nixx.poc.datastruct.delayedqueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;

public class DelayQueueTest {

	public static void main(String[] args) {

		final BlockingQueue<Email> queue = new DelayQueue<>();

		new DelayQueueProducer(queue).start();
		new DelayQueueConsumer(queue).start();

	}
	
}