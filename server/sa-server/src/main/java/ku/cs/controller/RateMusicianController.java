package ku.cs.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import org.json.JSONObject;
import com.sun.net.httpserver.HttpExchange;
import ku.cs.service.MusicianService;

public class RateMusicianController extends Controller {

    private MusicianService musicianService;

    public RateMusicianController(MusicianService musicianService) {
        this.musicianService = musicianService;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("[Server] Received request at: " + exchange.getRequestURI());

        InputStream is = exchange.getRequestBody();
        String jsonString = new String(is.readAllBytes(), StandardCharsets.UTF_8);
        System.out.println("[Server] Request JSON: " + jsonString);

        try {
            JSONObject jsonObject = new JSONObject(jsonString);

            // ✅ ดึง "musician_uuid" จาก "data" (แก้ไขจากโค้ดเดิม)
            if (!jsonObject.has("data")) {
                throw new IllegalArgumentException("Missing key: data");
            }

            JSONObject dataObject = jsonObject.getJSONObject("data");

            if (!dataObject.has("UUID")) {
                throw new IllegalArgumentException("Missing key: musician_uuid");
            }

            String musicianUUID = dataObject.getString("UUID");
            System.out.println("[Server] Processing musician UUID: " + musicianUUID);

            // ✅ เพิ่มคะแนนให้ Musician
            String response = musicianService.incrementMusicianPoint(musicianUUID);

            // ✅ ส่ง Response กลับไปยัง Client
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();

            System.out.println("[Server] Response sent: " + response);
        } catch (Exception e) {
            e.printStackTrace();
            responseError(exchange, e);
        }
    }

}
