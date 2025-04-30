package com.chat.server;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;
import com.chat.worker.WorkerPool;
import com.chat.model.Message;
import com.chat.db.DatabaseManager;
import com.chat.worker.RoomManager;

/**
 * Сервер
 */
@ServerEndpoint("/chat/{roomId}/{username}")
public class ChatServerEndpoint {

    private String roomId;
    private String username;
    private static final Gson gson = new Gson();

    /**
     * Открывает соединение с клиентом
     * @param session сессия с клиентом
     */
    @OnOpen
    public void onOpen(Session session,
                       @PathParam("roomId") String roomId,
                       @PathParam("username") String username) {
        if (roomId == null || username == null) {
            System.err.println("Ошибка: roomId или username = null!");
            try {
                session.close(new CloseReason(CloseReason.CloseCodes.CANNOT_ACCEPT, "Missing roomId or username"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        try {
            this.roomId = roomId;
            this.username = username;

            WorkerPool.submit(() -> RoomManager.join(roomId, username, session));
            System.out.println("Connected: " + username + " to room: " + roomId);
            List<Message> hist = DatabaseManager.loadHistory(this.roomId);
            for (Message m : hist) {
                session.getAsyncRemote().sendText(m.getUsername() + ": " + m.getText());
            }
        } catch (Exception e) {
            System.out.println("Error: " + username + " to room: " + roomId);
        }
    }

    /**
     * Отправляет сообщение клиенту
     * @param messageText отправляемое сообщение
     * @param session сессия с клиентом, которому отправляется сообщение
     * @throws IOException
     */
    @OnMessage
    public void onMessage(String messageText, Session session) throws IOException {
        WorkerPool.submit(() -> {
            try {
                Message msg = new Message(
                        roomId,
                        username,
                        System.currentTimeMillis(),
                        messageText
                );

                DatabaseManager.saveMessage(msg);

                // Сериализация для отправки
                String payload = gson.toJson(msg);
                RoomManager.broadcast(roomId, username + ": " + messageText);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    /**
     * Закрывает соединение с клиентом
     * @param session сессия с клиентом, который отключается
     */
    @OnClose
    public void onClose(Session session) {
        WorkerPool.submit(() -> RoomManager.leave((String) session.getUserProperties().get("roomId"),
                (String) session.getUserProperties().get("username"),
                session));
        System.out.println("Connection closed: " + session.getId());
    }

    /**
     * Обрабатывает возникающие ошибки
     * @param session сессия с клиентом, с которым возникла ошибка
     * @param error ошибка
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.err.println("Error: " + error.getMessage());
    }
}

