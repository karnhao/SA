package ku.cs.repository;

import ku.cs.entity.Stereo;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
            throw e;
        } finally {
            this.statement.close();
        }
    }

    public List<Stereo> getStereosFromUUID(String uuid) throws SQLException {
        try {
            this.statement = connection.prepareStatement(
                    "SELECT s.STID, s.NAME, s.TYPE_ID, u.NAME OWNERNAME, st.STNAME FROM stereo s JOIN user u ON u.UUID = s.UUID JOIN stereotype st ON st.TYPE_ID = s.TYPE_ID WHERE s.UUID = ?;");
            ((PreparedStatement) this.statement).setString(1, uuid);
            this.resultSet = ((PreparedStatement) this.statement).executeQuery();

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

    public List<Stereo> getStereos() throws SQLException {
        try {
            this.statement = connection.prepareStatement(
                    "SELECT s.STID, s.NAME, s.TYPE_ID, u.UUID, u.NAME OWNERNAME, st.STNAME FROM stereo s JOIN user u ON u.UUID = s.UUID JOIN stereotype st ON st.TYPE_ID = s.TYPE_ID;");
            this.resultSet = ((PreparedStatement) this.statement).executeQuery();

            List<Stereo> result = new LinkedList<Stereo>();

            while (this.resultSet.next()) {
                String resultSTID = this.resultSet.getString("STID");
                String resultName = this.resultSet.getString("NAME");
                String resultTypeID = this.resultSet.getString("TYPE_ID");
                String resultOwnerName = this.resultSet.getString("OWNERNAME");
                String resultTypeName = this.resultSet.getString("STNAME");
                String resultOwner_id = this.resultSet.getString("UUID");

                Stereo s = new Stereo();
                s.setId(resultSTID);
                s.setName(resultName);
                s.setOwner_id(resultOwner_id);
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
        String query = "SELECT s.NAME, s.TYPE_ID, s.UUID, u.NAME AS OWNERNAME, st.STNAME " +
                "FROM stereo s " +
                "JOIN user u ON u.UUID = s.UUID " +
                "JOIN stereotype st ON st.TYPE_ID = s.TYPE_ID " +
                "WHERE STID = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, stid);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String resultName = rs.getString("NAME");
                    String resultTypeID = rs.getString("TYPE_ID");
                    String resultUUID = rs.getString("UUID");
                    String resultOwnerName = rs.getString("OWNERNAME");
                    String resultTypeName = rs.getString("STNAME");

                    Stereo s = new Stereo();
                    s.setId(stid);
                    s.setName(resultName);
                    s.setOwner_id(resultUUID);
                    s.setType_id(resultTypeID);
                    s.setOwner_name(resultOwnerName);
                    s.setType_name(resultTypeName);

                    return s;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    

    public List<Stereo> getStereosByTypeId(String typeId) {
        List<Stereo> stereos = new LinkedList<>();
        String query = "SELECT s.STID, s.NAME, s.UUID, s.TYPE_ID, u.NAME AS OWNERNAME, u.EMAIL_ADDRESS, u.PHONE_NUMBER, st.STNAME " +
                       "FROM stereo s " +
                       "JOIN user u ON u.UUID = s.UUID " +
                       "JOIN stereotype st ON st.TYPE_ID = s.TYPE_ID " +
                       "WHERE s.TYPE_ID = ?";
        try (PreparedStatement ps = this.connection.prepareStatement(query)) {

            ps.setString(1, typeId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Stereo stereo = new Stereo();
                stereo.setId(rs.getString("STID"));
                stereo.setName(rs.getString("NAME"));
                stereo.setOwner_id(rs.getString("UUID"));
                stereo.setType_id(rs.getString("TYPE_ID"));
                stereo.setOwner_name(rs.getString("OWNERNAME"));
                stereo.setOwner_email(rs.getString("EMAIL_ADDRESS"));
                stereo.setOwner_phone_number(rs.getString("PHONE_NUMBER"));
                stereo.setType_name(rs.getString("STNAME"));

                stereos.add(stereo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stereos;
    }
}
