package ku.cs.net;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;

public class ClientLogin {

    public void login(String username, String password) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username", username);
            jsonObject.put("password", password);

            // HTTP Connection with json body
            HttpURLConnection httpURLConnection = Client.getClient().getHttpURLConnection(jsonObject.toString(), "/login");

            // Get Response Status Code
            int responseCode = httpURLConnection.getResponseCode();
            System.out.println("POST Response Code :: " + responseCode);

            if (responseCode != HttpURLConnection.HTTP_OK) throw new RuntimeException("ERROR CODE " + responseCode);

            // Get Response JSON
            JSONObject responseJSON = getResponseJSON(httpURLConnection);

            System.out.println(responseJSON.toString(4));
            String access_token = responseJSON.getString("access_token");

            Client.getClient().setAccessToken(access_token);

        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static JSONObject getResponseJSON(HttpURLConnection httpURLConnection) throws IOException {
        JSONObject responseJSON;

        try (BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()))) {
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            responseJSON = new JSONObject(response.toString());
        }

        return responseJSON;
    }
}
