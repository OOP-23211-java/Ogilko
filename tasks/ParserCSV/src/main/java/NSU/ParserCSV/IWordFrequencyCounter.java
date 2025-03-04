package NSU.ParserCSV;

import java.util.Map;

interface IWordFrequencyCounter {
    void CalculateFrequencyWords(Map<String, Integer> wordFrequencies, String[] words);
    int getFrequencyText();
}

