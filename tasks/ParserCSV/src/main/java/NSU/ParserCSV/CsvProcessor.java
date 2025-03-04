package NSU.ParserCSV;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class CsvProcessor {
    private final IFilePathReader filePathReader;
    private final IWordFrequencyCounter wordCounter;
    private final ICsvWriter csvWriter;
    private final ISorterMapByVal sorter;

    private Map<String, Integer> wordFrequencies;

    public CsvProcessor(IFilePathReader filePathReader, IWordFrequencyCounter wordCounter, ICsvWriter csvWriter, ISorterMapByVal sorter) {
        this.filePathReader = filePathReader;
        this.wordCounter = wordCounter;
        this.csvWriter = csvWriter;
        this.sorter = sorter;
    }

    public void processCsv() {
        processingData();
        outputData();
    }

    private void processingData() {
        wordFrequencies = new HashMap<>();
        try (Reader fileReader = new Reader(filePathReader.getFileReader())) {
            for (String currentLine; (currentLine = fileReader.readLine()) != null; ) {
                String[] wordsInLine = currentLine.split("\\s+");
                wordCounter.CalculateFrequencyWords(wordFrequencies, wordsInLine);
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении или закрытии файла: " + e.getMessage());
        }
    }

    private void outputData() {
        try {
            List<Map.Entry<String, Integer>> sortedWordFrequencies = sorter.SortByFrequency(wordFrequencies);
            int numWords = wordCounter.getFrequencyText();

            csvWriter.Write(sortedWordFrequencies, numWords);
        } catch (IOException e) {
            System.err.println("Ошибка при записи в файл: " + e.getMessage());
        }
    }
}
