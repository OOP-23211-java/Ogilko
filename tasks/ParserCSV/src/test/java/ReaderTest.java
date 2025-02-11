import NSU.ParserCSV.Reader;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReaderTest {

    @Mock
    private BufferedReader bufferedReaderMock;

    @Test
    void testGetLine_success() throws IOException {
        when(bufferedReaderMock.readLine()).thenReturn("test line");
        Reader reader = new Reader(bufferedReaderMock);

        String line = reader.getLine();

        assertEquals("test line", line);
    }

    @Test
    void testGetLine_IOException() throws IOException {
        when(bufferedReaderMock.readLine()).thenThrow(new IOException("Test exception"));
        Reader reader = new Reader(bufferedReaderMock);

        String line = reader.getLine();

        assertNull(line);
    }

    @Test
    void testConstructor_validFile() {
        String testFilePath = "test.txt\n";
        InputStream inputStream = new ByteArrayInputStream(testFilePath.getBytes());

        Reader reader = new Reader(inputStream);
        assertNotNull(reader);
        reader.close();
    }

    @Test
    void testConstructor_invalidFile() {
        String testFilePath = "nonexistent_file.txt\n";
        InputStream inputStream = new ByteArrayInputStream(testFilePath.getBytes());

        Reader reader = new Reader(inputStream);
        assertNotNull(reader);
        reader.close();
    }
}
