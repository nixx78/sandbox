package lv.nixx.oop.mutipleinterfaces;

import lv.nixx.oop.interfaces.InterfaceOne;
import lv.nixx.oop.interfaces.InterfaceTwo;
import org.junit.jupiter.api.Test;

class MultipleinterfacesTest {

    @Test
    void classWithMultipleInterfacesTest() {

        M m = new M();

        interfaceOneProcess(m);
        System.out.println("-------------------");
        interfaceTwoProcess(m);
    }

    void interfaceOneProcess(InterfaceOne impl) {
        impl.method1("p1_1");
        impl.method2("p2_1");
    }

    void interfaceTwoProcess(InterfaceTwo impl) {
        impl.method1("p1_2");
        impl.method2("p2_2");
        impl.method3("p3_3");
    }


    class M implements InterfaceOne, InterfaceTwo {

        @Override
        public void method1(String p) {
            System.out.println("Call method '1' :" + p);
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
