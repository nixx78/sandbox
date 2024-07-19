package lv.nixx.poc.features.java9;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static java.util.Map.entry;

public class Java9SandboxTest {
	
	@Test
	public void listOf() {
		final List<String> lst = List.of("1","2","3","4");
		lst.forEach(System.out::println);
 	}
	
	@Test
	public void mapOf() {
		Map<Integer, String> map = Map.of(1, "one", 2, "two", 3, "three");
		map.entrySet().forEach(System.out::println);
		
		map = Map.ofEntries(entry(1,"one"), entry(2, "two"), entry(3, "three"), entry(4, "four"));

		map.entrySet().forEach(System.out::println);
	}
	
	@Test
	public void iterate() {
		IntStream.iterate(1, i -> i < 10, i -> i + 1).forEach(System.out::println);
	}
	

}
