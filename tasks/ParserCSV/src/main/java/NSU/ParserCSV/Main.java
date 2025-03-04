package NSU.ParserCSV;

public class Main {
    public static void main(String[] args) {
        Container container = new Container(args);
        CsvProcessor parser = container.getCsvProcessor();
        parser.processCsv();
    }
}