package com.chat.client;

import jakarta.websocket.ContainerProvider;
import jakarta.websocket.DeploymentException;
import jakarta.websocket.Session;
import jakarta.websocket.WebSocketContainer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Отвечает за включение и работу клиента
 */
public class MainClient {
    /**
     * Точка входа в программу клиента
     */
    public static void main(String[] args) {
        runClient();
    }

    /**
     * Запускает клиента и
     * подключает клиента по IP 185.84.163.83, на порт 8080
     */
    public static void runClient() {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Session session = null; // <<< объявляем тут!

        try {
            System.out.print("Enter roomId: ");
            String roomId = reader.readLine().trim();
            System.out.print("Enter username: ");
            String username = reader.readLine().trim();

            String uri = "ws://185.84.163.83:8080/chat/" + roomId + "/" + username;
            ChatClientEndpoint clientEndpoint = new ChatClientEndpoint();
            session = container.connectToServer(clientEndpoint, new URI(uri)); // <<< тут просто присваиваем

            System.out.println("Connected to server. Type 'exit' to quit.");

            String message;
            while (session != null && session.isOpen()) {
                message = reader.readLine();

                if (message == null) break;

                message = message.trim();
                if (message.equalsIgnoreCase("exit")) {
                    break;
                }

                if (!message.isEmpty()) {
                    clientEndpoint.sendMessage(session, message);
                }
            }
        } catch (DeploymentException | URISyntaxException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (session != null && session.isOpen()) {
                    session.close();
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
