package ku.cs.net;

import org.json.JSONObject;

import java.net.HttpURLConnection;

public class ClientRegister {

    public String register(String username, String name, String email, String password, String phone_number, String role) throws Exception {

        JSONObject jsonObject = toJSONObject(username, name, email, password, phone_number, role);

        HttpURLConnection httpURLConnection = Client.getClient().getHttpURLConnection(jsonObject.toString(), "/reg");

        JSONObject response;
        response = Client.getClient().getResponseJSON(httpURLConnection);
        return response.toString();

    }

    public String registerMusician(String username, String name, String email, String password, String phone_number, String role, String bank_name, String bank_number) throws Exception {

        JSONObject jsonObject = toJSONObject(username, name, email, password, phone_number, role);
        jsonObject.put("bank_name", bank_name);
        jsonObject.put("bank_number", bank_number);

        HttpURLConnection httpURLConnection = Client.getClient().getHttpURLConnection(jsonObject.toString(), "/reg", "type=musician");

        JSONObject response;
        response = Client.getClient().getResponseJSON(httpURLConnection);
        return response.toString();

    }

    private JSONObject toJSONObject(String username, String name, String email, String password, String phone_number, String role) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", username);
        jsonObject.put("name", name);
        jsonObject.put("email", email);
        jsonObject.put("password", password);
        jsonObject.put("phone_number", phone_number);
        jsonObject.put("role", role);
        return jsonObject;
    }
}
