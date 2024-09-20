package lv.nixx.poc.collection.collection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.junit.Test;

import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TotalWithTypeSample {

    @Test
    public void calculateTotalAndGetType() {

        Collection<Position> pos = List.of(
                new Position(2, new AmountHolder(20, "EOD")),
                new Position(1, new AmountHolder(10, "REAL")),
                new Position(3, new AmountHolder(30, "EOD"))
        );

        // Работает так-же как второй вариант, используется, если нельзя применять map
        TotalAmountHolder total1 = pos.stream()
                .reduce(new TotalAmountHolder(), (t1, t2) -> t1.addAmount(t2.getHolder()), TotalAmountHolder::add);

        TotalAmountHolder total = pos.stream()
                .map(Position::getHolder)
                .reduce(new TotalAmountHolder(), TotalAmountHolder::addAmount, TotalAmountHolder::add);

        // Parameter combiner not in use, this is used only in parallel operations
        assertEquals(60, total.amount);
        assertEquals("REAL", total.source);
    }

    @AllArgsConstructor
    @NoArgsConstructor
    static class TotalAmountHolder {
        int amount = 0;
        String source = "";

        TotalAmountHolder add(TotalAmountHolder anotherTotal) {
            return new TotalAmountHolder(this.amount + anotherTotal.amount, calculateSource(anotherTotal.source));
        }

        TotalAmountHolder addAmount(AmountHolder posAmount) {
            return new TotalAmountHolder(this.amount + posAmount.amount, calculateSource(posAmount.source));
        }

        String calculateSource(String anotherSource) {
            return this.source.equals("REAL") ? this.source : anotherSource;
        }

    }

    @AllArgsConstructor
    static class AmountHolder {
        int amount;
        String source;
    }

    @AllArgsConstructor
    @Getter
    static class Position {
        int id;
        AmountHolder holder;
    }


}
