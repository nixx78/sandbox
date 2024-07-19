package lv.nixx.poc.features.java9;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

public class Java9StreamTest {
	
	@Test
	public void stream() {
		Stream.of(1,2,3,4,5,6,7,8,9,10).takeWhile(i -> i < 5)
        .forEach(System.out::println);
		System.out.println("====================");
		
		List.of(7,7,7,0,0,0).stream().takeWhile(i -> i == 7)
        .forEach(System.out::println);
	}

}
