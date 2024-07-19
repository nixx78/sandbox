package lv.nixx.poc.features.java15;

import org.junit.jupiter.api.Test;

public class TextBlockSampleTest {

    // https://blogs.oracle.com/javamagazine/inside-java-15-fourteen-jeps-in-five-buckets
    // https://www.baeldung.com/java-15-new
    @Test
    public void textBlockSample() {

        String textBlock = """
                12  \s   
                Lorem ipsum dolor sit amet,
                consectetur adipiscing elit,
                sed do eiusmod tempor incididunt\
                """;

        // \ in text block prevent new line creation

        System.out.println("Text Block String:\n[" + textBlock + "]");

        String str = "line1\nline2\nline3";
        System.out.println("Normal String Literal:\n" + str);

        System.out.println("textBlock.lines()\n" + textBlock.lines().toList());

        System.out.println("Text Block and String Literal equals() Comparison: " + (textBlock.equals(str)));
        System.out.println("Text Block and String Literal == Comparison: " + (textBlock.equals(str)));

        System.out.println("After stripIndent");
        System.out.println(textBlock.stripIndent().replace(" ", "*"));
    }

    @Test
    public void patternMatching() {

//        if ("String.value" instanceof String str) {
//            System.out.println("String to UpperCase:" + str.toUpperCase());
//        }
//
//        Object d = Double.valueOf(10.00);
//        if (d instanceof String str) {
//            System.out.println("String:" + str);
//        } else {
//            System.out.println("Not a string");
//        }
//
//        List<String> mappedStrings = Stream.of("S1", "S2", BigDecimal.valueOf(10), "S3", BigDecimal.valueOf(20))
//                .map(t -> {
//                    if (t instanceof String s) {
//                        return s + "m";
//                    }
//                    return null;
//                })
//                .filter(Objects::nonNull)
//                .collect(Collectors.toList());
//
//        System.out.println(mappedStrings);
//

    }

}
