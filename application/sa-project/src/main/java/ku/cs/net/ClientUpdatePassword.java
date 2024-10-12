package ku.cs.net;

import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;

public class ClientUpdatePassword {
    public JSONObject updatePassword(String oldPassword, String newPassword) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("access_token", Client.getClient().getAccessToken());
            jsonObject.put("old_password", oldPassword);
            jsonObject.put("password", newPassword);

            // HTTP Connection with json body
            HttpURLConnection httpURLConnection = Client.getClient().getHttpURLConnection(jsonObject.toString(), "/userinfo");

            // Get Response JSON
            return Client.getClient().getResponseJSON(httpURLConnection);

        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
