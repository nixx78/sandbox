package lv.nixx.poc.datastruct.compare;

import org.junit.Test;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

public class CustomStringComparatorTest {

    private static final CustomStringComparator c = new CustomStringComparator();

    @Test
    public void comparatorTest() {

        List<String> sortedList = Stream.of("X", "NA", "1", "X", "AAA", "B", "XXX", "M", "D1", "A", "AAA").sorted(c).collect(Collectors.toList());

        assertThat(sortedList, contains("X", "X", "A", "1", "B", "AAA", "AAA", "D1", "M", "NA", "XXX"));
        System.out.println(sortedList);
    }


    static class CustomStringComparator implements Comparator<String> {
        private static final List<String> order = List.of("X", "A", "1", "B");

        @Override
        public int compare(String s1, String s2) {
            if (order.contains(s1) && order.contains(s2)) {
                return Integer.compare(order.indexOf(s1), order.indexOf(s2));
            }

            if (order.contains(s1)) {
                // s1 is in the ordered list, but s2 isn't. s1 is smaller (i.e. first)
                return -1;
            }

            if (order.contains(s2)) {
                // s2 is in the ordered list, but s1 isn't. s2 is smaller (i.e. first)
                return 1;
            }

            // Compare by Alphabetical order
            return s1.compareTo(s2);
        }
    }

}
