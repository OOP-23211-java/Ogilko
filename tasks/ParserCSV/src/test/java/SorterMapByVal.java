import org.example.SorterMapByVal;

import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class SorterMapByValTest {
    @Test
    void testSortByFrequency() {
        SorterMapByVal sorter = new SorterMapByVal();
        Map<String, Integer> wordFrequencies = new HashMap<>();
        wordFrequencies.put("apple", 3);
        wordFrequencies.put("banana", 1);
        wordFrequencies.put("cherry", 2);

        List<Map.Entry<String, Integer>> sortedEntries = sorter.SortByFrequency(wordFrequencies);

        assertEquals("apple", sortedEntries.get(0).getKey());
        assertEquals(3, sortedEntries.get(0).getValue());

        assertEquals("cherry", sortedEntries.get(1).getKey());
        assertEquals(2, sortedEntries.get(1).getValue());

        assertEquals("banana", sortedEntries.get(2).getKey());
        assertEquals(1, sortedEntries.get(2).getValue());
    }

    @Test
    void testSortByFrequency_EmptyMap() {
        SorterMapByVal sorter = new SorterMapByVal();
        Map<String, Integer> wordFrequencies = new HashMap<>();

        List<Map.Entry<String, Integer>> sortedEntries = sorter.SortByFrequency(wordFrequencies);

        assertTrue(sortedEntries.isEmpty());
    }

    @Test
    void testSortByFrequency_SingleEntry() {
        SorterMapByVal sorter = new SorterMapByVal();
        Map<String, Integer> wordFrequencies = new HashMap<>();
        wordFrequencies.put("apple", 3);

        List<Map.Entry<String, Integer>> sortedEntries = sorter.SortByFrequency(wordFrequencies);

        assertEquals(1, sortedEntries.size());
        assertEquals("apple", sortedEntries.get(0).getKey());
        assertEquals(3, sortedEntries.get(0).getValue());
    }
}