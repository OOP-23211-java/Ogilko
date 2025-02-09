package org.example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class SorterMapByVal {
    public List<Map.Entry<String, Integer>> SortByFrequency(@org.jetbrains.annotations.NotNull Map<String, Integer> wordFrequencies) {
        Comparator<Map.Entry<String, Integer>> comparator = (entry1, entry2) -> entry2.getValue() - entry1.getValue();

        List<Map.Entry<String, Integer>> entries = new ArrayList<>(wordFrequencies.entrySet());
        entries.sort(comparator);

        return entries;
    }
}
