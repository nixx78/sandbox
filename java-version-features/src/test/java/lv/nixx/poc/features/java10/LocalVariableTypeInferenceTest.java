package lv.nixx.poc.features.java10;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;

public class LocalVariableTypeInferenceTest {

    // http://openjdk.java.net/jeps/286
    @Test
    public void sample() {

        var x = new ArrayList<String>();
        x.add("First element");
        x.add("Second element");

        print(x);
    }

    private void print(Collection<String> c) {
        System.out.println(c);
    }


}
