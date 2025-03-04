package NSU.ParserCSV;

import java.io.IOException;
import java.util.List;
import java.util.Map;

interface ICsvWriter {
    void Write(List<Map.Entry<String, Integer>> sortedWordFrequencies, int numWords) throws IOException;
}

