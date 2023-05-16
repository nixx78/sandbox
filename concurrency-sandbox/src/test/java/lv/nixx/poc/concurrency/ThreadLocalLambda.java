package lv.nixx.poc.concurrency;

import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ThreadLocalLambda {
	
	ThreadLocal<DateFormat> df = ThreadLocal.withInitial(() -> new SimpleDateFormat("ddMMyyyy"));
	
	@Test
	public void test() {
		
		Runnable[] ra = {
				() -> {
					try {
						System.out.println(Thread.currentThread().getName() + ":" + df.get().parse("01122016"));
						synchronized (this) {
							wait(200);
						}
						System.out.println("R1 End");
					} catch (Exception e) {
						e.printStackTrace();
				}},
				() -> {
					try {
						System.out.println(Thread.currentThread().getName() + ":" + df.get().parse("02122016"));
					} catch (Exception e) {
						e.printStackTrace();
				}}
		};		
		
		List<Thread> threads = new ArrayList<>();
		
		Arrays.stream(ra).map(Thread::new).forEach(t -> {
			threads.add(t);
			t.start();
		});

		threads.forEach(t -> {
			try {
				t.join();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

	}

}
