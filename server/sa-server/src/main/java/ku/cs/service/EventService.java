package ku.cs.service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.naming.AuthenticationException;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import ku.cs.entity.Event;
import ku.cs.entity.MusicianRequirement;
import ku.cs.entity.StereoRequirement;
import ku.cs.repository.EventRepository;
import ku.cs.repository.RequirementRepository;

public class EventService {
    private EventRepository eventRepository;
    private RequirementRepository requirementsRepository;

    public EventService(EventRepository eventRepository, RequirementRepository requirementsRepository) {
        this.eventRepository = eventRepository;
        this.requirementsRepository = requirementsRepository;
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

        JSONArray array = requirementJSONObject.getJSONArray("musicians");
        JSONObject o;
        for (int i = 0; i < array.length(); i++) {
            o = array.getJSONObject(i);
            requirementsRepository.addMusicianRequirement(event_id, o.getString("role_id"), o.getInt("quantity"));
        }

        array = requirementJSONObject.getJSONArray("stereos");
        for (int i = 0; i < array.length(); i++) {
            o = array.getJSONObject(i);
            requirementsRepository.addStereoRequirement(event_id, o.getString("type_id"), o.getInt("quantity"));
        }

        return "success";
    }

    public JSONObject getAllEvent(String accessToken) throws SQLException, AuthenticationException {
        AuthenticationService authenticationService = AuthenticationService.get();
        String uuid = authenticationService.getUserID(accessToken);
        if (uuid == null)
            throw new AuthenticationException("Authentication Failed");

        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();

        List<Event> events = eventRepository.getAllEventByUUID(uuid);
        events.stream().map(e -> this.toJSONObject(e, null, null)).forEach(array::put);

        jsonObject.put("events", array);

        return jsonObject;
    }

    public JSONObject getEvent(String accessToken, String event_id) throws AuthenticationException, SQLException {
        AuthenticationService authenticationService = AuthenticationService.get();
        String uuid = authenticationService.getUserID(accessToken);

        if (uuid == null)
            throw new AuthenticationException("Authentication Failed");
        Event event = eventRepository.getEventByEID(event_id);
        List<MusicianRequirement> mRequirements = requirementsRepository.getMusicianRequirementList(event_id);
        List<StereoRequirement> stereoRequirements = requirementsRepository.getStereoRequirementList(event_id);

        return toJSONObject(event, mRequirements, stereoRequirements);
    }

    private JSONObject toJSONObject(Event event, List<MusicianRequirement> musicianRequirements,
            List<StereoRequirement> stereoRequirements) {
        JSONObject o = new JSONObject();
        o.put("title", event.getTitle());
        o.put("description", event.getDescription());
        o.put("status", event.getStatus());
        o.put("id", event.getId());
        o.put("start_datetime", event.getStartDateTime().toString());
        o.put("end_datetime", event.getEndDateTime());

        JSONArray mArray = new JSONArray();

        if (musicianRequirements != null) {
            musicianRequirements.forEach(m -> {
                JSONObject n = new JSONObject();
                n.put("id", m.getMusician_id());
                n.put("quantity", m.getQuantity());
                mArray.put(n);
            });

            o.put("musician_requirement", mArray);
        }

        if (stereoRequirements != null) {
            JSONArray sArray = new JSONArray();
            stereoRequirements.forEach(s -> {
                JSONObject n = new JSONObject();
                n.put("id", s.getType_id());
                n.put("quantity", s.getQuantity());
                sArray.put(n);
            });

            o.put("stereo_requirement", sArray);
        }

        return o;
    }
}
