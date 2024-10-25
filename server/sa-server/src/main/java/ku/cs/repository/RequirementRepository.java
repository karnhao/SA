package ku.cs.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import ku.cs.entity.MusicianRequirement;
import ku.cs.entity.StereoRequirement;

public class RequirementRepository extends Repository {

    public RequirementRepository(Connection connection) {
        super(connection);
    }

    public void addMusicianRequirement(String event_id, String role_id, int quantity) throws SQLException {
        try {
            this.statement = this.connection.createStatement();

            this.statement.executeUpdate(String.format(
                    "INSERT INTO musicianrequirement (EID, ROLE_ID, QUANTITY) VALUES ('%s','%s',%d)",
                    event_id, role_id, quantity));
        } finally {
            this.statement.close();
        }

    }

    public void addStereoRequirement(String event_id, String type_id, int quantity) throws SQLException {
        try {
            this.statement = this.connection.createStatement();

            this.statement.executeUpdate(String.format(
                    "INSERT INTO stereorequirement (EID, TYPE_ID, QUANTITY) VALUES ('%s','%s',%d)",
                    event_id, type_id, quantity));
        } finally {
            this.statement.close();
        }
    }

    public List<MusicianRequirement> getMusicianRequirementList(String eid) throws SQLException {
        LinkedList<MusicianRequirement> requirements = new LinkedList<MusicianRequirement>();
        try {
            this.statement = connection.createStatement();

            this.resultSet = this.statement.executeQuery(String.format(
                    "SELECT m.ROLE_ID, m.QUANTITY, n.ROLE_NAME FROM musicianrequirement m JOIN musicianrole n ON m.ROLE_ID = n.ROLE_ID WHERE EID = '%s';", eid));

            while (this.resultSet.next()) {
                MusicianRequirement r = new MusicianRequirement();
                String resultID = resultSet.getString("ROLE_ID");
                String resultName = resultSet.getString("ROLE_NAME");
                int resultQuantity = resultSet.getInt("QUANTITY");

                r.setMusician_id(resultID);
                r.setQuantity(resultQuantity);
                r.setRoleName(resultName);

                requirements.add(r);
            }

            return requirements;

        } catch (SQLException e) {
            return null;
        } finally {
            this.statement.close();
        }
    }

    public List<StereoRequirement> getStereoRequirementList(String eid) throws SQLException {
        LinkedList<StereoRequirement> requirements = new LinkedList<StereoRequirement>();
        try {
            this.statement = connection.createStatement();

            this.resultSet = this.statement.executeQuery(String.format(
                    "SELECT m.TYPE_ID, m.QUANTITY, n.STNAME FROM stereorequirement m JOIN stereotype n ON m.TYPE_ID = n.TYPE_ID WHERE EID = '%s';", eid));

            while (this.resultSet.next()) {
                StereoRequirement r = new StereoRequirement();
                String resultID = resultSet.getString("TYPE_ID");
                String resultName = resultSet.getString("STNAME");
                int resultQuantity = resultSet.getInt("QUANTITY");

                r.setType_id(resultID);
                r.setQuantity(resultQuantity);
                r.setTypeName(resultName);

                requirements.add(r);
            }

            return requirements;

        } catch (SQLException e) {
            return null;
        } finally {
            this.statement.close();
        }
    }

}
