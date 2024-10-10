package ku.cs.net;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;

public class ClientRegister {

    public String register(String username, String name, String email, String password, String phone_number, String role) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username", username);
            jsonObject.put("name", name);
            jsonObject.put("email", email);
            jsonObject.put("password", password);
            jsonObject.put("phone_number", phone_number);
            jsonObject.put("role", role);

            HttpURLConnection httpURLConnection = Client.getClient().getHttpURLConnection(jsonObject.toString(), "/reg");

            int responseCode = httpURLConnection.getResponseCode();
            System.out.println("POST Response Code :: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()))) {
                    String inputLine;
                    StringBuilder response = new StringBuilder();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }

                    return response.toString();
                }
            } else {
                String error;
                try (BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getErrorStream()))) {
                    String inputLine;
                    StringBuilder response = new StringBuilder();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }

                    error = response.toString();
                }
                throw new RuntimeException(responseCode + " " + error);
            }
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }


}
