package ku.cs.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.List;
import java.util.LinkedList;

import java.sql.PreparedStatement;

import ku.cs.entity.Event;
import ku.cs.entity.Musician;
import ku.cs.entity.Stereo;
import ku.cs.entity.User;

public class EventRepository extends Repository {

    public EventRepository(Connection connection) {
        super(connection);
    }

    public Event getEventByEID(String eid) throws SQLException {
        try {
            this.statement = connection.createStatement();

            this.resultSet = this.statement.executeQuery(
                    String.format(
                            "SELECT EID, START_DATE, END_DATE, START_TIME, END_TIME, STATUS, TITLE, DESCRIPTION, UUID FROM event WHERE EID = '%s';",
                            eid));

            this.resultSet.next();
            String resultEID = this.resultSet.getString("EID");
            Date resultStartDate = this.resultSet.getDate("START_DATE");
            Date resultEndDate = this.resultSet.getDate("END_DATE");
            Time resultStartTime = this.resultSet.getTime("START_TIME");
            Time resultEndTime = this.resultSet.getTime("END_TIME");
            String resultStatus = this.resultSet.getString("STATUS");
            String resultTitle = this.resultSet.getString("TITLE");
            String resultDescription = this.resultSet.getString("DESCRIPTION");
            String resultOwnerUUID = this.resultSet.getString("UUID");

            Event event = new Event();
            event.setId(resultEID);
            event.setTitle(resultTitle);
            event.setDescription(resultDescription);
            event.setStartDateTime(LocalDateTime.of(resultStartDate.toLocalDate(), resultStartTime.toLocalTime()));
            event.setEndDateTime(LocalDateTime.of(resultEndDate.toLocalDate(), resultEndTime.toLocalTime()));
            event.setStatus(resultStatus);
            event.setOwnerID(resultOwnerUUID);

            return event;

        } catch (SQLException e) {
            return null;
        } finally {
            this.statement.close();
        }
    }

    public void addMusicianRequest(String uuid, String eid, String role_id) throws SQLException {
        try {
            this.statement = connection.prepareStatement(
                    "INSERT INTO musicianeventmap (UUID, EID, ROLE_ID, STATUS) VALUES (?, ?, ?, 'await');");
            ((PreparedStatement) this.statement).setString(1, uuid);
            ((PreparedStatement) this.statement).setString(2, eid);
            ((PreparedStatement) this.statement).setString(3, role_id);

            ((PreparedStatement) this.statement).executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            this.statement.close();
        }
    }

    public void addStereoRequest(String eid, String stid) throws SQLException {
        try {
            this.statement = connection.prepareStatement(
                    "INSERT INTO stereoeventmap (EID, STID, STATUS) VALUES (?, ?, 'await')");
            ((PreparedStatement) this.statement).setString(1, eid);
            ((PreparedStatement) this.statement).setString(2, stid);

            ((PreparedStatement) this.statement).executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            this.statement.close();
        }
    }

    public List<Musician> getMusicianFromEventID(String eid) throws SQLException {
        try {
            this.statement = connection.prepareStatement(
                    "SELECT mm.STATUS, mm.ROLE_ID, mm.UUID, mr.ROLE_NAME, u.NAME, u.PHONE_NUMBER, u.EMAIL_ADDRESS FROM musicianeventmap mm INNER JOIN musicianrole mr ON mm.ROLE_ID = mr.ROLE_ID INNER JOIN user u ON mm.UUID = u.UUID WHERE mm.EID = ?;");

            ((PreparedStatement) this.statement).setString(1, eid);

            this.resultSet = ((PreparedStatement) this.statement).executeQuery();

            LinkedList<Musician> result = new LinkedList<>();
            while (this.resultSet.next()) {
                String resultStatus = this.resultSet.getString("STATUS");
                String resultName = this.resultSet.getString("NAME");
                String resultPhoneNumber = this.resultSet.getString("PHONE_NUMBER");
                String resultEmailAddress = this.resultSet.getString("EMAIL_ADDRESS");
                String resultRoleID = this.resultSet.getString("ROLE_ID");
                String resultRoleName = this.resultSet.getString("ROLE_NAME");
                String resultUUID = this.resultSet.getString("UUID");

                Musician m = new Musician(new User());
                m.setStatus(resultStatus);
                m.setName(resultName);
                m.setPhone_number(resultPhoneNumber);
                m.setEmail(resultEmailAddress);
                m.setMusicianRoleID(resultRoleID);
                m.setMusicianRoleName(resultRoleName);
                m.setUuid(resultUUID);

                result.add(m);
            }

            return result;
        } catch (SQLException e) {
            return null;
        } finally {
            this.statement.close();
        }
    }

