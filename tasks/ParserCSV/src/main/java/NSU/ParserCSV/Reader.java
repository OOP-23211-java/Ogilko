package NSU.ParserCSV;

import java.io.FileReader;
import java.io.BufferedReader;
import java.util.Scanner;
import java.io.IOException;
import java.io.InputStream;

public class Reader {
    private BufferedReader reader;

    public Reader() {
        this(System.in);
    }

    public Reader(InputStream inputStream) {
        try {
            final Scanner scanner = new Scanner(inputStream);
            System.out.print("Enter path to input file: ");
            String filePath = scanner.nextLine();
            reader = new BufferedReader(new FileReader(filePath));

        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }
    }

    public Reader(BufferedReader reader) {
        this.reader = reader;
    }

    public String getLine() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
            return null;
        }
    }

    public void close() {
        try {
            if (reader != null) {
                reader.close();
            }
        } catch (IOException e) {
            System.err.println("Ошибка при закрытии файла: " + e.getMessage());
        }
    }
}
