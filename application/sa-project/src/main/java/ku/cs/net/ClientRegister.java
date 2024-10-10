package ku.cs.net;

import org.json.JSONObject;

import java.net.HttpURLConnection;

public class ClientRegister {

    public String register(String username, String name, String email, String password, String phone_number, String role) throws Exception {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", username);
        jsonObject.put("name", name);
        jsonObject.put("email", email);
        jsonObject.put("password", password);
        jsonObject.put("phone_number", phone_number);
        jsonObject.put("role", role);

        HttpURLConnection httpURLConnection = Client.getClient().getHttpURLConnection(jsonObject.toString(), "/reg");

        JSONObject response;
        response = Client.getClient().getResponseJSON(httpURLConnection);
        return response.toString();

    }


}
