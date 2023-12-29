package lv.nixx.oop.fake;

import org.junit.Test;

public class PersonsTest {

    @Test
    public void testFakeApproach() {
        Service service = new Service();
        service.setPersons(new Persons.Stub.existing());

        service.processAllPersons();

        service.setPersons(new Persons.Stub.empty());
        service.processAllPersons();
    }

    @Test
    public void mockApproach() {

        //No work with Mockito

//        Persons p = mock(Persons.class);
//        doReturn(List.of(
//                new Person(1, "name1", "surname1", "iid"),
//                new Person(2, "name2", "surname2", "iid"),
//                new Person(3, "name3", "surname3", "iid")
//        )).when(p).get();
//
//        Service service = new Service();
//        service.setPersons(p);
//
//        service.processAllPersons();
    }


}
