package lv.nixx.poc.collection.lambda;


import org.junit.jupiter.api.Test;

/*
Замыкание (closure) в языке Java — это конструкция, которая позволяет функции иметь доступ к переменным из внешней области видимости,
даже после завершения этой области. В контексте Java это понятие часто связывают
с лямбда-выражениями и анонимными классами
*/

class ClosureExample {

    /* Данная функция также является чистой, поскольку
        - Всегда возвращает одинаковый результат при одинаковых входных данных, без зависимости от внешнего состояния или побочных эффектов.
        - Не изменяет состояние вне своей области видимости (то есть нет побочных эффектов), как, например, изменение глобальных переменных или взаимодействие с I/O (ввод/вывод).
     */
    @Test
    void closureAndCleanFunctionExample() {
        String value = "Value";
        MyFunctionalInterface myFunc = t -> System.out.println(value + ":" + t);

        myFunc.doSomething("FunctionParam");
    }

    @FunctionalInterface
    interface MyFunctionalInterface {
        void doSomething(String value);
    }

}
