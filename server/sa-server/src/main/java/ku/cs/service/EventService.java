package ku.cs.service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.naming.AuthenticationException;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import ku.cs.entity.Event;
import ku.cs.entity.Musician;
import ku.cs.entity.MusicianRequirement;
import ku.cs.entity.Stereo;
import ku.cs.entity.StereoRequirement;
import ku.cs.entity.User;
import ku.cs.repository.EventRepository;
import ku.cs.repository.RequirementRepository;
import ku.cs.repository.UserRepository;

public class EventService {
    private EventRepository eventRepository;
    private RequirementRepository requirementsRepository;
    private UserRepository userRepository;

    public EventService(EventRepository eventRepository, RequirementRepository requirementsRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.requirementsRepository = requirementsRepository;
        this.userRepository = userRepository;
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

        User user = userRepository.getUserByUUID(uuid);

        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();

        List<Event> events = user.getRole().equalsIgnoreCase("agent") ? eventRepository.getAllEvent() : eventRepository.getAllEventByUUID(uuid);
        events.stream().map(e -> this.toJSONObject(e, null, null, null, null)).forEach(array::put);

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
        List<Musician> musicians = eventRepository.getMusicianFromEventID(event_id);
        List<Stereo> stereos = eventRepository.getStereoFromEventID(event_id);

        return toJSONObject(event, mRequirements, stereoRequirements, musicians, stereos);
    }

    public String requestMusician(JSONObject jsonObject) throws AuthenticationException, SQLException {

        String accessToken = jsonObject.getString("access_token");
        String eventID = jsonObject.getString("event_id");
        String target_uuid = jsonObject.getString("uuid");
        String roleID = jsonObject.getString("role_id");

        AuthenticationService authenticationService = AuthenticationService.get();
        String uuid = authenticationService.getUserID(accessToken);

        User sourceUser = userRepository.getUserByUUID(uuid);
        if (!sourceUser.getRole().equalsIgnoreCase("agent")) throw new AuthenticationException("Access Denied");

        eventRepository.addMusicianRequest(target_uuid, eventID, roleID);

        return "OK";
    }

    public String requestStereo(JSONObject jsonObject) throws AuthenticationException, SQLException {

        String accessToken = jsonObject.getString("access_token");
        String eventID = jsonObject.getString("event_id");
        String stereoID = jsonObject.getString("stereo_id");

        AuthenticationService authenticationService = AuthenticationService.get();
        String uuid = authenticationService.getUserID(accessToken);

        User sourceUser = userRepository.getUserByUUID(uuid);
        if (!sourceUser.getRole().equalsIgnoreCase("agent")) throw new AuthenticationException("Access Denied");

        eventRepository.addStereoRequest(eventID, stereoID);

        return "OK";
    }

    public JSONObject getRequestedEventsMusician(String accessToken) {
        AuthenticationService authenticationService = AuthenticationService.get();
        String uuid = authenticationService.getUserID(accessToken);

        JSONObject jsonObject = new JSONObject();
        List<Event> events = eventRepository.getEventsByMusicianUUID(uuid);

        JSONArray jsonArray = new JSONArray();
        for (Event event : events) {
            JSONObject jsonObj = toJSONObject(event, null, null, null, null);
            jsonArray.put(jsonObj);
        }
        jsonObject.put("events", jsonArray);

        return jsonObject;
    }

    public JSONObject getRequestedEventsStereo(String accessToken) {
        AuthenticationService authenticationService = AuthenticationService.get();
        String uuid = authenticationService.getUserID(accessToken);

        JSONObject jsonObject = new JSONObject();
        List<Event> events = eventRepository.getEventsByStereoOwnerUUID(uuid);

        JSONArray jsonArray = new JSONArray();
        for (Event event : events) {
            JSONObject jsonObj = toJSONObject(event, null, null, null, null);
            jsonArray.put(jsonObj);
        }
        jsonObject.put("events", jsonArray);

        return jsonObject;
    }

    public String acceptMusicianEvent(JSONObject jsonObject) throws Exception {
        String accessToken = jsonObject.getString("access_token");

        AuthenticationService authenticationService = AuthenticationService.get();
        String uuid = authenticationService.getUserID(accessToken);
        if (uuid == null) throw new AuthenticationException("Unauthorized");

        String eid = jsonObject.getString("event_id");
        String role_id = jsonObject.getString("role_id");

        if(!eventRepository.acceptMusicianEvent(uuid, eid, role_id)) {
            throw new Exception("ERROR: Failed to accept");
        }

        return "OK";
    }

    public String rejectMusicianEvent(JSONObject jsonObject) throws Exception {
        String accessToken = jsonObject.getString("access_token");

        AuthenticationService authenticationService = AuthenticationService.get();
        String uuid = authenticationService.getUserID(accessToken);
        if (uuid == null) throw new AuthenticationException("Unauthorized");

        String eid = jsonObject.getString("event_id");
        String role_id = jsonObject.getString("role_id");

        if(!eventRepository.rejectMusicianEvent(uuid, eid, role_id)) {
            throw new Exception("ERROR: Failed to accept");
        }

        return "OK";
    }

