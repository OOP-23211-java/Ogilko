package client;

import jakarta.websocket.ContainerProvider;
import jakarta.websocket.DeploymentException;
import jakarta.websocket.Session;
import jakarta.websocket.WebSocketContainer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

public class MainClient {

    public static void main(String[] args) {
        runClient();
    }

    public static void runClient() {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        String uri = "ws://localhost:8080/chat";
        Session session = null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            ChatClientEndpoint clientEndpoint = new ChatClientEndpoint();
            session = container.connectToServer(clientEndpoint, new URI(uri));
            System.out.println("Connected to server. Type 'exit' to quit.");

            String message;
            while (session != null && session.isOpen()) {
                System.out.print("Enter message: ");
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
