package ku.cs.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MusicianRepository {
    private Connection conn;

    public MusicianRepository(Connection conn) {
        this.conn = conn;
    }

    public String incrementPoint(String uuid) {
        String sql = "UPDATE musician SET Point = Point + 1 WHERE UUID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, uuid);
            int affectedRows = stmt.executeUpdate();

            conn.commit();

            if (affectedRows > 0) {
                return "Musician rated successfully";
            } else {
                throw new RuntimeException("Musician not found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Database error: " + e.getMessage());
        }
    }

}
