package ku.cs.service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.UUID;

import org.json.JSONObject;

import ku.cs.entity.Event;
import ku.cs.repository.EventRepository;

public class EventService {
    private EventRepository eventRepository;

    EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public String createEvent(String accessToken, JSONObject JSONObject) throws SQLException {
        AuthenticationService authenticationService = AuthenticationService.get();
        String uuid = authenticationService.getUserID(accessToken);

        JSONObject eventJSONObject = JSONObject.getJSONObject("event");
        JSONObject requirementJSONObject = JSONObject.getJSONObject("requirement");

        String event_id = UUID.randomUUID().toString();

        Event event = new Event();
        event.setTitle(eventJSONObject.getString("title"));
        event.setDescription(eventJSONObject.getString("description"));
        event.setStartDateTime(LocalDateTime.parse(eventJSONObject.getString("start_datetime")));
        event.setEndDateTime(LocalDateTime.parse(eventJSONObject.getString("end_datetime")));
        event.setStatus("consider");
        event.setId(event_id);

        eventRepository.createEvent(uuid, event);
        return "success";
    }
}
