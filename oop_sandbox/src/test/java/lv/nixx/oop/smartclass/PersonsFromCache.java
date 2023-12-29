package lv.nixx.oop.smartclass;

import lv.nixx.oop.domain.Person;

import java.util.Collection;
import java.util.Collections;

public class PersonsFromCache implements Persons {

    @Override
    public Collection<Person> getAll() {
        return Collections.emptyList();
    }

}
