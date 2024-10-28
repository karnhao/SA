package ku.cs.net;

import ku.cs.model.Musician;
import ku.cs.model.User;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;

public class ClientGetUsers {
    public List<User> getAllCustomers() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("access_token", Client.getClient().getAccessToken());

            // HTTP Connection with json body
            HttpURLConnection httpURLConnection = Client.getClient().getHttpURLConnection(jsonObject.toString(), "/getallusers","role=Customer");

            // Get Response JSON
            JSONObject responseJSON = Client.getClient().getResponseJSON(httpURLConnection);
            JSONArray array = responseJSON.getJSONArray("users");

            List<User> userList = new LinkedList<>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject o = array.getJSONObject(i);

                User user = new User();
                user.setRole(o.getString("role"));
                user.setName(o.getString("name"));
                user.setPhone_number(o.getString("phone_number"));
                user.setUuid(o.getString("uuid"));
                user.setEmail(o.getString("email"));
                user.setUsername(o.getString("username"));

                userList.add(user);
            }

            return userList;

        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Musician> getAllMusicians() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("access_token", Client.getClient().getAccessToken());

            // HTTP Connection with json body
            HttpURLConnection httpURLConnection = Client.getClient().getHttpURLConnection(jsonObject.toString(), "/getallusers","role=Musician");

            // Get Response JSON
            return getMusicians(httpURLConnection);

        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Musician> getAllMusicianByAvailableRole(String role_id) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("access_token", Client.getClient().getAccessToken());

            // HTTP Connection with json body
            HttpURLConnection httpURLConnection = Client.getClient().getHttpURLConnection(jsonObject.toString(), "/getallusers","role=Musician&available-role="+role_id);

            // Get Response JSON
            return getMusicians(httpURLConnection);

        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Musician> getMusicians(HttpURLConnection httpURLConnection) throws IOException {
        JSONObject responseJSON = Client.getClient().getResponseJSON(httpURLConnection);
        JSONArray array = responseJSON.getJSONArray("users");

        List<Musician> userList = new LinkedList<>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject o = array.getJSONObject(i);

            Musician user = new Musician();
            user.setRole(o.getString("role"));
            user.setName(o.getString("name"));
            user.setPhone_number(o.getString("phone_number"));
            user.setUuid(o.getString("uuid"));
            user.setEmail(o.getString("email"));
            user.setUsername(o.getString("username"));
            user.setBankName(o.has("bank_name") ? o.getString("bank_name") : null);
            user.setBankNumber(o.has("bank_number") ? o.getString("bank_number") : null);

            userList.add(user);
        }

        return userList;
    }
}
