package NSU.ParserCSV;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

interface IFilePathReader {
    FileReader getFileReader() throws FileNotFoundException;
    FileWriter getFileWriter() throws IOException;
}

