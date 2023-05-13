package lv.nixx.poc.datastruct.txn;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

public class BatchExecutorPOC {
	
	@Test
	public void testBatchExecutor() {
		
		BatchExecutor<Integer, String> batchExecutor = new BatchExecutor<>();
		// Функция, куда будует передан результат вычесления
		batchExecutor.setCallback(System.out::println);

		batchExecutor.add(t-> t+":"+ System.currentTimeMillis(), 100);
		batchExecutor.add(t-> t+"#"+ System.currentTimeMillis(), 200);
		batchExecutor.add(t-> t+"$"+ System.currentTimeMillis(), 200);
		
		batchExecutor.executeAll();
	}

	
	static class BatchExecutor <T, U> {
		
		Set<Container<T,U>> container = new HashSet<>();
		Consumer<U> callback;
		
		public void setCallback(Consumer<U> callback) {
			this.callback = callback;
		}
		
		public void add(Function<T, U> funct, T data) {
			container.add(new Container<>(funct, data));
		}

		public void executeAll() {
			container.forEach(t -> callback.accept( t.execute() )
			);
		}
		
	}
	
	static class Container <T, U>{

		private final Function<T, U> function;
		private final T data;
		
		Container(Function<T, U> function, T data) {
			this.function = function;
			this.data = data;
		}

		public U execute() {
			return function.apply(data);
		}
	}
	
}
