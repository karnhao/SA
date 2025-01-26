package ku.cs.net;

import ku.cs.model.StereoType;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;

public class ClientGetStereoType {
    public List<StereoType> getStereoTypes() {
        List<StereoType> result = new LinkedList<>();
        try {
            // HTTP Connection with json body
            HttpURLConnection httpURLConnection = Client.getClient().getHttpURLConnectionWithoutBody("/getstereotypes");

            // Get Response JSON
            JSONObject responseJSON = Client.getClient().getResponseJSON(httpURLConnection);
            System.out.println(responseJSON);

            JSONArray roles = responseJSON.getJSONArray("types");
            JSONObject o;
            StereoType type;
            for (int i = 0; i < roles.length(); i++) {
                o = roles.getJSONObject(i);
                type = new StereoType();
                type.setId(o.getString("id"));
                type.setName(o.getString("name"));
                result.add(type);
            }

            return result;

        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
