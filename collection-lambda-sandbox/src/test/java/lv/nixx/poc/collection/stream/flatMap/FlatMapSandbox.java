package lv.nixx.poc.collection.stream.flatMap;

import lombok.Builder;
import lombok.Getter;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class FlatMapSandbox {

    @Test
    void flatMapSandbox() {

        // Метод flatMap() позволяет сделать маппинг объекта в stream, в последствие, элементы stream
        // будут "слиты" в один стрим

        Collection<String> uniqueHobies = Stream.of(Person.builder()
                                .name("n1")
                                .surname("n1")
                                .hobie(List.of("Photo", "Video"))
                                .build(),
                        Person.builder()
                                .name("n2")
                                .surname("n2")
                                .hobie(List.of("Photo", "Cooking"))
                                .build(),
                        Person.builder()
                                .name("n3")
                                .surname("n3")
                                .hobie(List.of("Travel", "Hiking", "Photo"))
                                .build(),
                        Person.builder()
                                .name("n4")
                                .surname("n4")
                                .build()
                )
                .filter(t -> t.getHobie() != null)
                .flatMap(t -> t.getHobie().stream().map(String::toUpperCase))
                .collect(Collectors.toSet());

        assertThat(uniqueHobies)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(List.of("PHOTO", "VIDEO", "COOKING", "TRAVEL", "HIKING"));
    }

    @Builder
    @Getter
    static class Person {
        private final String name;
        private final String surname;
        private final Collection<String> hobie;
    }

}