    public String acceptStereoEvent(JSONObject jsonObject) throws Exception {
        String accessToken = jsonObject.getString("access_token");

        AuthenticationService authenticationService = AuthenticationService.get();
        String uuid = authenticationService.getUserID(accessToken);
        if (uuid == null) throw new AuthenticationException("Unauthorized");

        String eid = jsonObject.getString("event_id");
        String stid = jsonObject.getString("stereo_id");

        if(!eventRepository.acceptStereoEvent(stid, eid)) {
            throw new Exception("ERROR: Failed to accept");
        }

        return "OK";
    }

    public String rejectStereoEvent(JSONObject jsonObject) throws Exception {
        String accessToken = jsonObject.getString("access_token");

        AuthenticationService authenticationService = AuthenticationService.get();
        String uuid = authenticationService.getUserID(accessToken);
        if (uuid == null) throw new AuthenticationException("Unauthorized");

        String eid = jsonObject.getString("event_id");
        String stid = jsonObject.getString("stereo_id");

        if(!eventRepository.rejectStereoEvent(stid, eid)) {
            throw new Exception("ERROR: Failed to reject");
        }

        return "OK";
    }

    public String approveEvent(JSONObject jsonObject) throws Exception {
        String accessToken = jsonObject.getString("access_token");
        AuthenticationService authenticationService = AuthenticationService.get();
        String uuid = authenticationService.getUserID(accessToken);
        if (uuid == null) throw new AuthenticationException("Unauthorized");

        User sourceUser = userRepository.getUserByUUID(uuid);
        if (!sourceUser.getRole().equalsIgnoreCase("agent")) throw new Exception("Access Denied");

        if(!eventRepository.approveEvent(jsonObject.getString("event_id"))) throw new Exception("Failed");

        return "OK";
    }

    public String cancelEvent(JSONObject jsonObject) throws Exception {
        String accessToken = jsonObject.getString("access_token");
        AuthenticationService authenticationService = AuthenticationService.get();
        String uuid = authenticationService.getUserID(accessToken);
        if (uuid == null) throw new AuthenticationException("Unauthorized");

        User sourceUser = userRepository.getUserByUUID(uuid);
        if (!sourceUser.getRole().equalsIgnoreCase("agent")) throw new Exception("Access Denied");

        if(!eventRepository.cancelEvent(jsonObject.getString("event_id"))) throw new Exception("Failed");

        return "OK";
    }

    private JSONObject toJSONObject(Event event, List<MusicianRequirement> musicianRequirements,
            List<StereoRequirement> stereoRequirements, List<Musician> musicians, List<Stereo> stereos) {
        JSONObject o = new JSONObject();
        o.put("title", event.getTitle());
        o.put("description", event.getDescription());
        o.put("status", event.getStatus());
        o.put("id", event.getId());
        o.put("start_datetime", event.getStartDateTime().toString());
        o.put("end_datetime", event.getEndDateTime());
        o.put("owner_id", event.getOwnerID());

        JSONArray mArray = new JSONArray();

        if (musicianRequirements != null) {
            musicianRequirements.forEach(m -> {
                JSONObject n = new JSONObject();
                n.put("id", m.getMusician_id());
                n.put("quantity", m.getQuantity());
                n.put("name", m.getRoleName());
                
                JSONArray nArray = new JSONArray();
                if (musicians != null) {
                    musicians.stream().filter(t -> t.getMusicianRoleID().equals(m.getMusician_id())).forEach(t -> {
                        JSONObject p = new JSONObject();
                        p.put("name", t.getName());
                        p.put("status", t.getStatus());
                        p.put("email", t.getEmail());
                        p.put("phone_number", t.getPhone_number());
                        p.put("id", t.getUuid());
                        nArray.put(p);
                    });
                }

                n.put("musicians", nArray);
                
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
                n.put("name", s.getTypeName());

                JSONArray nArray = new JSONArray();
                if (stereos != null) {
                    stereos.stream().filter(t -> t.getType_id().equals(s.getType_id())).forEach(t -> {
                        JSONObject p = new JSONObject();
                        p.put("name", t.getName());
                        p.put("status", t.getStatus());
                        p.put("email", t.getOwner_email());
                        p.put("phone_number", t.getOwner_phone_number());
                        p.put("id", t.getId());
                        p.put("owner_id", t.getOwner_id());
                        p.put("owner_name", t.getOwner_name());
                        nArray.put(p);
                    });
                }

                n.put("stereos", nArray);

                sArray.put(n);
            });

            o.put("stereo_requirement", sArray);
        }

        return o;
    }
}
