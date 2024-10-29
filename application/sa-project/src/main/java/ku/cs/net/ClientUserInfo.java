package ku.cs.net;

import ku.cs.model.User;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;

public class ClientUserInfo {

    public User getUserInfo() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("access_token", Client.getClient().getAccessToken());

            // HTTP Connection with json body
            HttpURLConnection httpURLConnection = Client.getClient().getHttpURLConnection(jsonObject.toString(), "/userinfo");

            // Get Response JSON
            return getUser(httpURLConnection);

        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public User getUserInfo(String uuid) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("access_token", Client.getClient().getAccessToken());

            // HTTP Connection with json body
            HttpURLConnection httpURLConnection = Client.getClient().getHttpURLConnection(jsonObject.toString(), "/userinfo", "id=" + uuid);

            // Get Response JSON
            return getUser(httpURLConnection);

        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private User getUser(HttpURLConnection httpURLConnection) throws IOException {
        JSONObject userInfo = Client.getClient().getResponseJSON(httpURLConnection);

        User user = new User();
        if (userInfo.has("email")) user.setEmail(userInfo.getString("email"));
        if (userInfo.has("name")) user.setName(userInfo.getString("name"));
        if (userInfo.has("role")) user.setRole(userInfo.getString("role"));
        if (userInfo.has("uuid")) user.setUuid(userInfo.getString("uuid"));
        if (userInfo.has("username")) user.setUsername(userInfo.getString("username"));
        if (userInfo.has("phone_number")) user.setPhone_number(userInfo.getString("phone_number"));
        return user;
    }
}
