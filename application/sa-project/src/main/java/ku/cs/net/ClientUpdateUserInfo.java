package ku.cs.net;

import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;

public class ClientUpdateUserInfo {
    public String updateInfo(String name, String email, String phoneNumber) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("access_token", Client.getClient().getAccessToken());
            jsonObject.put("name", name);
            jsonObject.put("email", email);
            jsonObject.put("phone_number", phoneNumber);

            // HTTP Connection with json body
            HttpURLConnection httpURLConnection = Client.getClient().getHttpURLConnection(jsonObject.toString(), "/updateuserinfo");

            // Get Response JSON
            return Client.getClient().getResponseJSON(httpURLConnection).toString();

        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
