package lv.nixx.poc.concurrency.future;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class CompletableFutureSandbox {
	
	final static Map<String, String> users = new ConcurrentHashMap<>();

	@BeforeAll
	static void init() {
		users.put("id1", "id1:name1");
		users.put("id2", "id2:name2");
		users.put("id3", "id3:name3");
		users.put("id4", "id4:name4");
		users.put("id5", "id5:name5");
		users.put("id6", "id6:name6");
	}

	@Test
	public void helloFutureTest() throws Exception {
		CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "Hello").thenApply(s -> s + " World");

		assertEquals("Hello World", completableFuture.get());
	}

	@Test
	void createCompleteFuture() throws Exception {

		String userId = "id1";

		final CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> getUserInfo(userId))
				.thenApply(this::convert)
				.thenApply(this::convertResult)
				.exceptionally(this::processException);

		assertEquals("id1:name1", cf.get());

	}
	
	@Test
	void combineFuturesTest() throws Exception {
		
		CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "Hello")
		    .thenCompose(s -> CompletableFuture.supplyAsync(() -> s + " World"));
		 
		assertEquals("Hello World", completableFuture.get());
	}

	@Test
	void completeFutureWithException() {
		CompletableFuture<String> thenApply = CompletableFuture.supplyAsync(() -> getUserInfo("ERROR"))
		.thenApply(this::convert)
		.thenApply(this::convertResult);

		ExecutionException ex = assertThrows(ExecutionException.class, thenApply::get);
		assertEquals("java.lang.IllegalArgumentException: GetUserInfo error", ex.getMessage());
	}
	
	@Test
	void exceptionHandling() throws Exception {
		
		String name = null;
		
		CompletableFuture<String> completableFuture
		  =  CompletableFuture.supplyAsync(() -> {
		      if (name == null) {
		          throw new RuntimeException("Computation error!");
		      }
		      return "Hello, " + name;
		  }).handle((s, t) -> s != null ? s : t.getMessage());
		
		assertEquals("java.lang.RuntimeException: Computation error!", completableFuture.get());
		  
	}

	private String processException(Throwable e) {
		System.err.println(e);
		return null;
	}

	@Test
	public void allOfFutures() {
		Collection<CompletableFuture<User>> c = new ArrayList<>();
		final ExecutorService tp = Executors.newFixedThreadPool(3);

		for (String id : users.keySet()) {
			final CompletableFuture<User> cf = CompletableFuture.supplyAsync(() -> getUserInfo(id), tp).thenApply(this::convert);
			c.add(cf);
		}

		List<User> users = CompletableFuture.allOf(c.toArray(new CompletableFuture[0]))
				.thenApply(v -> c.stream()
				.map(CompletableFuture::join)
				.collect(Collectors.toList())).thenApply(t -> {
					
			System.out.println("====== All is done =======");
			return t;
		}).join();

		users.forEach(System.out::println);
	}

	private String getUserInfo(String id) {
		try {

			if (id.equals("ERROR")) {
				throw new IllegalArgumentException("GetUserInfo error");
			}

			final int st = ThreadLocalRandom.current().nextInt(50, 2000 + 1);
			System.out.println("Thread [" + Thread.currentThread().getName() + "] Call: send request id [" + id + "] and wait [" + st + "]");
			TimeUnit.MILLISECONDS.sleep(st);

		} catch (InterruptedException e) {
			System.err.println(e);
		}
		return users.get(id);
	}

	private String convertResult(User res) {
		System.out.println("Thread [" + Thread.currentThread().getName() + "] Result: " + res);

		return res.id + ":" + res.name;
	}

	private User convert(String body) {
		StringTokenizer st = new StringTokenizer(body, ":");
		User u = new User();
		final String id = u.id = st.nextToken();
		u.name = st.nextToken();

		System.out.println("Thread [" + Thread.currentThread().getName() + "] Id [" + id + "] convert success");
		return u;
	}

	static class User {
		String id;
		String name;

		@Override
		public String toString() {
			return "User [id=" + id + ", name=" + name + "]";
		}
	}

}
