package lv.nixx.poc.concurrency.simple;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/*
CountDownLatch — это синхронизирующий механизм в Java, который используется для управления потоками,
когда один или несколько потоков должны ожидать завершения других потоков перед продолжением своей работы.
Его можно рассматривать как счетчик, который уменьшается по мере завершения отдельных потоков, и как только этот счетчик достигает нуля,
другие ожидающие потоки могут продолжить выполнение.

Основные случаи использования:
Ожидание завершения всех потоков: Если у вас есть набор задач, которые выполняются параллельно (в отдельных потоках), и вам нужно дождаться
их завершения перед тем, как продолжить основную работу, вы можете использовать CountDownLatch. Например, поток ожидает завершения загрузки нескольких файлов,
и как только все файлы загружены, поток продолжает выполнение.

Начало выполнения после подготовки: Например, у вас есть несколько потоков, которые должны стартовать одновременно, но они должны дождаться,
пока все потоки не будут готовы (выполнят некоторую начальную настройку). Можно инициализировать счетчик с числом потоков, и каждый поток будет уменьшать
счетчик по мере своей готовности. Как только счетчик станет равен нулю, все потоки могут начать основную работу.
 */

class CountDownLatchExample {

    @Test
    void countDownLatchAllSuccessExample() throws InterruptedException {

        CountDownLatch cd = new CountDownLatch(3);

        IntStream.of(1000, 100, 200).mapToObj(t -> (Runnable) () -> {
                            final String tn = Thread.currentThread().getName();
                            try {
                                System.out.println("Thread [" + tn + "] will sleep [" + t + "]");
                                Thread.sleep(t);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            cd.countDown();
                            System.out.println("Thread [" + tn + "] done");
                        }
                ).map(Thread::new)
                .forEach(Thread::start);

        System.out.println("Waiting for all treads..");

        boolean await = cd.await(2, TimeUnit.SECONDS);
        System.out.println("All threads are ready, await result: " + await);
    }

}
