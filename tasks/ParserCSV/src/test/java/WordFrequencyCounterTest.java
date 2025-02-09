import org.example.WordFrequencyCounter;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class WordFrequencyCounterTest {
    @Test
    void testCalculateFrequencyWords_emptyArray() {
        WordFrequencyCounter counter = new WordFrequencyCounter();
        String[] words = {};
        Map<String, Integer> frequency = new HashMap<>();

        counter.CalculateFrequencyWords(frequency, words);

        assertTrue(frequency.isEmpty());
        assertEquals(0, counter.getFrequencyText());
    }

    @Test
    void testCalculateFrequencyWords_singleWord() {
        WordFrequencyCounter counter = new WordFrequencyCounter();
        String[] words = {"hello"};
        Map<String, Integer> frequency = new HashMap<>();

        counter.CalculateFrequencyWords(frequency, words);

        assertEquals(1, frequency.size());
        assertEquals(1, frequency.get("hello"));
        assertEquals(1, counter.getFrequencyText());
    }

    @Test
    void testCalculateFrequencyWords_multipleWords() {
        WordFrequencyCounter counter = new WordFrequencyCounter();
        String[] words = {"hello", "world", "hello"};
        Map<String, Integer> frequency = new HashMap<>();

        counter.CalculateFrequencyWords(frequency, words);

        assertEquals(2, frequency.size());
        assertEquals(2, frequency.get("hello"));
        assertEquals(1, frequency.get("world"));
        assertEquals(3, counter.getFrequencyText());
    }

    @Test
    void testCalculateFrequencyWords_differentCase() {
        WordFrequencyCounter counter = new WordFrequencyCounter();
        String[] words = {"Hello", "hello", "HELLO"};
        Map<String, Integer> frequency = new HashMap<>();

        counter.CalculateFrequencyWords(frequency, words);

        assertEquals(3, frequency.size());
        assertEquals(1, frequency.get("Hello"));
        assertEquals(1, frequency.get("hello"));
        assertEquals(1, frequency.get("HELLO"));
        assertEquals(3, counter.getFrequencyText());
    }

    @Test
    void testCalculateFrequencyWords_withPunctuation() {
        WordFrequencyCounter counter = new WordFrequencyCounter();
        String[] words = {"hello,", "world!", "hello."};
        Map<String, Integer> frequency = new HashMap<>();

        counter.CalculateFrequencyWords(frequency, words);

        assertEquals(3, frequency.size());
        assertEquals(1, frequency.get("hello,"));
        assertEquals(1, frequency.get("world!"));
        assertEquals(1, frequency.get("hello."));
        assertEquals(3, counter.getFrequencyText());
    }
}

