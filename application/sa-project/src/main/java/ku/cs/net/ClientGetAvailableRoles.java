package ku.cs.net;

import ku.cs.model.MusicianRole;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;

public class ClientGetAvailableRoles {
    public List<MusicianRole> getAvailableRoles() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("access_token", Client.getClient().getAccessToken());

            // HTTP Connection with json body
            HttpURLConnection httpURLConnection = Client.getClient().getHttpURLConnection(jsonObject.toString(), "/get_available_roles");

            // Get Response JSON
            JSONObject response = Client.getClient().getResponseJSON(httpURLConnection);
            System.out.println(response);
            JSONArray array = response.getJSONArray("roles");

            LinkedList<MusicianRole> roles = new LinkedList<>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject o = array.getJSONObject(i);
                MusicianRole role = new MusicianRole();
                role.setId(o.getString("role_id"));
                role.setName(o.getString("name"));
                roles.add(role);
            }

            return roles;

        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
