package NSU.ParserCSV;

import java.io.IOException;

public class Container {
    private IFilePathReader filePathReader;
    private IWordFrequencyCounter wordCounter;
    private ICsvWriter csvWriter;
    private ISorterMapByVal sorter;
    private CsvProcessor csvProcessor;

    public Container(String[] filePaths) {
        try {
            if (filePaths.length == 2) {
                this.filePathReader = new FilePathReader(filePaths[0], filePaths[1]);
            } else {
                this.filePathReader = new FilePathReader();
            }
            this.wordCounter = new WordFrequencyCounter();
            this.csvWriter = new CsvWriter(filePathReader.getFileWriter());
            this.sorter = new SorterMapByVal();
            this.csvProcessor = new CsvProcessor(filePathReader, wordCounter, csvWriter, sorter);
        } catch (IOException e) {
            System.err.println("Ошибка при записи в файл: " + e.getMessage());
        }
    }

    public CsvProcessor getCsvProcessor() {
        return csvProcessor;
    }
}
