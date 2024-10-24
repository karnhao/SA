package ku.cs.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import ku.cs.entity.MusicianRole;

public class MusicianRoleRepository extends Repository {

    public MusicianRoleRepository(Connection connection) {
        super(connection);
    }

    public List<MusicianRole> getAllRole() throws SQLException {
        LinkedList<MusicianRole> roles = new LinkedList<MusicianRole>();
        try {
            this.statement = connection.createStatement();

            // ทำ SQL Injection ไม่ได้เพราะระบบจะทำงานเพียง statement เดียว
            this.resultSet = this.statement.executeQuery(
                    "SELECT (ROLE_ID, ROLE_NAME) FROM musicianrole;");

            MusicianRole role = new MusicianRole();
            while (this.resultSet.next()) {
                String resultID = resultSet.getString("ROLE_ID");
                String resultName = resultSet.getString("ROLE_NAME");

                role.setId(resultID);
                role.setName(resultName);

                roles.add(role);
            }

            return roles;

        } catch (SQLException e) {
            return null;
        } finally {
            this.statement.close();
        }
    }

}
