package lv.nixx.poc.collection.lambda;

import org.junit.jupiter.api.Test;

import java.util.Objects;

public class LambdaWithCustomInterface {


    @Test
    void test() {
        MyConsumer<String> c1 = t -> System.out.println(t + ":ConsumedFirst");
        MyConsumer<String> c2 = t -> System.out.println(t + ":ConsumedSecond");
        MyConsumer<String> c3 = t -> System.out.println(t + ":ConsumedThird");

        c1.andThen(c2).andThen(c3).consume("ValueToConsume");
    }

    @FunctionalInterface
    interface MyConsumer<T> {
        void consume(T consume);

        default MyConsumer<T> andThen(MyConsumer<? super T> after) {
            Objects.requireNonNull(after);
            return (T t) -> {
                consume(t);
                after.consume(t);
            };
        }
    }

}
