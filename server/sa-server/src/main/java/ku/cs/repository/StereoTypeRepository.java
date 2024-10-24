package ku.cs.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import ku.cs.entity.StereoType;

public class StereoTypeRepository extends Repository {

    public StereoTypeRepository(Connection connection) {
        super(connection);
    }

    public List<StereoType> getAllType() throws SQLException {
        LinkedList<StereoType> roles = new LinkedList<StereoType>();
        try {
            this.statement = connection.createStatement();

            // ทำ SQL Injection ไม่ได้เพราะระบบจะทำงานเพียง statement เดียว
            this.resultSet = this.statement.executeQuery(
                    "SELECT TYPE_ID, STNAME FROM stereotype;");

            while (this.resultSet.next()) {
                StereoType role = new StereoType();
                String resultID = resultSet.getString("TYPE_ID");
                String resultName = resultSet.getString("STNAME");

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
