package lv.nixx.poc.features.java9;

import org.junit.jupiter.api.Test;

import java.io.Closeable;
import java.io.IOException;

public class TryWithResourcesTest {

	@Test
	public void java17Sample() throws IOException {
		try (Closeable c = new CloseableImpl()) {
			System.out.println("Process");
		}
	}

	@Test
	public void java19Sample() throws IOException {
		Closeable c = new CloseableImpl();
		try (c) {
			System.out.println("Process");
		}
	}

	static class CloseableImpl implements Closeable {

		@Override
		public void close() {
			System.out.println("Closeable called");
		}
		
	}
}
