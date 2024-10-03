package lv.nixx.poc.collection.lambda;

import org.junit.jupiter.api.Test;

/*

Данная функция не является чистой, поскольку:
 - изменяет внешнее состояние
 - при вызове с одинаковыми параметрами возвращает разные результаты

 */

class NoCleanFunctionExample {

    @Test
    void nonCleanFunctionExample() {

        int[] num = {10};

        MyFunctionalInterface myFunc = (value) -> {
            num[0] += value;  // изменение внешнего состояния
            return num[0];
        };

        System.out.println(myFunc.doSomething(5));
        System.out.println(myFunc.doSomething(5));
    }

    @FunctionalInterface
    interface MyFunctionalInterface {
        int doSomething(int value);
    }

}