    public List<Stereo> getStereoFromEventID(String eid) throws SQLException {
        try {
            this.statement = connection.prepareStatement(
                    "SELECT sm.STATUS, sm.STID, st.STNAME, st.TYPE_ID, s.NAME STEREO_NAME, u.NAME OWNER_NAME, u.PHONE_NUMBER OWNER_PHONE_NUMBER, u.EMAIL_ADDRESS OWNER_EMAIL_ADDRESS, u.UUID\r\n" +
                            "FROM stereoeventmap sm\r\n" +
                            "INNER JOIN stereo s ON sm.STID = s.STID\r\n" +
                            "INNER JOIN stereotype st ON s.TYPE_ID = st.TYPE_ID\r\n" +
                            "INNER JOIN user u ON s.UUID = u.UUID WHERE sm.EID = ?;");

            ((PreparedStatement) this.statement).setString(1, eid);

            this.resultSet = ((PreparedStatement) this.statement).executeQuery();

            LinkedList<Stereo> result = new LinkedList<>();
            while (this.resultSet.next()) {
                String resultStatus = this.resultSet.getString("STATUS");
                String resultStereoID = this.resultSet.getString("STID");
                String resultTypeName = this.resultSet.getString("STNAME");
                String resultTypeID = this.resultSet.getString("TYPE_ID");
                String reusltStereoName = this.resultSet.getString("STEREO_NAME");
                String resultOwnerName = this.resultSet.getString("OWNER_NAME");
                String resultPhoneNumber = this.resultSet.getString("OWNER_PHONE_NUMBER");
                String resultEmailAddress = this.resultSet.getString("OWNER_EMAIL_ADDRESS");
                String resultOwnerID = this.resultSet.getString("UUID");

                Stereo stereo = new Stereo();
                stereo.setId(resultStereoID);
                stereo.setName(reusltStereoName);
                stereo.setOwner_id(resultOwnerID);
                stereo.setOwner_name(resultOwnerName);
                stereo.setType_id(resultTypeID);
                stereo.setType_name(resultTypeName);
                stereo.setStatus(resultStatus);
                stereo.setOwner_phone_number(resultPhoneNumber);
                stereo.setOwner_email(resultEmailAddress);

                result.add(stereo);
            }

            return result;
        } catch (SQLException e) {
            return null;
        } finally {
            this.statement.close();
        }
    }

    public List<Event> getAllEventByUUID(String uuid) throws SQLException {
        try {
            this.statement = connection.prepareStatement(
                    "SELECT EID, START_DATE, END_DATE, START_TIME, END_TIME, STATUS, TITLE, DESCRIPTION, UUID FROM event WHERE UUID = ?;");
            ((PreparedStatement) this.statement).setString(1, uuid);
            this.resultSet = ((PreparedStatement) this.statement).executeQuery();

            List<Event> result = new LinkedList<Event>();

            while (this.resultSet.next()) {
                String resultEID = this.resultSet.getString("EID");
                Date resultStartDate = this.resultSet.getDate("START_DATE");
                Date resultEndDate = this.resultSet.getDate("END_DATE");
                Time resultStartTime = this.resultSet.getTime("START_TIME");
                Time resultEndTime = this.resultSet.getTime("END_TIME");
                String resultStatus = this.resultSet.getString("STATUS");
                String resultTitle = this.resultSet.getString("TITLE");
                String resultDescription = this.resultSet.getString("DESCRIPTION");

                Event event = new Event();
                event.setId(resultEID);
                event.setTitle(resultTitle);
                event.setDescription(resultDescription);
                event.setStartDateTime(LocalDateTime.of(resultStartDate.toLocalDate(), resultStartTime.toLocalTime()));
                event.setEndDateTime(LocalDateTime.of(resultEndDate.toLocalDate(), resultEndTime.toLocalTime()));
                event.setStatus(resultStatus);
                event.setOwnerID(uuid);

                result.add(event);
            }

            return result;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            this.statement.close();
        }
    }

