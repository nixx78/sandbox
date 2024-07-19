package lv.nixx.poc.features.java16;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

class NewInCollectionsTest {

    @Test
    void toListTest() {

        List<String> modifiedList = Stream.of("1", "2", "3")
                .map(t -> t + "+")
                .toList();

        assertThat(modifiedList, contains("1+", "2+", "3+"));
    }

}
