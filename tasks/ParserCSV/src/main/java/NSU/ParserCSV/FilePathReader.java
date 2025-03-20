package NSU.ParserCSV;

import java.io.FileReader;
import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class FilePathReader implements IFilePathReader {
    private final String inputFilePath;
    private final String outputFilePath;

    /**
     * Конструктор, принимающий пути к входному и выходному файлам.
     * @param inputFilePath  Путь к входному файлу.
     * @param outputFilePath Путь к выходному файлу.
     * @throws IllegalArgumentException Если какой-либо из путей не является допустимым.
     */
    public FilePathReader(String inputFilePath, String outputFilePath) {
        if (inputFilePath == null || inputFilePath.isEmpty() || outputFilePath == null || outputFilePath.isEmpty()) {
            throw new IllegalArgumentException("Пути к файлам не должны быть пустыми.");
        }
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
    }

    /**
     * Конструктор, который запрашивает пути к файлам через консоль.
     */
    public FilePathReader() {
        Scanner consoleScanner = new Scanner(System.in);
        String inputPath;
        String outputPath;

        while (true) {
            try {
                System.out.print("Введите путь к входному файлу: ");
                inputPath = consoleScanner.nextLine();
                File inputFile = new File(inputPath);
                if (!inputFile.exists()) {
                    System.out.println("Файл не существует. Пожалуйста, введите корректный путь.");
                    continue;
                }

                System.out.print("Введите путь к выходному файлу: ");
                outputPath = consoleScanner.nextLine();

                break;
            } catch (Exception e) {
                System.out.println("Произошла ошибка: " + e.getMessage());
                System.out.println("Пожалуйста, попробуйте еще раз.");
            }
        }
        this.inputFilePath = inputPath;
        this.outputFilePath = outputPath;
        consoleScanner.close();
    }

    /**
     * Возвращает FileReader для чтения из файла.
     * @return FileReader для чтения из файла.
     * @throws FileNotFoundException Если файл не найден.
     */
    @Override
    public FileReader getFileReader() throws FileNotFoundException {
        File file = new File(inputFilePath);
        if (!file.exists()) {
            throw new FileNotFoundException("Входной файл не найден: " + inputFilePath);
        }
        return new FileReader(file);
    }

    /**
     * Возвращает FileWriter для записи в файл.
     * @return FileWriter для записи в файл.
     * @throws IOException Если произошла ошибка при создании FileWriter.
     */
    @Override
    public FileWriter getFileWriter() throws IOException {
        File file = new File(outputFilePath);
        return new FileWriter(file);
    }
}
