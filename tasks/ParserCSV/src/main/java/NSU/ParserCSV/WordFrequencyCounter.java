package NSU.ParserCSV;

import java.util.Map;

public class WordFrequencyCounter implements IWordFrequencyCounter {
    private int frequencyText = 0;

    /**
     * Считает частоту слов в строке и записывает в мапу
     * @param frequency мапа слово:частота
     * @param words строка слов
     */
    @Override
    public void CalculateFrequencyWords(Map<String, Integer> frequency, String[] words) {
        for (String word : words) {
            ++frequencyText;
            frequency.put(word, frequency.getOrDefault(word, 0) + 1);
        }
    }

    @Override
    public int getFrequencyText() {
        return frequencyText;
    }
}
