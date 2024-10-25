package ku.cs.net;

import ku.cs.model.MusicianRole;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;

public class ClientGetRole {
    public List<MusicianRole> getMusicianRoles() {
        List<MusicianRole> result = new LinkedList<>();
        try {
            // HTTP Connection with json body
            HttpURLConnection httpURLConnection = Client.getClient().getHttpURLConnectionWithoutBody("/getmusicianroles");

            // Get Response JSON
            JSONObject responseJSON = Client.getClient().getResponseJSON(httpURLConnection);
            System.out.println(responseJSON);

            JSONArray roles = responseJSON.getJSONArray("roles");
            JSONObject o;
            MusicianRole role;
            for (int i = 0; i < roles.length(); i++) {
                o = roles.getJSONObject(i);
                role = new MusicianRole();
                role.setId(o.getString("id"));
                role.setName(o.getString("name"));
                result.add(role);
            }

            return result;

        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
