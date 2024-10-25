package ku.cs.net;

import ku.cs.model.User;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.util.List;

public class ClientGetUsers {
    public List<User> getAllCustomers() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("access_token", Client.getClient().getAccessToken());

            // HTTP Connection with json body
            HttpURLConnection httpURLConnection = Client.getClient().getHttpURLConnection(jsonObject.toString(), "/getallusers","role=Customer");

            // Get Response JSON
            JSONObject users = Client.getClient().getResponseJSON(httpURLConnection);
            System.out.println(users.toString(4));
            return null;

        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
