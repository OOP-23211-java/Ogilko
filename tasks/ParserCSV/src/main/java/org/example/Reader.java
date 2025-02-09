package org.example;

import java.io.FileReader;
import java.io.BufferedReader;
import java.util.Scanner;
import java.io.IOException;

public class Reader {
    private BufferedReader reader;

    public Reader() {
        try {
            final Scanner scanner = new Scanner(System.in);
            System.out.print("Enter path to input file: ");
            String filePath = scanner.nextLine();
            reader = new BufferedReader(new FileReader(filePath));

        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }
    }

    public String getLine() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
            return null;
        }
    }
}
