package NSU.ParserCSV;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class CsvProcessor {
    public void ParserCSV() {
        WordFrequencyCounter counter = new WordFrequencyCounter();
        Map<String, Integer> wordFrequencies = new HashMap<>();
        Reader reader = new Reader();
        for (String line; (line = reader.getLine()) != null; ) {
            String[] words = line.split(" ");
            counter.CalculateFrequencyWords(wordFrequencies, words);
        }

        CsvWriter writer = new CsvWriter();
        int numWords = counter.getFrequencyText();
        SorterMapByVal sorterMapByVal = new SorterMapByVal();
        List<Map.Entry<String, Integer>> entries = sorterMapByVal.SortByFrequency(wordFrequencies);
        writer.Write(entries, numWords);
    }
}
