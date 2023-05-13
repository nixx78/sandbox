package lv.nixx.poc.datastruct.binarysearch;

import java.util.*;

public class BinarySearcher {

	private BinarySearcher() {
	}
	
	public static Person searchById(List<Person> c, Integer searchId) {

		c.sort(Comparator.comparingInt(p -> p.id));

		while(true) {
			int middle = c.size() / 2;
			final Person cp = c.get(middle);
			
			if (cp.id == searchId) {
				return cp;
			} else if ( middle == 0) {
				return null;
			}
			c = searchId < cp.id  ? c.subList(0, middle) : c.subList(middle, c.size());
		}
	}
	

}