    public void createEvent(String ownerUUID, Event event) throws SQLException {
        try {
            this.statement = connection.createStatement();
            this.statement.executeUpdate(
                    String.format(
                            "INSERT INTO event (EID, START_DATE, END_DATE, START_TIME, END_TIME, STATUS, TITLE, DESCRIPTION, UUID) VALUES ('%s','%s','%s','%s','%s','%s','%s','%s','%s');",
                            event.getId(),
                            Date.valueOf(event.getStartDateTime().toLocalDate()).toString(),
                            Date.valueOf(event.getEndDateTime().toLocalDate()).toString(),
                            Time.valueOf(event.getStartDateTime().toLocalTime()).toString(),
                            Time.valueOf(event.getEndDateTime().toLocalTime()).toString(),
                            event.getStatus(),
                            event.getTitle(),
                            event.getDescription(),
                            ownerUUID));
        } finally {
            this.statement.close();
        }
    }

    public List<Event> getAllEvent() throws SQLException {
        try {
            this.statement = connection.createStatement();

            this.resultSet = this.statement.executeQuery(
                    "SELECT EID, START_DATE, END_DATE, START_TIME, END_TIME, STATUS, TITLE, DESCRIPTION, UUID FROM event;");

            List<Event> result = new LinkedList<Event>();

            while (this.resultSet.next()) {
                String resultEID = this.resultSet.getString("EID");
                Date resultStartDate = this.resultSet.getDate("START_DATE");
                Date resultEndDate = this.resultSet.getDate("END_DATE");
                Time resultStartTime = this.resultSet.getTime("START_TIME");
                Time resultEndTime = this.resultSet.getTime("END_TIME");
                String resultStatus = this.resultSet.getString("STATUS");
                String resultTitle = this.resultSet.getString("TITLE");
                String resultDescription = this.resultSet.getString("DESCRIPTION");
                String resultOwnerUUID = this.resultSet.getString("UUID");

                Event event = new Event();
                event.setId(resultEID);
                event.setTitle(resultTitle);
                event.setDescription(resultDescription);
                event.setStartDateTime(LocalDateTime.of(resultStartDate.toLocalDate(), resultStartTime.toLocalTime()));
                event.setEndDateTime(LocalDateTime.of(resultEndDate.toLocalDate(), resultEndTime.toLocalTime()));
                event.setStatus(resultStatus);
                event.setOwnerID(resultOwnerUUID);

                result.add(event);
            }

            return result;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            this.statement.close();
        }
    }

