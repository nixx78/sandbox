package lv.nixx.poc.features.java9;

public interface MyInterface {

	void simpleMethod();

	default void methodWithDefault() {
		privateMethod();
	}

	default void anotherMethodWithDefault() {
		privateMethod();
	}
	
	private void privateMethod() {
		System.out.println("private method is called");
	}

}
