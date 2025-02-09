//import org.example.WordFrequencyCounter;
//import org.junit.jupiter.api.Test;
//
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class WordFrequencyCounterTest {
//
//    @Test
//    void calculateFrequencyWords_emptyArray_returnsEmptyMap() {
//        WordFrequencyCounter reader = new WordFrequencyCounter();
//        String[] words = {};
//        Map<String, Integer> result = reader.CalculateFrequencyWords(words);
//        assertTrue(result.isEmpty());
//        assertEquals(0, reader.getFrequencyText());
//    }
//
//    @Test
//    void calculateFrequencyWords_singleWord_returnsMapWithFrequencyOne() {
//        WordFrequencyCounter reader = new WordFrequencyCounter();
//        String[] words = {"hello"};
//        Map<String, Integer> result = reader.CalculateFrequencyWords(words);
//        assertEquals(1, result.get("hello"));
//        assertEquals(1, reader.getFrequencyText());
//    }
//
//    @Test
//    void calculateFrequencyWords_multipleWords_returnsCorrectFrequencies() {
//        WordFrequencyCounter reader = new WordFrequencyCounter();
//        String[] words = {"hello", "world", "hello"};
//        Map<String, Integer> result = reader.CalculateFrequencyWords(words);
//        assertEquals(2, result.get("hello"));
//        assertEquals(1, result.get("world"));
//        assertEquals(3, reader.getFrequencyText());
//    }
//}
//
