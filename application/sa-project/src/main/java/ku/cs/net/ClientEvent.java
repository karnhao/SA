package ku.cs.net;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;

import org.json.JSONObject;

public class ClientEvent {
    

    public String setStereoRequirementStatus(String eventID, String typeID, String status) throws URISyntaxException, IOException {
        // Create Request Payload
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("access_token", Client.getClient().getAccessToken());

        jsonObject.put("eid", eventID);
        jsonObject.put("tid", typeID);
        jsonObject.put("status", status);

        System.out.println(jsonObject.toString(4));

        // send
        HttpURLConnection httpURLConnection = Client.getClient().getHttpURLConnection(jsonObject.toString(), "/requirement/stereo/set-status");

        JSONObject response;
        response = Client.getClient().getResponseJSON(httpURLConnection);

        return response.toString();
    }

    public String setMusicianRequirementStatus(String eventID, String roleID, String status) throws URISyntaxException, IOException {
        // Create Request Payload
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("access_token", Client.getClient().getAccessToken());

        jsonObject.put("eid", eventID);
        jsonObject.put("rid", roleID);
        jsonObject.put("status", status);

        System.out.println(jsonObject.toString(4));

        // send
        HttpURLConnection httpURLConnection = Client.getClient().getHttpURLConnection(jsonObject.toString(), "/requirement/musician/set-status");

        JSONObject response;
        response = Client.getClient().getResponseJSON(httpURLConnection);

        return response.toString();
    }
}
