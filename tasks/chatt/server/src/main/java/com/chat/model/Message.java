package com.chat.model;

/**
 * Модель одного сообщения в чате.
 * Используется и для сохранения в БД, и для сериализации в JSON.
 */
public class Message {
    /** ID комнаты, в которой отправлено сообщение */
    private String roomId;
    /** Никнейм пользователя, отправившего сообщение */
    private String username;
    /** Время отправки в миллисекундах от эпохи */
    private long timestamp;
    /** Текст самого сообщения */
    private String text;

    /** Пустой конструктор нужен для десериализации JSON-библиотеками */
    public Message() {}

    /** Основной конструктор для ручного создания объекта */
    public Message(String roomId, String username, long timestamp, String text) {
        this.roomId = roomId;
        this.username = username;
        this.timestamp = timestamp;
        this.text = text;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
