package lv.nixx.poc.datastruct.top3;


import lv.nixx.poc.datastruct.domain.Person;
import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.PriorityQueue;

public class FindTopElements {

	@Test
	public void findTopElements() throws ParseException {

		int topElementsCount = 3;

		DateFormat df = new SimpleDateFormat("dd.MM.yyyy");

		Collection<Person> coll = Arrays.asList(new Person(4, "name4", df.parse("06.10.1983")),
				new Person(5, "name5", df.parse("06.11.1985")), 
				new Person(6, "name6", df.parse("06.10.1990")),
				new Person(1, "name1", df.parse("06.12.1978")), 
				new Person(2, "name2", df.parse("06.10.1980")),
				new Person(3, "name3", df.parse("06.10.1981")));

		final Comparator<Person> comparator = Comparator.comparing(Person::getDateOfBirth);
		PriorityQueue<Person> topElements = new PriorityQueue<>(3, comparator);

		// Add elements from external source
		for (Person p : coll) {

			if (topElements.size() < topElementsCount) {
				topElements.add(p);
			} else {
				final Person top = topElements.peek();
				if (comparator.compare(p, top) > 0) {
					topElements.poll();
					topElements.add(p);
				}
			}

		}
		topElements.forEach(System.out::println);
	}

}
