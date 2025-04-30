package com.chat.db;

import java.sql.*;  // JDBC: Connection, DriverManager, Statement, SQLException
import java.util.List;
import java.util.ArrayList;
import com.chat.model.Message;
/**
 * DatabaseManager — отвечает за всё, что связано с базой данных.
 * 1) Открывает соединение с файлом SQLite
 * 2) При необходимости создаёт таблицы
 * 3) Содержит методы для CRUD-операций (пока только init())
 */
public class DatabaseManager {

    private static Connection conn;

    /**
     * Инициализация базы — вызывается один раз при старте сервера.
     * Делает:
     *  1) DriverManager.getConnection(...) — открывает или создаёт файл chat.db
     *  2) Через Statement выполняет SQL, создающий таблицы, если их ещё нет
     */
    public static void init() throws SQLException {
        conn = DriverManager.getConnection("jdbc:sqlite:chat.db");

        try (Statement st = conn.createStatement()) {
            String sql =
                    "CREATE TABLE IF NOT EXISTS rooms (" +
                            "  id TEXT PRIMARY KEY" +
                            ");" +
                            "CREATE TABLE IF NOT EXISTS users (" +
                            "  username TEXT," +
                            "  room_id TEXT," +
                            "  PRIMARY KEY(username, room_id)" +
                            ");" +
                            "CREATE TABLE IF NOT EXISTS messages (" +
                            "  id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "  room_id TEXT," +
                            "  username TEXT," +
                            "  timestamp INTEGER," +
                            "  text TEXT" +
                            ");";

            st.executeUpdate(sql);
            System.out.println("[DB] Таблицы созданы или уже существуют.");
        }
    }

    /**
     * Проверяет, существует ли комната с заданным id.
     * @param roomId — идентификатор комнаты
     * @return true, если строка с таким id найдена в таблице rooms
     * @throws SQLException при ошибке работы с БД
     */
    public static boolean roomExists(String roomId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM rooms WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, roomId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    /**
     * Создаёт новую комнату с заданным id.
     * Если комната уже есть, то INSERT IGNORE ничего не изменит.
     * @param roomId — идентификатор комнаты
     * @throws SQLException при ошибке работы с БД
     */
    public static void createRoom(String roomId) throws SQLException {
        String sql = "INSERT OR IGNORE INTO rooms(id) VALUES(?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, roomId);
            ps.executeUpdate();
        }
    }

    /**
     * Проверяет, сидит ли уже пользователь в комнате.
     * @param username — никнейм
     * @param roomId   — id комнаты
     * @return true, если пара (username, roomId) есть в таблице users
     */
    public static boolean isUserInRoom(String username, String roomId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ? AND room_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, roomId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    /**
     * Добавляет пользователя в комнату.
     * Если он уже там — ничего не сломается (INSERT OR IGNORE).
     * @param username — никнейм
     * @param roomId   — id комнаты
     */
    public static void addUserToRoom(String username, String roomId) throws SQLException {
        String sql = "INSERT OR IGNORE INTO users(username, room_id) VALUES(?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, roomId);
            ps.executeUpdate();
        }
    }

    /**
     * Удаляет пользователя из комнаты.
     * @param username — никнейм
     * @param roomId   — id комнаты
     */
    public static void removeUserFromRoom(String username, String roomId) throws SQLException {
        String sql = "DELETE FROM users WHERE username = ? AND room_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, roomId);
            ps.executeUpdate();
        }
    }

    public static void saveMessage(Message msg) throws SQLException {
        String sql = "INSERT INTO messages(room_id, username, timestamp, text) VALUES(?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, msg.getRoomId());
            ps.setString(2, msg.getUsername());
            ps.setLong(3, msg.getTimestamp());
            ps.setString(4, msg.getText());
            ps.executeUpdate();
        }
    }

    public static List<Message> loadHistory(String roomId) throws SQLException {
        String sql = "SELECT room_id, username, timestamp, text\n" +
                "  FROM messages\n" +
                " WHERE room_id = ?\n" +
                " ORDER BY timestamp ASC";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, roomId);
            try (ResultSet rs = ps.executeQuery()) {
                List<Message> history = new ArrayList<>();
                while (rs.next()) {
                    Message msg = new Message();
                    msg.setRoomId(rs.getString("room_id"));
                    msg.setUsername(rs.getString("username"));
                    msg.setTimestamp(rs.getLong("timestamp"));
                    msg.setText(rs.getString("text"));
                    history.add(msg);
                }
                return history;
            }
        }
    }
}
