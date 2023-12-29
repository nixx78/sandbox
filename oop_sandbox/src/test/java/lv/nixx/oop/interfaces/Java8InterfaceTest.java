package lv.nixx.oop.interfaces;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Java8InterfaceTest {
	
	@Test
	void staticMethodCallFromInterface() {
		assertEquals("static_data:interfaceA", InterfaceA.staticMethodInInterface());
	}
	
	@Test
	void interfaceInheritance(){
		
		InterfaceA ri = new InterfaceA() {

			@Override
			public String getData() {
				return "data:class";
			}

			@Override
			public void emptyMethod() {
				System.out.println("Empty method called");
			}
		};
		
		// В этом случае, будет вызван метод класса 
		assertEquals("data:class", ri.getData());
	}
	
	@Test
	void classABTest(){
		ClassAB ab = new ClassAB();
		
		assertTrue(ab instanceof InterfaceA);
		assertTrue(ab instanceof InterfaceB);
		assertTrue(ab instanceof ClassAB);

		assertEquals("data:interfaceB", ab.getData());
	}

}
