package ku.cs.net;

import org.json.JSONObject;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;

public class ClientRatingMusician {
    public String rateMusician(String musicianId) throws URISyntaxException, IOException {
        if (musicianId == null || musicianId.trim().isEmpty()) {
            throw new IllegalArgumentException("Musician ID is null or empty!");
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("access_token", Client.getClient().getAccessToken());
        jsonObject.put("musician_id", musicianId);

        System.out.println(jsonObject.toString(4));

        HttpURLConnection httpURLConnection = Client.getClient().getHttpURLConnection(jsonObject.toString(), "/rate_musician");

        int responseCode = httpURLConnection.getResponseCode();
        System.out.println("[Client] Response Code: " + responseCode);

        if (responseCode == 200) {
            JSONObject response = Client.getClient().getResponseJSON(httpURLConnection);
            System.out.println("[Client] Response from server: " + response.toString(4));
            return response.getString("message");
        } else {
            System.out.println("[Client] Error response from server");
            return "Error: Response code " + responseCode;
        }
    }
}
