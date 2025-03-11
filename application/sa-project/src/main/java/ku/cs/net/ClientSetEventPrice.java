package ku.cs.net;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;

import org.json.JSONObject;

public class ClientSetEventPrice {
    public String setEventPrice(String eventID,int price){
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("access_token", Client.getClient().getAccessToken());
            jsonObject.put("event_id", eventID);
            jsonObject.put("price", price);

            // HTTP Connection with json body
            HttpURLConnection httpURLConnection = Client.getClient().getHttpURLConnection(jsonObject.toString(), "/setEventPrice");

            // Get Response JSON
            return Client.getClient().getResponseJSON(httpURLConnection).toString();

        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
