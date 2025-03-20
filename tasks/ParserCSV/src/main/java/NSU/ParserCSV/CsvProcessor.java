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
        this.sorter = sorter;this.wordFrequencies = new HashMap<>();
    }

    public void processCsv() {
        processingData();
        writeCsv();
    }

    /**
     * Построчно читает текст из файла,
     * разбивает его на слова и
     * подсчитывает частоту каждого слова
     */
    private void processingData() {
        try (Reader fileReader = new Reader(filePathReader.getFileReader())) {
            String currentLine;
            while ((currentLine = fileReader.readLine()) != null) {
                String[] wordsInLine = currentLine.split("[^a-zA-Z0-9]+");
                wordCounter.CalculateFrequencyWords(wordFrequencies, wordsInLine);
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении или закрытии файла: " + e.getMessage());
        }
    }

    /**
     * Сортирует слова в порядке убывания частоты,
     * считает количество всех слов,
     * записывает полученные данные в формате CSV.
     */
    private void writeCsv() {
        try {
            List<Map.Entry<String, Integer>> sortedWordFrequencies = sorter.SortByFrequency(wordFrequencies);
            int numWords = wordCounter.getFrequencyText();

            csvWriter.Write(sortedWordFrequencies, numWords);
        } catch (IOException e) {
            System.err.println("Ошибка при записи в файл: " + e.getMessage());
        }
    }
}
