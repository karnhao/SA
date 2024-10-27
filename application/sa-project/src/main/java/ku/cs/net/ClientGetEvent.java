package ku.cs.net;

import ku.cs.model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class ClientGetEvent {
    public EventDetail getEvent(String event_id) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("access_token", Client.getClient().getAccessToken());
            jsonObject.put("id", event_id);

            // HTTP Connection with json body
            HttpURLConnection httpURLConnection = Client.getClient().getHttpURLConnection(jsonObject.toString(), "/event", event_id);

            // Get Response JSON
            JSONObject eventJSON = Client.getClient().getResponseJSON(httpURLConnection);

            System.out.println(eventJSON.toString(4));

            EventDetail eventDetail = new EventDetail();
            eventDetail.setTitle(eventJSON.getString("title"));
            eventDetail.setDescription(eventJSON.getString("description"));
            eventDetail.setStatus(eventJSON.getString("status"));
            eventDetail.setEventID(eventJSON.getString("id"));
            eventDetail.setStartDate(LocalDateTime.parse(eventJSON.getString("start_datetime")));
            eventDetail.setEndDate(LocalDateTime.parse(eventJSON.getString("end_datetime")));
            eventDetail.setMusicianRequirements(toMusicianRequirementList(eventJSON.getJSONArray("musician_requirement")));
            eventDetail.setStereoRequirements(toStereoRequirementList(eventJSON.getJSONArray("stereo_requirement")));

            User owner = new User();
            owner.setUuid(eventJSON.getString("owner_id"));
            eventDetail.setOwner(owner);

            return eventDetail;

        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<MusicianRequirement> toMusicianRequirementList(JSONArray arrayJSON) {
        List<MusicianRequirement> list = new LinkedList<>();

        for (int i = 0; i < arrayJSON.length(); i++) {
            JSONObject o = arrayJSON.getJSONObject(i);
            MusicianRole role = new MusicianRole();
            role.setName(o.getString("name"));
            role.setId(o.getString("id"));

            MusicianRequirement r = new MusicianRequirement();
            r.setQuantity(o.getInt("quantity"));
            r.setMusicianRole(role);
            list.add(r);
        }

        return list;
    }

    private List<StereoRequirement> toStereoRequirementList(JSONArray arrayJSON) {
        List<StereoRequirement> list = new LinkedList<>();

        for (int i = 0; i < arrayJSON.length(); i++) {
            JSONObject o = arrayJSON.getJSONObject(i);
            StereoType type = new StereoType();
            type.setName(o.getString("name"));
            type.setId(o.getString("id"));

            StereoRequirement r = new StereoRequirement();
            r.setQuantity(o.getInt("quantity"));
            r.setType(type);
            list.add(r);
        }

        return list;
    }
}
