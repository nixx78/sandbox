package lv.nixx.oop.mutipleinterfaces;

import lv.nixx.oop.interfaces.InterfaceTwo;
import lv.nixx.oop.interfaces.InterfaceWithDefault;
import org.junit.jupiter.api.Test;

class InterfaceWithDefaultTest {

    @Test
    void interfaceWithDefaultTest() {
        M m = new M();

        m.method1("p1.v");
        m.method2("p2.v");
        m.method3("p3.v");

        System.out.println(InterfaceWithDefault.d);
        System.out.println(InterfaceWithDefault.staticString);
    }

    class M implements InterfaceWithDefault, InterfaceTwo {

        // Этот метод обязательно должен быть определен, посколько он есть в InterfaceTwo
        @Override
        public void method1(String p) {
            InterfaceWithDefault.super.method1(p);
            System.out.println("Override method '1' call:" + p);
        }

        @Override
        public void method2(String p) {
            System.out.println("Call method '2' :" + p);
        }

        @Override
        public void method3(String p) {
            System.out.println("Call method '3' :" + p);
        }
    }

}
