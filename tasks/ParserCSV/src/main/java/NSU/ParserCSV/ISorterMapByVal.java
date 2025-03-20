package NSU.ParserCSV;

import java.util.List;
import java.util.Map;

interface ISorterMapByVal {
    List<Map.Entry<String, Integer>> SortByFrequency(Map<String, Integer> wordFrequencies);
}

