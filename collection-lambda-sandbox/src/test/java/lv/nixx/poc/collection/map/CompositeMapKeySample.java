package lv.nixx.poc.collection.map;

import org.apache.commons.collections4.keyvalue.MultiKey;
import org.apache.commons.collections4.map.MultiKeyMap;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class CompositeMapKeySample {
	
	
	@Test
	public void multiKeyMapSample() {
		
		MultiKeyMap<Long, String> map = new MultiKeyMap<>();
		
		map.put(1L, 2L, "String12");
		map.put(3L, 4L, "String34");
		map.put(new MultiKey<>(new Long[] {1L, 2L, 3L}), "123");

		assertEquals("String12", map.get(1L, 2L));
		assertEquals("123", map.get(1L, 2L, 3L));
	}
	
	@Test
	public void listAsKeySample() {
		
		Map<List<String>, String> map = new HashMap<>();
		
		map.put(Arrays.asList("1", "2", "3"), "123");
		map.put(Arrays.asList("3", "2", "1"), "321");
		map.put(Arrays.asList("1", "2", "4"), "124");
		
		assertEquals("123", map.get(Arrays.asList("1", "2", "3")));
		assertEquals("321", map.get(Arrays.asList("3", "2", "1")));
		assertNull(map.get(Arrays.asList("1", "2")));
	}
	

		

}
