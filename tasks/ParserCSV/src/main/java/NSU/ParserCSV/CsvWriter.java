package NSU.ParserCSV;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.List;

public class CsvWriter {
    BufferedWriter writer;

    public CsvWriter(FileWriter fileWriter) {
        writer = new BufferedWriter(fileWriter);
    }

    public void Write(List<Map.Entry<String, Integer>> entries, int numWords) {
        if (writer == null) {
            System.err.println("Не удалось создать файл для записи. Данные не будут сохранены.");
            return;
        }

        try {
            for (Map.Entry<String, Integer> entry : entries) {
                String word = entry.getKey();
                float freq = entry.getValue();

                String lineCSV = word + "\t" + freq + "\t" + freq * 100 / numWords + "%";
                writer.write(lineCSV);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                System.err.println("Ошибка при закрытии файла: " + e.getMessage());
            }
        }
    }
}
