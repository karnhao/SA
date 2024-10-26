package ku.cs.net;

import ku.cs.model.Stereo;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;

public class ClientGetStereoList {
    public List<Stereo> getStereoList() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("access_token", Client.getClient().getAccessToken());

            // HTTP Connection with json body
            HttpURLConnection httpURLConnection = Client.getClient().getHttpURLConnection(jsonObject.toString(), "/stereo_list");

            // Get Response JSON
            JSONObject responseJSON = Client.getClient().getResponseJSON(httpURLConnection);

            LinkedList<Stereo> result = new LinkedList<>();

            System.out.println(responseJSON.toString(4));

            JSONArray array = responseJSON.getJSONArray("stereos");
            for (int i = 0; i < array.length(); i++) {
                JSONObject o = array.getJSONObject(i);
                Stereo s = new Stereo();

                s.setName(o.getString("name"));
                s.setOwner_id(o.getString("owner_id"));
                s.setOwner_name(o.getString("owner_name"));
                s.setType_id(o.getString("type_id"));
                s.setType_name(o.getString("type_name"));
                s.setId(o.getString("id"));

                result.add(s);
            }
            return result;
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
