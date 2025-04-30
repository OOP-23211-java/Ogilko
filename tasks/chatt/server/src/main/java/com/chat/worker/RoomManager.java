package com.chat.worker;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import jakarta.websocket.Session;
import com.chat.db.DatabaseManager;

public class RoomManager {
    // Карта: roomId → набор активных сессий
    private static final Map<String, Map<String, Session>> rooms = new ConcurrentHashMap<>();

    public static void join(String roomId, String username, Session session) {
        try {
            Map<String, Session> map = rooms.computeIfAbsent(
                    roomId,
                    key -> new ConcurrentHashMap<>()
            );

            if (map.containsKey(username)) {
                session.getAsyncRemote().sendText(
                        "Ошибка: пользователь с именем «" + username +
                                "» уже в комнате. Пожалуйста, выберите другой ник.");
                session.close();
                return;
            }

            map.put(username, session);

            DatabaseManager.createRoom(roomId);
            DatabaseManager.addUserToRoom(username, roomId);
        } catch (Exception e) {
            System.err.println("Ошибка создания комнаты: " + e.getMessage());
            return;
        }
    }

    public static void leave(String roomId, String username, Session session) {
        try {
            Map<String, Session> map = rooms.get(roomId);
            if (map != null) {
                map.remove(username);
                if (map.isEmpty()) {
                    rooms.remove(roomId);
                }
            }

            DatabaseManager.removeUserFromRoom(username, roomId);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public static boolean isUserInRoom(String username, String roomId) {
        Map<String, Session> map = rooms.get(roomId);
        return map != null && map.containsKey(username);
    }

    public static Map<String, Session> getSessions(String roomId) {
        return rooms.get(roomId);
    }

    public static void broadcast(String roomId, String text) {
        Map<String, Session> ss = getSessions(roomId);
        if (ss == null) return;
        for (Session s : ss.values()) {
            s.getAsyncRemote().sendText(text);
        }
    }
}
