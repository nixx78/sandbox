package lv.nixx.poc.datastruct.delayedqueue;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class Email implements Delayed {

	private final String receipient;
	private final String mailBody;
	private final long startTime;

	public Email(String receipient, String body, long delay) {
		this.receipient = receipient;
		this.mailBody = body;
		this.startTime = System.currentTimeMillis() + delay;
	}

	@Override
	public long getDelay(TimeUnit unit) {
		return unit.convert(this.startTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
	}

	@Override
	public int compareTo(Delayed o) {
		return Long.compare(this.startTime, ((Email) o).startTime);
	}

	@Override
	public String toString() {
		return "Email [receipient=" + receipient + ", mailBody=" + mailBody + ", startTime=" + startTime + "]";
	}
}