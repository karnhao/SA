package ku.cs.net;

import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;

public class ClientApproveEvent {
    public String approve(String event_id) throws URISyntaxException, IOException {

        // Create Request Payload
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("access_token", Client.getClient().getAccessToken());
        jsonObject.put("event_id", event_id);

        System.out.println(jsonObject.toString(4));

        // send

        HttpURLConnection httpURLConnection = Client.getClient().getHttpURLConnection(jsonObject.toString(), "/approve");

        JSONObject response;
        response = Client.getClient().getResponseJSON(httpURLConnection);

        return response.toString();
    }
}