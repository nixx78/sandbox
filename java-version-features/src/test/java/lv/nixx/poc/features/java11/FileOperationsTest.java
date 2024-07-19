package lv.nixx.poc.features.java11;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

public class FileOperationsTest {

    @Test
    public void sample() throws Exception {

        Path path = Files.writeString(
                Path.of("./test.txt"), "text in file"
        );

        System.out.println(path);
        String s = Files.readString(path);
        System.out.println(s);
    }
}
