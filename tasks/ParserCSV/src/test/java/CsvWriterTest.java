import org.example.CsvWriter;

import org.junit.jupiter.api.Test;
import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class CsvWriterTest {

    @Test
    void testWrite() throws IOException {
        File tempFile = File.createTempFile("test", ".csv");
        tempFile.deleteOnExit();

        CsvWriter csvWriter = new CsvWriter();

        List<Map.Entry<String, Integer>> entries = new ArrayList<>();
        entries.add(new AbstractMap.SimpleEntry<>("apple", 3));
        entries.add(new AbstractMap.SimpleEntry<>("banana", 2));

        csvWriter.Write(entries, 5);

        BufferedReader reader = new BufferedReader(new FileReader(tempFile));
        assertEquals("apple\t3\t0.6%", reader.readLine());
        assertEquals("banana\t2\t0.4%", reader.readLine());
        assertNull(reader.readLine());
        reader.close();
    }
}