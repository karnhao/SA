package ku.cs.net;

import ku.cs.model.MusicianRole;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.util.List;

public class ClientSetAvailableRoles {
    public String setAvailableRoles(List<MusicianRole> roles) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("access_token", Client.getClient().getAccessToken());

            JSONArray array = new JSONArray();
            for (MusicianRole role : roles) {
                JSONObject o = new JSONObject();
                o.put("role_id", role.getId());
                array.put(o);
            }

            jsonObject.put("roles", array);

            // HTTP Connection with json body
            HttpURLConnection httpURLConnection = Client.getClient().getHttpURLConnection(jsonObject.toString(), "/set_available_roles");

            // Get Response JSON
            return Client.getClient().getResponseJSON(httpURLConnection).toString();

        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
