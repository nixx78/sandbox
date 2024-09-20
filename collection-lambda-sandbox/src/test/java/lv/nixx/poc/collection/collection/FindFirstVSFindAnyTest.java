package lv.nixx.poc.collection.collection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class FindFirstVSFindAnyTest {
    private final Stream<Data> stream = Stream.of(
            new Data("T1", "V1"),
            new Data("T1", "V2"),
            new Data("T3", "V3"),
            new Data("T3", "V4")
    );

    @Test
    void findFirstSample() {

        Data firstElement = stream.filter(t -> t.getType().equals("T1"))
                .findFirst()
                .orElse(null);

        assertThat(firstElement)
                .usingRecursiveComparison()
                .isEqualTo(new Data("T1", "V1"));
    }

    @Test
    void findAnySample() {
        Data firstElement = stream.parallel().filter(t -> t.getType().equals("T1"))
                .findAny()
                .orElse(null);

        // В этом случае, не гарантируется, какой элемент будет первым, возможен любой элемент с типом T1
        System.out.println(firstElement);
    }

    @AllArgsConstructor
    @Getter
    @ToString
    static class Data {
        String type;
        String value;
    }

}
