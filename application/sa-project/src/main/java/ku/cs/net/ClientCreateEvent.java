package ku.cs.net;

import ku.cs.model.MusicianRequirement;
import ku.cs.model.StereoRequirement;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.List;

public class ClientCreateEvent {
    public String createEvent(String eventName,
                              LocalDateTime startDateTime,
                              LocalDateTime endDateTime,
                              String description,
                              List<MusicianRequirement> musicianRequirements,
                              List<StereoRequirement> stereoRequirements) throws URISyntaxException, IOException {

        // Create Request Payload
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("access_token", Client.getClient().getAccessToken());

        JSONObject dataJSONObject = new JSONObject();
        JSONObject eventJSONObject = new JSONObject();
        JSONObject requirementJSONObject = new JSONObject();

        eventJSONObject.put("title", eventName);
        eventJSONObject.put("description", description);
        eventJSONObject.put("start_datetime", startDateTime.toString());
        eventJSONObject.put("end_datetime", endDateTime.toString());

        JSONArray musicianRequirementJSONArray = new JSONArray();
        JSONArray stereoRequirementJSONArray = new JSONArray();

        if (musicianRequirements != null) {
            musicianRequirements.forEach(r -> {
                JSONObject o = new JSONObject();
                o.put("role_id", r.getMusicianRole().getId());
                o.put("quantity", r.getQuantity());
                musicianRequirementJSONArray.put(o);
            });
        }

        if (stereoRequirements != null) {
            stereoRequirements.forEach(r -> {
                JSONObject o = new JSONObject();
                o.put("type_id", r.getType().getId());
                o.put("quantity", r.getQuantity());
                stereoRequirementJSONArray.put(o);
            });
        }

        requirementJSONObject.put("musicians", musicianRequirementJSONArray);
        requirementJSONObject.put("stereos", stereoRequirementJSONArray);

        dataJSONObject.put("event", eventJSONObject);
        dataJSONObject.put("requirement", requirementJSONObject);
        jsonObject.put("data", dataJSONObject);

        System.out.println(jsonObject.toString(4));

        // send

        HttpURLConnection httpURLConnection = Client.getClient().getHttpURLConnection(jsonObject.toString(), "/create_event");

        JSONObject response;
        response = Client.getClient().getResponseJSON(httpURLConnection);

        return response.toString();
    }
}
