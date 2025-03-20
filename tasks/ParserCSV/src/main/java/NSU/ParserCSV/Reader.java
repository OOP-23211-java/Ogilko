package NSU.ParserCSV;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Closeable;

public class Reader implements Closeable  {
    private final BufferedReader reader;

    public Reader(FileReader fileReader) {
        reader = new BufferedReader(fileReader);
    }

    public String readLine() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void close() throws IOException {
        try {
            reader.close();
        } catch (IOException e) {
            System.err.println("Ошибка при закрытии файла: " + e.getMessage());
            throw e;
        }
    }
}
