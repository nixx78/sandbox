package lv.nixx.oop.smartclass;

import org.junit.Test;

public class PersonsTest {

    @Test
    public void getTest() {
        Persons.Operations cachedPersons = new Persons.Operations(new PersonsFromCache());

        cachedPersons.getById("Id");
        cachedPersons.getByName("Name");

        Persons.Operations personsFromDb = new Persons.Operations(new PersonsFromDb());
        personsFromDb.getById("Id");
        personsFromDb.getByName("Name");

    }
}
