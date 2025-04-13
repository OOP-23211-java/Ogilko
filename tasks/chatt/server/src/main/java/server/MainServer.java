package server;

import org.glassfish.tyrus.server.Server;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MainServer {

    public static void main(String[] args) {
        runServer();
    }

    public static void runServer() {
        Server server = new Server("localhost", 8080, "/", null, ChatServerEndpoint.class);
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