    public List<Event> getEventsByMusicianUUID(String musicianUUID) {
        List<Event> events = new LinkedList<>();
        String query = "SELECT e.EID, e.START_DATE, e.END_DATE, e.START_TIME, e.END_TIME, e.STATUS, e.TITLE, e.DESCRIPTION, e.UUID " +
                       "FROM event e " +
                       "JOIN musicianeventmap mem ON e.EID = mem.EID " +
                       "WHERE mem.UUID = ?";

        try (PreparedStatement ps = this.connection.prepareStatement(query)) {

            ps.setString(1, musicianUUID);
            this.resultSet = ps.executeQuery();

            while (this.resultSet.next()) {
                String resultEID = this.resultSet.getString("EID");
                Date resultStartDate = this.resultSet.getDate("START_DATE");
                Date resultEndDate = this.resultSet.getDate("END_DATE");
                Time resultStartTime = this.resultSet.getTime("START_TIME");
                Time resultEndTime = this.resultSet.getTime("END_TIME");
                String resultStatus = this.resultSet.getString("STATUS");
                String resultTitle = this.resultSet.getString("TITLE");
                String resultDescription = this.resultSet.getString("DESCRIPTION");
                String resultOwnerUUID = this.resultSet.getString("UUID");

                Event event = new Event();
                event.setId(resultEID);
                event.setTitle(resultTitle);
                event.setDescription(resultDescription);
                event.setStartDateTime(LocalDateTime.of(resultStartDate.toLocalDate(), resultStartTime.toLocalTime()));
                event.setEndDateTime(LocalDateTime.of(resultEndDate.toLocalDate(), resultEndTime.toLocalTime()));
                event.setStatus(resultStatus);
                event.setOwnerID(resultOwnerUUID);

                events.add(event);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return events;
    }

    public List<Event> getEventsByStereoOwnerUUID(String musicianUUID) {
        List<Event> events = new LinkedList<>();
        String query = "SELECT e.EID, e.START_DATE, e.END_DATE, e.START_TIME, e.END_TIME, e.STATUS, e.TITLE, e.DESCRIPTION, e.UUID " +
                       "FROM event e " +
                       "JOIN stereoeventmap sem ON e.EID = sem.EID " +
                       "JOIN stereo s ON sem.STID = s.STID " +
                       "WHERE s.UUID = ?";

        try (PreparedStatement ps = this.connection.prepareStatement(query)) {

            ps.setString(1, musicianUUID);
            this.resultSet = ps.executeQuery();

            while (this.resultSet.next()) {
                String resultEID = this.resultSet.getString("EID");
                Date resultStartDate = this.resultSet.getDate("START_DATE");
                Date resultEndDate = this.resultSet.getDate("END_DATE");
                Time resultStartTime = this.resultSet.getTime("START_TIME");
                Time resultEndTime = this.resultSet.getTime("END_TIME");
                String resultStatus = this.resultSet.getString("STATUS");
                String resultTitle = this.resultSet.getString("TITLE");
                String resultDescription = this.resultSet.getString("DESCRIPTION");
                String resultOwnerUUID = this.resultSet.getString("UUID");

                Event event = new Event();
                event.setId(resultEID);
                event.setTitle(resultTitle);
                event.setDescription(resultDescription);
                event.setStartDateTime(LocalDateTime.of(resultStartDate.toLocalDate(), resultStartTime.toLocalTime()));
                event.setEndDateTime(LocalDateTime.of(resultEndDate.toLocalDate(), resultEndTime.toLocalTime()));
                event.setStatus(resultStatus);
                event.setOwnerID(resultOwnerUUID);

                events.add(event);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return events;
    }

    public boolean acceptMusicianEvent(String uuid, String eid, String role_id) {
        String updateQuery = "UPDATE musicianeventmap SET STATUS = 'promise' WHERE UUID = ? AND EID = ? AND ROLE_ID = ?";
        
        try (PreparedStatement ps = this.connection.prepareStatement(updateQuery)) {

            ps.setString(1, uuid);
            ps.setString(2, eid);
            ps.setString(3, role_id);
            int rowsUpdated = ps.executeUpdate();

            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean rejectMusicianEvent(String uuid, String eid, String role_id) {
        String updateQuery = "UPDATE musicianeventmap SET STATUS = 'reject' WHERE UUID = ? AND EID = ? AND ROLE_ID = ?";
        
        try (PreparedStatement ps = this.connection.prepareStatement(updateQuery)) {

            ps.setString(1, uuid);
            ps.setString(2, eid);
            ps.setString(3, role_id);
            int rowsUpdated = ps.executeUpdate();

            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean acceptStereoEvent(String stid, String eid) {
        String updateQuery = "UPDATE stereoeventmap SET STATUS = 'promise' WHERE STID = ? AND EID = ?";
        try (
             PreparedStatement ps = this.connection.prepareStatement(updateQuery)) {

            ps.setString(1, stid);
            ps.setString(2, eid);
            int rowsUpdated = ps.executeUpdate();

            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean rejectStereoEvent(String stid, String eid) {
        String updateQuery = "UPDATE stereoeventmap SET STATUS = 'reject' WHERE STID = ? AND EID = ?";
        try (
             PreparedStatement ps = this.connection.prepareStatement(updateQuery)) {

            ps.setString(1, stid);
            ps.setString(2, eid);
            int rowsUpdated = ps.executeUpdate();

            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean approveEvent(String eid) {
        String updateQuery = "UPDATE event SET STATUS = 'done' WHERE EID = ?";
        
        try (PreparedStatement ps = this.connection.prepareStatement(updateQuery)) {

            ps.setString(1, eid);
            int rowsUpdated = ps.executeUpdate();

            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean cancelEvent(String eid) {
        String updateQuery = "UPDATE event SET STATUS = 'cancel' WHERE EID = ?";
        
        try (PreparedStatement ps = this.connection.prepareStatement(updateQuery)) {

            ps.setString(1, eid);
            int rowsUpdated = ps.executeUpdate();

            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
