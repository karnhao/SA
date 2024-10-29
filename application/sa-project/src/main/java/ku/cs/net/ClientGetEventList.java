package ku.cs.net;

import ku.cs.model.Event;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class ClientGetEventList {

    public List<Event> getEventList() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("access_token", Client.getClient().getAccessToken());

            // HTTP Connection with json body
            HttpURLConnection httpURLConnection = Client.getClient().getHttpURLConnection(jsonObject.toString(), "/event_list");

            // Get Response JSON
            return getEvents(httpURLConnection);
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Event> getRequestedEventMusician() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("access_token", Client.getClient().getAccessToken());

            // HTTP Connection with json body
            HttpURLConnection httpURLConnection = Client.getClient().getHttpURLConnection(jsonObject.toString(), "/get_requested_events", "base=musician");

            // Get Response JSON
            return getEvents(httpURLConnection);
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Event> getRequestedEventStereo() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("access_token", Client.getClient().getAccessToken());

            // HTTP Connection with json body
            HttpURLConnection httpURLConnection = Client.getClient().getHttpURLConnection(jsonObject.toString(), "/get_requested_events", "base=stereo");

            // Get Response JSON
            return getEvents(httpURLConnection);
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Event> getEvents(HttpURLConnection httpURLConnection) throws IOException {
        JSONObject json = Client.getClient().getResponseJSON(httpURLConnection);

        LinkedList<Event> result = new LinkedList<>();

        JSONArray array = json.getJSONArray("events");
        for (int i = 0; i < array.length(); i++) {
            JSONObject o = array.getJSONObject(i);
            Event e = new Event();

            e.setTitle(o.getString("title"));
            e.setDescription(o.getString("description"));
            e.setStartDate(LocalDateTime.parse(o.getString("start_datetime")));
            e.setEndDate(LocalDateTime.parse(o.getString("end_datetime")));
            e.setStatus(o.getString("status"));
            e.setEventID(o.getString("id"));

            result.add(e);
        }
        return result;
    }
}
