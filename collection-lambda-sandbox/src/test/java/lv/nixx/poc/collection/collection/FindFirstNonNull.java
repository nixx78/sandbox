package lv.nixx.poc.collection.collection;

import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FindFirstNonNull {

    @Test
    void test() {

        Map<Long, Map<String, Wrapper>> dataForTest = Map.of(
                3L, Map.of(
                        "v1", new Wrapper(1L, null),
                        "v2", new Wrapper(2L, null),
                        "default", new Wrapper(3L, "DefaultValue"),
                        "v4", new Wrapper(4L, null)
                ),
                1L, Map.of(
                        "v1", new Wrapper(1L, "V1"),
                        "v2", new Wrapper(2L, null),
                        "default", new Wrapper(3L, "DefaultValue"),
                        "v4", new Wrapper(4L, null)
                ), 2L, Map.of(
                        "v1", new Wrapper(1L, null),
                        "v2", new Wrapper(2L, "V2"),
                        "default", new Wrapper(3L, "DefaultValue"),
                        "v4", new Wrapper(4L, null)
                )
        );
        getByWaterfallTest(dataForTest);
        getByWaterfallStreamTest(dataForTest);
    }

    void getByWaterfallTest(Map<Long, Map<String, Wrapper>> testDataAndExpectedResult) {
        testDataAndExpectedResult.forEach( (expected, data) -> assertEquals(expected, getByWaterfall(data)));
    }

    void getByWaterfallStreamTest(Map<Long, Map<String, Wrapper>> testDataAndExpectedResult) {
        testDataAndExpectedResult.forEach( (expected, data) -> assertEquals(expected, getByWaterfallStream(data)));
    }

    private Long getByWaterfall(Map<String, Wrapper> values) {

        Wrapper v1 = values.get("v1");

        if (v1 == null || v1.value() == null) {
            Wrapper v2 = values.get("v2");
            if (v2 == null || v2.value() == null) {
                return values.get("default").id();
            } else {
                return v2.id();
            }
        } else {
            return v1.id();
        }
    }

    private Long getByWaterfallStream(Map<String, Wrapper> values) {
        return Stream.of(
                        values.get("v1"),
                        values.get("v2"),
                        values.get("default")
                )
                .filter(t -> t != null && t.value() != null)
                .findFirst()
                .map(Wrapper::id)
                .orElseThrow(() -> new IllegalStateException("No value found"));
    }

    record Wrapper(Long id, String value) {
    }

}
