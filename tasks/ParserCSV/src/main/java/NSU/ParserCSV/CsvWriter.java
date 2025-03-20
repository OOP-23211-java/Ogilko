package NSU.ParserCSV;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.List;

public class CsvWriter implements ICsvWriter {
    BufferedWriter writer;

    public CsvWriter(FileWriter fileWriter) {
        writer = new BufferedWriter(fileWriter);
    }

    /**
     * Записывает список в формате CSV
     * @param entries список мап
     * @param numWords количество слов во всём файле
     */
    @Override
    public void Write(List<Map.Entry<String, Integer>> entries, int numWords) {
        try {
            for (Map.Entry<String, Integer> entry : entries) {
                String lineCSV = CSVFormatter(entry, numWords);
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

    /**
     * Форматирует слово с частотой в CSV
     */
    private String CSVFormatter(Map.Entry<String, Integer> entry, Integer numWords) {
        String word = entry.getKey();
        float freq = entry.getValue();
        return word + "\t" + freq + "\t" + freq * 100 / numWords + "%";
    }
}
