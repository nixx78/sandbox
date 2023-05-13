package lv.nixx.poc.datastruct.binarysearch;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class BinarySearcherTest {
	
	List<Person> p;
	
	@Before
	public void init() {
		p = new ArrayList<>();
		p.add(new Person(10, "Ivan"));
		p.add(new Person(2, "Mike"));
		p.add(new Person(777, "Juan"));
		p.add(new Person(0, "John"));
		p.add(new Person(1, "Petr"));
		p.add(new Person(999, "Anna"));
		p.add(new Person(3, "Filipe"));
		p.add(new Person(7, "Sidr"));
	}
	
	@Test
	public void testById_3() {
		final Person foundPerson = BinarySearcher.searchById(p, 3); 
		
		assertNotNull(foundPerson);
		assertEquals(3, foundPerson.id);
		assertEquals("Filipe", foundPerson.name);
	}
	
	@Test
	public void testById_LastElement() {
		final Person foundPerson = BinarySearcher.searchById(p, 2); 
		
		assertNotNull(foundPerson);
		assertEquals(2, foundPerson.id);
		assertEquals("Mike", foundPerson.name);
	}
	
	@Test
	public void testById_FirstElement() {
		final Person foundPerson = BinarySearcher.searchById(p, 0); 
		
		assertNotNull(foundPerson);
		assertEquals(0, foundPerson.id);
		assertEquals("John", foundPerson.name);
	}

	@Test
	public void testById_OneElementList() {
		final Person foundPerson = BinarySearcher.searchById(Collections.singletonList(new Person(777, "Ivan")), 777);
		assertNotNull(foundPerson);
		assertEquals(777, foundPerson.id);
		assertEquals("Ivan", foundPerson.name);
	}
	
	@Test
	public void notFound_8888() {
		assertNull(BinarySearcher.searchById(p, 8888));
	}
	
	@Test
	public void notFound_OneElementList() {
		assertNull(BinarySearcher.searchById(Collections.singletonList(new Person(777, "Ivan")), 8888));
	}

}
