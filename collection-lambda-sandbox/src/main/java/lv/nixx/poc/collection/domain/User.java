package lv.nixx.poc.collection.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class User {
    private final int id;
    private final String attribute;
    private final String value;
}
