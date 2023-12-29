package lv.nixx.oop.smartclass;

import lv.nixx.oop.domain.Person;

import java.util.Collection;

public interface Persons {

    Collection<Person> getAll();

    final class Operations {

        private final Persons persons;

        public Operations(Persons persons) {
            this.persons = persons;
        }

        public Person getById(String id) {
            persons.getAll();
            return null;
        }

        public Person getByName(String name) {
            return null;
        }
    }
}
