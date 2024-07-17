package lv.nixx.poc.collection.reduce;

import lombok.ToString;
import lv.nixx.poc.collection.domain.User;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.reducing;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GroupReduceMap {

    @Test
    void groupSample() {

        Collection<User> users = List.of(
                new User(10, "name", "user1"),
                new User(10, "group", "group1"),
                new User(10, "schema", "schema1"),
                new User(20, "name", "user2"),
                new User(20, "group", "group2"),
                new User(20, "schema", "schema2")
        );

        Map<Integer, UserDTO> userDTO = users.stream()
                .collect(
                        groupingBy(User::getId,
                                reducing(new UserDTO(), UserDTO::new, (dto1, dto2) -> dto2.putAll(dto1.values))
                        )
                );

        System.out.println(userDTO.values());
        assertEquals(2, userDTO.size());
        assertThat(userDTO.get(10).values.entrySet(), is(Map.of(
                "name", "user1",
                "group", "group1",
                "schema", "schema1"
        ).entrySet()));
    }

    @ToString
    static class UserDTO {
        int id;
        Map<String, String> values = new HashMap<>();

        public UserDTO() {
        }

        public UserDTO(User user) {
            this.id = user.getId();
            values.put(user.getAttribute(), user.getValue());
        }

        private UserDTO putAll(Map<String, String> v) {
            values.putAll(v);
            return this;
        }
    }


}
