package lv.nixx.poc.features.java12;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileMismatchTest {

    @Test
    public void fileMismatchSample() throws IOException {

        Path filePath1 = Files.createTempFile("file1", ".txt");
        Path filePath2 = Files.createTempFile("file2", ".txt");
        Files.writeString(filePath1,"string in file");
        Files.writeString(filePath2,"string in file");

        long mismatch = Files.mismatch(filePath1, filePath2);

        System.out.println("File Mismatch position... It returns -1 if there is no mismatch");
        System.out.println("Mismatch position in file1 and file2 is >>>>");
        System.out.println(mismatch);
        assertEquals(-1, mismatch);

        filePath1.toFile().deleteOnExit();
        filePath2.toFile().deleteOnExit();

        System.out.println();

        Path filePath3 = Files.createTempFile("file3", ".txt");
        Path filePath4 = Files.createTempFile("file4", ".txt");
        Files.writeString(filePath3,"1234567890");
        Files.writeString(filePath4,"123XXXXXXX");

        long mismatch2 = Files.mismatch(filePath3, filePath4);
        assertEquals(3, mismatch2);

        System.out.println("Mismatch position in file3 and file4 is >>>>");
        System.out.println(mismatch2);

        filePath3.toFile().deleteOnExit();
        filePath4.toFile().deleteOnExit();
    }


}
