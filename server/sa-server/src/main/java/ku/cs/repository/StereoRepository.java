package ku.cs.repository;

import ku.cs.entity.Stereo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class StereoRepository extends Repository {

    public StereoRepository(Connection connection) {
        super(connection);
    }

    public void createStereo(Stereo stereo) throws SQLException {
        try {
            this.statement = connection.createStatement();

            this.statement.executeUpdate(
                    String.format("INSERT INTO stereo (STID, NAME, TYPE_ID, UUID) VALUES ('%s','%s','%s','%s')",
                            stereo.getId(),
                            stereo.getName(),
                            stereo.getType_id(),
                            stereo.getOwner_id()));

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.statement.close();
        }
    }

    public List<Stereo> getStereosFromUUID(String uuid) throws SQLException {
        try {
            this.statement = connection.createStatement();

            this.resultSet = this.statement.executeQuery(
                    String.format(
                            "SELECT s.STID, s.NAME, s.TYPE_ID, u.NAME OWNERNAME, st.STNAME FROM stereo s JOIN user u ON u.UUID = s.UUID JOIN stereotype st ON st.TYPE_ID = s.TYPE_ID WHERE s.UUID = '%s';",
                            uuid));

            List<Stereo> result = new LinkedList<Stereo>();

            while (this.resultSet.next()) {
                String resultSTID = this.resultSet.getString("STID");
                String resultName = this.resultSet.getString("NAME");
                String resultTypeID = this.resultSet.getString("TYPE_ID");
                String resultOwnerName = this.resultSet.getString("OWNERNAME");
                String resultTypeName = this.resultSet.getString("STNAME");

                Stereo s = new Stereo();
                s.setId(resultSTID);
                s.setName(resultName);
                s.setOwner_id(uuid);
                s.setType_id(resultTypeID);
                s.setOwner_name(resultOwnerName);
                s.setType_name(resultTypeName);

                result.add(s);
            }

            return result;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            this.statement.close();
        }
    }

    public Stereo getStereosFromSTID(String stid) throws SQLException {
        try {
            this.statement = connection.createStatement();

            this.resultSet = this.statement.executeQuery(
                    String.format(
                            "SELECT s.NAME, s.TYPE_ID, s.UUID, u.NAME OWNERNAME, st.STNAME FROM stereo s JOIN user u ON u.UUID = s.UUID JOIN stereotype st ON st.TYPE_ID = s.TYPE_ID WHERE STID = '%s';",
                            stid));

            this.resultSet.next();
            String resultName = this.resultSet.getString("NAME");
            String resultTypeID = this.resultSet.getString("TYPE_ID");
            String resultUUID = this.resultSet.getString("UUID");
            String resultOwnerName = this.resultSet.getString("OWNERNAME");
            String resultTypeName = this.resultSet.getString("STNAME");

            Stereo s = new Stereo();
            s.setId(stid);
            s.setName(resultName);
            s.setOwner_id(resultUUID);
            s.setType_id(resultTypeID);
            s.setOwner_name(resultOwnerName);
            s.setType_name(resultTypeName);
            
            return s;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            this.statement.close();
        }
    }
}
