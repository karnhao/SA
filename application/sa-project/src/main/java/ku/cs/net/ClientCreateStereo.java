package ku.cs.net;

import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;

public class ClientCreateStereo {
    public String createStereo(String stereoName, String type_id) throws URISyntaxException, IOException {

        // Create Request Payload
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("access_token", Client.getClient().getAccessToken());

        JSONObject stereoJSONObject = new JSONObject();

        stereoJSONObject.put("name", stereoName);
        stereoJSONObject.put("type_id", type_id);

        jsonObject.put("stereo", stereoJSONObject);

        System.out.println(jsonObject.toString(4));

        // send

        HttpURLConnection httpURLConnection = Client.getClient().getHttpURLConnection(jsonObject.toString(), "/create_stereo");

        JSONObject response;
        response = Client.getClient().getResponseJSON(httpURLConnection);

        return response.toString();
    }
}
