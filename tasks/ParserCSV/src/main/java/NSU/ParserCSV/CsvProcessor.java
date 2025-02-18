package NSU.ParserCSV;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class CsvProcessor {
    private GetterStream filePaths;
    private WordFrequencyCounter wordCounter;
    private Map<String, Integer> wordFrequencies;

    public void processCsv() {
        wordCounter = new WordFrequencyCounter();
        filePaths = new GetterStream();

        processingData();

        outputData();
    }

    private void processingData() {
        wordFrequencies = new HashMap<>();
        Reader fileReader = new Reader(filePaths.getInputFile());

        for (String currentLine; (currentLine = fileReader.readLine()) != null; ) {
            String[] wordsInLine = currentLine.split("\\s+");
            wordCounter.CalculateFrequencyWords(wordFrequencies, wordsInLine);
        }

        fileReader.close();
    }

    private void outputData() {
        CsvWriter csvWriter = new CsvWriter(filePaths.getOutputFile());
        filePaths.close();

        SorterMapByVal sorterMapByVal = new SorterMapByVal();
        List<Map.Entry<String, Integer>> sortedWordFrequencies = sorterMapByVal.SortByFrequency(wordFrequencies);

        int numWords = wordCounter.getFrequencyText();

        csvWriter.Write(sortedWordFrequencies, numWords);
    }
}
