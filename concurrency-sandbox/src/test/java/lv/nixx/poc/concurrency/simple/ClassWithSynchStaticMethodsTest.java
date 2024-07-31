package lv.nixx.poc.concurrency.simple;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/*
Если статический метод объявляется с модификатором synchronized, он блокируется с использованием mutex,
связанного с классом (объектом класса Class), а не с конкретным экземпляром класса. Это означает,
что если один поток вызывает синхронизированный статический метод, все остальные потоки, пытающиеся вызвать
любой другой синхронизированный статический метод того же класса, будут заблокированы до тех пор,
пока первый поток не завершит выполнение метода.

Мьютекс (от английского "mutex" — сокращение от "mutual exclusion", взаимное исключение) — это механизм синхронизации,
используемый в многопоточном программировании для предотвращения одновременного доступа нескольких потоков к общим ресурсам,
таким как данные или участки кода. Мьютексы позволяют только одному потоку владеть ресурсом в любой момент времени, гарантируя,
что другие потоки будут заблокированы, пока ресурс занят.

Как работает мьютекс
Блокировка (Locking):
Когда поток хочет получить доступ к защищенному ресурсу, он пытается заблокировать мьютекс. Если мьютекс свободен (не заблокирован другим потоком),
поток захватывает его и получает доступ к ресурсу.

Освобождение (Unlocking):
После завершения работы с ресурсом поток освобождает мьютекс, делая его доступным для других потоков.

Ожидание (Waiting):
Если мьютекс уже захвачен другим потоком, поток, пытающийся захватить мьютекс,
будет заблокирован (ожидать), пока мьютекс не будет освобожден.
 */


public class ClassWithSynchStaticMethodsTest {

    @Test
    public void test() {

        MyClass firstInstance = new MyClass();
        MyClass secondInstance = new MyClass();

        ExecutorService executorService = Executors.newFixedThreadPool(4);

        executorService.submit(MyClass::method1);
        executorService.submit(MyClass::method2);
        executorService.submit(firstInstance::nonStaticMethod);
        executorService.submit(secondInstance::nonStaticMethod);

        System.out.println("--> Thread '" + Thread.currentThread().getName() + "' All threads started");

        executorService.shutdown();

        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }

        System.out.println("--> Thread '" + Thread.currentThread().getName() + "'All tasks have finished execution");
    }

    static class MyClass {

        synchronized static void method1() {
            System.out.println("Start: 'static method1'");
            try {
                TimeUnit.SECONDS.sleep(2); // имитируем долгую операцию
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Finished 'static method1'");
        }

        synchronized static void method2() {
            System.out.println("Start: 'method2'");
            try {
                TimeUnit.SECONDS.sleep(2); // имитируем долгую операцию
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Finished 'static method2'");
        }

        synchronized void nonStaticMethod() {
            System.out.println("Start: 'non static method'");
            try {
                TimeUnit.SECONDS.sleep(2); // имитируем долгую операцию
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Finished 'non static method'");
        }
    }

}
