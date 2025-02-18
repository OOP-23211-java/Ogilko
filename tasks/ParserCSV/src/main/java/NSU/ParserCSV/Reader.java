package NSU.ParserCSV;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Reader {
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

    public void close() {
        try {
            reader.close();
        } catch (IOException e) {
            System.err.println("Ошибка при закрытии файла: " + e.getMessage());
        }
    }
}
