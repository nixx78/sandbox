package lv.nixx.poc.datastruct.collection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

public class CollectionOfObjectFlattenSampleTest {

    @Test
    public void flattenSample() {

        List<Integer> flattenIds = Stream.of(
                        new Entity(1, "name1", Collections.emptyList()),
                        new Entity(2, "name2", List.of(
                                new Entity(21, "name21", null),
                                new Entity(22, "name22", null)
                        )),
                        new Entity(3, "name3", Collections.emptyList())
                )
                .map(t -> {
                    ArrayList<Integer> lst = t.grouped.stream()
                            .map(Entity::getId)
                            .distinct()
                            .collect(Collectors.toCollection(ArrayList::new));

                    lst.add(t.getId());
                    return lst;
                }).flatMap(Collection::stream)
                .toList();

        System.out.println(flattenIds);

        assertThat(flattenIds, containsInAnyOrder(1, 2, 21, 22, 3));
    }

    @Getter
    @AllArgsConstructor
    static class Entity {
        int id;
        String name;
        Collection<Entity> grouped;

    }


}
