package lv.nixx.poc.features.java12;

import org.junit.jupiter.api.Test;

import java.text.NumberFormat;
import java.util.Locale;

public class CompactNumberFormattingTest {

    @Test
    public void sample() {
        Locale locale = new Locale("en", "US");

        NumberFormat nf1 = NumberFormat.getCompactNumberInstance(locale, NumberFormat.Style.LONG);
        nf1.setMaximumFractionDigits(2);
        System.out.println(nf1.format(2011) + " upvotes");

        NumberFormat nf2 = NumberFormat.getCompactNumberInstance(locale, NumberFormat.Style.SHORT);
        nf2.setGroupingUsed(true);
        nf2.setMaximumFractionDigits(3);
        System.out.println(nf2.format(1_123_600) + " upvotes");
    }

}
