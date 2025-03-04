package ku.cs.net;

import org.json.JSONObject;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;

public class ClientRatingMusician {
    public String rateMusician(String musicianUUID) throws URISyntaxException, IOException {
        if (musicianUUID == null || musicianUUID.trim().isEmpty()) {
            throw new IllegalArgumentException("Musician UUID is null or empty!");
        }

        // ✅ สร้าง JSON Request Body ตามโครงสร้าง `ClientCreateEvent`
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("access_token", Client.getClient().getAccessToken());

        JSONObject dataJSONObject = new JSONObject();
        dataJSONObject.put("UUID", musicianUUID);

        jsonObject.put("data", dataJSONObject);

        System.out.println("[Client] Sending request to server: " + jsonObject.toString(4));

        // ✅ ใช้ `getHttpURLConnection` ส่ง Request ไปยัง `/rate_musician`
        HttpURLConnection httpURLConnection = Client.getClient().getHttpURLConnection(jsonObject.toString(), "/rate_musician");

        // ✅ ตรวจสอบ Response Code
        int responseCode = httpURLConnection.getResponseCode();
        System.out.println("[Client] Response Code: " + responseCode);

        // ✅ รับ JSON Response จากเซิร์ฟเวอร์
        JSONObject response = Client.getClient().getResponseJSON(httpURLConnection);
        System.out.println("[Client] Response from server: " + response.toString(4));

        return response.toString();
    }
}
