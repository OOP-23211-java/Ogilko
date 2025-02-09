package org.example;

import java.util.Map;

public class WordFrequencyCounter {
    private int frequencyText = 0;

    public void CalculateFrequencyWords(Map<String, Integer> frequency, String[] words) {
        for (String word : words) {
            ++frequencyText;
            frequency.put(word, frequency.getOrDefault(word, 0) + 1);
        }
    }

    public int getFrequencyText() {
        return frequencyText;
    }
}
