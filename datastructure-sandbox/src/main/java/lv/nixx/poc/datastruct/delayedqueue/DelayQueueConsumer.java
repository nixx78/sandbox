package lv.nixx.poc.datastruct.delayedqueue;

import java.util.concurrent.BlockingQueue;

public class DelayQueueConsumer {

	private final BlockingQueue<Email> queue;

	public DelayQueueConsumer(BlockingQueue<Email> queue) {
		this.queue = queue;
	}

	public void start() {
		
		Thread consumerThread = new Thread(() -> {
			String tname = Thread.currentThread().getName();
			while (true) {
				try {
					Email email = queue.take();
					System.out.printf("[%s] - Sending mail when delay is over = %s%n", tname, email);
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}, "Consumer Thread-1");

		consumerThread.start();
	}

}