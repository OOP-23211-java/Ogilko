package NSU.ParserCSV;

import java.io.FileReader;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class GetterStream {
    private final Scanner consoleScanner = new Scanner(System.in);

    /**
     * Получает InputStream для чтения данных из файла, путь к которому вводится через консоль.
     * Обеспечивает обработку исключений и повторный запрос пути при ошибке.
     * @return InputStream для чтения из файла, или null, если не удалось получить InputStream.
     */
    public FileReader getInputFile() {
        while (true) {
            try {
                System.out.print("Введите путь к входному файлу: ");
                String filePath = consoleScanner.nextLine();

                File file = new File(filePath);
                if (!file.exists()) {
                    System.out.println("Файл не существует. Пожалуйста, введите корректный путь.");
                    continue;
                }

                return new FileReader(file);
            } catch (FileNotFoundException e) {
                System.out.println("Файл не найден: " + e.getMessage());
                System.out.println("Пожалуйста, введите корректный путь к файлу.");
            }
        }
    }

    /**
     * Получает OutputStream для записи данных в файл, путь к которому вводится через консоль.
     * Обеспечивает обработку исключений и повторный запрос пути при ошибке.
     * @return OutputStream для записи в файл, или null, если не удалось получить OutputStream.
     */
    public FileWriter getOutputFile() {
        while (true) {
            try {
                System.out.print("Введите путь к выходному файлу: ");
                String filePath = consoleScanner.nextLine();

                File file = new File(filePath);

                return new FileWriter(file);
            } catch (Exception e) {
                System.out.println("Произошла непредвиденная ошибка: " + e.getMessage());
                System.out.println("Пожалуйста, попробуйте еще раз.");
            }
        }
    }

    /**
     * Закрывает Scanner для чтения из консоли.
     * Важно вызывать этот метод после завершения работы с FileIOHelper.
     */
    public void close() {
        consoleScanner.close();
    }
}
