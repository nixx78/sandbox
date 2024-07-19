package lv.nixx.poc.features.java17;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.random.RandomGeneratorFactory;

class EnhancedPseudoRandomNumberGenerators {

    @Test
    void randomTest() {

        int[] randoms = RandomGeneratorFactory.of("Random")
                .create()
                .ints(5, 0, 1000)
                .toArray();

        System.out.println(Arrays.toString(randoms));
    }

}
