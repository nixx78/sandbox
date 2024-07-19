package lv.nixx.poc.features.java15.local;

import org.junit.jupiter.api.Test;

public class LocalTest {

    @Test
    public void methodWithLocalEnumSample() {

        enum LocalEnum {
            V1, V2, V3
        }

        System.out.println(LocalEnum.V1);
    }

    @Test
    public void methodWithLocalRecordSample() {

        record LocalRecord(int id, String name) {
        }

        LocalRecord record = new LocalRecord(1, "name1");
        System.out.println(record);
    }

    @Test
    public void methodWithLocalInterface() {

        interface LocalProcessor {
            void process();
        }

        LocalProcessor p1 = () -> System.out.println("process1");

        p1.process();

        LocalProcessor p2 = () -> System.out.println("process2");

        p2.process();
    }


}


