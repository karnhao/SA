package ku.cs.net;

import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;

public class ClientUserInfo {

    public JSONObject getUserInfo() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("access_token", Client.getClient().getAccessToken());

            // HTTP Connection with json body
            HttpURLConnection httpURLConnection = Client.getClient().getHttpURLConnection(jsonObject.toString(), "/userinfo");

            // Get Response JSON
            return Client.getClient().getResponseJSON(httpURLConnection);

        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
