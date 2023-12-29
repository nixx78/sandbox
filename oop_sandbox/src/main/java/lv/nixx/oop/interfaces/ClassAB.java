package lv.nixx.oop.interfaces;

public class ClassAB implements InterfaceA, InterfaceB{

	@Override
	public void emptyMethod() {
		System.out.println("emptyMethod:ClassAB");
	}

	@Override
	// Тут мы должно четко указать, какой метод должен вызыватся,
	// иначе, возникает ошибка компиляции.
	public String getData() {
		return InterfaceB.super.getData();
	}

}
