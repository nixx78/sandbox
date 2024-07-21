package lv.nixx.poc.features.java21.seq_collection;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.SequencedCollection;

class SequencesCollectionSandbox {

    @Test
    void sandbox() {

        SequencedCollection<String> sc = new ArrayList<>(List.of(
                "E1", "E2", "E3"
        ));

        System.out.println(sc.getFirst());
        System.out.println(sc.getLast());
    }

}
