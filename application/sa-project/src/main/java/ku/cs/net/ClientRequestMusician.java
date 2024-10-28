package ku.cs.net;

import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;

public class ClientRequestMusician {
    public String requestMusician(String event_id, String role_id, String uuid) throws URISyntaxException, IOException {

        // Create Request Payload
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("access_token", Client.getClient().getAccessToken());
        jsonObject.put("event_id", event_id);
        jsonObject.put("role_id", role_id);
        jsonObject.put("uuid", uuid);

        System.out.println(jsonObject.toString(4));

        // send

        HttpURLConnection httpURLConnection = Client.getClient().getHttpURLConnection(jsonObject.toString(), "/request_musician");

        JSONObject response;
        response = Client.getClient().getResponseJSON(httpURLConnection);

        return response.toString();
    }
}
