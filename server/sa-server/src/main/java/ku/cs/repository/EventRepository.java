package ku.cs.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDateTime;

import ku.cs.entity.Event;

public class EventRepository extends Repository {

    EventRepository(Connection connection) {
        super(connection);
    }

    public Event getEventByEID(String eid) throws SQLException {
        try {
            this.statement = connection.createStatement();

            this.resultSet = this.statement.executeQuery(
                    String.format(
                            "SELECT EID, START_DATE, END_DATE, START_TIME, END_TIME, STATUS, TITLE, DESCRIPTION, UUID WHERE EID = '%s';",
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

            Event event = new Event();
            event.setId(resultEID);
            event.setTitle(resultTitle);
            event.setDescription(resultDescription);
            event.setStartDateTime(LocalDateTime.of(resultStartDate.toLocalDate(), resultStartTime.toLocalTime()));
            event.setEndDateTime(LocalDateTime.of(resultEndDate.toLocalDate(), resultEndTime.toLocalTime()));
            event.setStatus(resultStatus);

            return event;

        } catch (SQLException e) {
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
                            ownerUUID
                    )
            );
        } finally {
            this.statement.close();
        }
    }
    
}
