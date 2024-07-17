package lv.nixx.poc.collection.bidAsk;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;
import static lv.nixx.poc.collection.bidAsk.BidAskHolderTest.BidAsk.ASK;
import static lv.nixx.poc.collection.bidAsk.BidAskHolderTest.BidAsk.BID;
import static org.junit.Assert.assertEquals;

public class BidAskHolderTest {

    @Test
    public void bidAskSample() {

        List<Position> positions = List.of(
                new Position("isin1", BID, 100.01),
                new Position("isin1", ASK, 100.02),
                new Position("isin2", BID, 200.01),
                new Position("isin2", ASK, 200.02),
                new Position("isin3", BID, 300.01),
                new Position("isin3", ASK, 300.02),
                new Position("isin4", BID, 400.01),
                new Position("isin4", ASK, 400.02)
        );

        Map<String, Map<BidAsk, Double>> m1 = positions.stream()
                .collect(groupingBy(Position::getIsin, toMap(Position::getBidAsk, Position::getValue)));

        assertEquals(4, m1.size());

        m1.forEach((key, value) -> System.out.println(key + "\n\t\t" + value));

        Map<String, BidAskDTO> m2 = positions.stream()
                .collect(groupingBy(Position::getIsin,
                        collectingAndThen(
                                reducing(new BidAskWrapper(), p -> new BidAskWrapper(p), (w1, w2) -> w1.combine(w2)), BidAskWrapper::toDto))
                );

        assertEquals(4, m2.size());

        System.out.println("==================");
        m2.forEach((key, value) -> System.out.println(key + "\n\t\t" + value));

        Map<String, BidAskDTO> m3 = positions.stream()
                .collect(groupingBy(Position::getIsin,
                        collectingAndThen(
                                reducing(new BidAskWrapperWithMap(),
                                        p -> new BidAskWrapperWithMap(p), (w1, w2) -> w1.combine(w2)), BidAskWrapperWithMap::toDto))
                );

        assertEquals(4, m3.size());

        System.out.println("==================");
        m3.forEach((key, value) -> System.out.println(key + "\n\t\t" + value));


    }

    // This is working approach, just for sample
    //    BidAskWrapper combine(BidAskWrapper p1, BidAskWrapper p2) {
    //        if (p1.bid != null) {
    //            p2.bid = p1.bid;
    //        }
    //        if (p1.ask != null) {
    //            p2.ask = p1.ask;
    //        }
    //        return p2;
    //    }


    @AllArgsConstructor
    @Getter
    static class Position {
        String isin;
        BidAsk bidAsk;
        Double value;
    }

    static class BidAskWrapper {
        private Double bid;
        private Double ask;

        BidAskWrapper() {
        }

        BidAskWrapper(Position p) {
            if (p.getBidAsk() == BID) {
                this.bid = p.getValue();
            } else {
                this.ask = p.getValue();
            }
        }

        BidAskWrapper combine(BidAskWrapper p) {
            if (this.bid != null) {
                p.bid = this.bid;
            }
            if (this.ask != null) {
                p.ask = this.ask;
            }
            return p;
        }

        BidAskDTO toDto() {
            return new BidAskDTO(bid, ask);
        }
    }

    static class BidAskWrapperWithMap {
        Map<BidAsk, Double> m;

        BidAskWrapperWithMap() {
            m = new HashMap<>();
        }

        BidAskWrapperWithMap(Position p) {
            this();
            m.put(p.bidAsk, p.value);
        }

        BidAskWrapperWithMap combine(BidAskWrapperWithMap p) {
            p.m.putAll(this.m);
            return p;
        }

        BidAskDTO toDto() {
            return new BidAskDTO(m.get(BID), m.get(ASK));
        }

    }

    @AllArgsConstructor
    @Getter
    @ToString
    static class BidAskDTO {
        Double bid;
        Double ask;
    }

    enum BidAsk {
        BID, ASK
    }

}

