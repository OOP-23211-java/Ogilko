package com.chat.server;

import org.glassfish.tyrus.server.Server;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ArrayList;

import com.chat.db.DatabaseManager;
import com.chat.model.Message;

/**
 * Отвечает за включение и работу сервера
 */
public class MainServer {
    /**
     * Точка входа в программу сервера
     * @param args не используются
     */
    public static void main(String[] args) {
        runServer();
    }

    /**
     * Запускает сервер.
     * 0.0.0.0 - сервер будет доступен по всем IP-адресам, которые может иметь
     * (например, 127.0.0.1 - localhost).
     * 8080 - порт на котором сервере слушает.
     * / - путь
     */
    public static void runServer() {
        try {
            DatabaseManager.init();
        } catch (Exception e) {
            System.err.println("Ошибка инициализации базы данных: " + e.getMessage());
            System.err.println("Сервер не может быть запущен без базы данных. Завершение работы.");
            return;
        }
        Server server = new Server("0.0.0.0", 8080, "/", null, ChatServerEndpoint.class);
        try {
            server.start();
            System.out.println("Server started.");

            if (System.console() != null) {
                System.console().readLine();
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String line;
            while ((line = reader.readLine()) != null) {
                if ("exit".equalsIgnoreCase(line)) {
                    break;
                } else {
                    System.out.println("You typed: " + line + ". Type 'exit' to stop.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            server.stop();
            System.out.println("Server stopped.");
        }
    }
}

