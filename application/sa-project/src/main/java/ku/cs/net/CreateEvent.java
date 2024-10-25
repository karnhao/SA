package ku.cs.net;

import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.time.LocalDateTime;

public class CreateEvent {
    public String createEvent(String eventName, LocalDateTime startDateTime, LocalDateTime endDateTime, String description) throws URISyntaxException, IOException {
        JSONObject jsonObject = new JSONObject();

        HttpURLConnection httpURLConnection = Client.getClient().getHttpURLConnection(jsonObject.toString(), "/create_event");

        JSONObject response;
        response = Client.getClient().getResponseJSON(httpURLConnection);
        return response.toString();

    }
}
