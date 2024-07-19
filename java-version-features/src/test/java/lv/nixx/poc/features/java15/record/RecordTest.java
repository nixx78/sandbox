package lv.nixx.poc.features.java15.record;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static java.math.BigDecimal.ZERO;
import static org.junit.jupiter.api.Assertions.*;

public class RecordTest {

    @Test
    public void sample() {

        Transaction t = new Transaction(100, BigDecimal.valueOf(100));

        System.out.println("Transaction (default: toString()):" + t);
        System.out.println("Transaction id:" + t.id());
        System.out.println("Transaction description:" + t.getDescription());

    }

    @Test
    public void incorrectTransactionSample() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> new Transaction(1, ZERO));
        assertEquals("Amount can't be zero", ex.getMessage());
    }

    @Test
    public void staticFactoryMethodSample() {
        Transaction t = Transaction.of(BigDecimal.valueOf(100));
        System.out.println(t);
    }

    @Test
    public void oneParameterConstructorSample() {
        Transaction t = new Transaction(BigDecimal.valueOf(-777));
        System.out.println(t);
    }

    @Test
    public void recordsReflect() {
        assertTrue(Transaction.class.isRecord());
        Stream.of(Transaction.class.getRecordComponents()).forEach(System.out::println);
    }


}

record Transaction(int id, BigDecimal amount) {

    // Простое поле добавить не возможно
    // private String vl;

    // Можно добавить статическое поле
    private static String s;

    // Альтернативный конструктор
    public Transaction(BigDecimal amount) {
        this(-1, amount);
    }

    Transaction {
        // в конструкторе, мы можем произвести проверку параметров
        if (amount.compareTo(ZERO) == 0) {
            throw new IllegalArgumentException("Amount can't be zero");
        }
    }

    // Можно создавать статические методы для инициации
    static Transaction of(BigDecimal amount) {
        return new Transaction(-1, amount);
    }

    // Можно создавать методы
    String getDescription() {
        return "Transaction id:" + id + "amount: " + amount;
    }

}
