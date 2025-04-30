package com.chat.client;

import jakarta.websocket.*;
import java.io.IOException;

/**
 * Клиент
 */
@ClientEndpoint
public class ChatClientEndpoint {
    private Session session;

    /**
     * Под
     * @param session сессия с сервером
     */
    @OnOpen
    public void onOpen(Session session) {

        this.session = session;
        try {
            session.getBasicRemote().sendText("Всем приветики)\nЧем промышляете?");
        } catch (IOException e) {
            System.err.println("Error sending initial message: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("Connected to server: " + session.getId());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println(message);
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        this.session = null;
        System.out.println("\nDisconnected: " + closeReason.getReasonPhrase());
        System.out.println("Close code: " + closeReason.getCloseCode().getCode());
        System.exit(0);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        System.err.println("Error: " + error.getMessage());
        this.session = null;
    }

    public void sendMessage(Session session, String message) throws IOException {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            System.err.println("Send error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

