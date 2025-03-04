package ku.cs.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MusicianService {
    private Connection conn;

    public MusicianService(Connection conn) {
        this.conn = conn;
    }

    public String incrementMusicianPoint(String musicianUUID) {
        String query = "UPDATE Musician SET Point = Point + 1 WHERE UUID = ?";
        System.out.println("[DB] Executing SQL: " + query + " with UUID: " + musicianUUID);

        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, musicianUUID);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("[DB] Update successful! Points increased for: " + musicianUUID);
                return "คะแนนถูกเพิ่มแล้ว!";
            } else {
                System.out.println("[DB] No musician found with UUID: " + musicianUUID);
                return "ไม่พบ Musician";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "เกิดข้อผิดพลาด: " + e.getMessage();
        }
    }


}
