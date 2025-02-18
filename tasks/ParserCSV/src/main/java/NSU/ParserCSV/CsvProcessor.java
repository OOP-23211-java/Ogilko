package NSU.ParserCSV;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class CsvProcessor {
    public void ParserCSV() {
        WordFrequencyCounter counter = new WordFrequencyCounter();
        Map<String, Integer> wordFrequencies = new HashMap<>();
        GetterStream stream = new GetterStream();
        Reader reader = new Reader(stream.getInputFile());

        for (String line; (line = reader.readLine()) != null; ) {
            String[] words = line.split(" ");
            counter.CalculateFrequencyWords(wordFrequencies, words);
        }
        reader.close();

        CsvWriter writer = new CsvWriter(stream.getOutputFile());
        int numWords = counter.getFrequencyText();
        SorterMapByVal sorterMapByVal = new SorterMapByVal();

        List<Map.Entry<String, Integer>> entries = sorterMapByVal.SortByFrequency(wordFrequencies);
        writer.Write(entries, numWords);
    }
}
