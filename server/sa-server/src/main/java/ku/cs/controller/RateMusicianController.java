package ku.cs.controller;

import java.io.*;
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

        if (!exchange.getRequestMethod().equalsIgnoreCase("POST")) {
            exchange.sendResponseHeaders(405, -1); // Method Not Allowed
            exchange.close();
            return;
        }

        try {
            String jsonString = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            System.out.println("[Server] Request JSON: " + jsonString);

            JSONObject jsonObject = new JSONObject(jsonString);

            if (!jsonObject.has("musician_id")) {
                throw new IllegalArgumentException("Missing key: musician_id");
            }

            String musicianId = jsonObject.getString("musician_id");
            System.out.println("[Server] Processing musician ID: " + musicianId);

            String resultMessage = musicianService.incrementMusicianPoint(musicianId);

            JSONObject responseJson = new JSONObject();
            responseJson.put("message", resultMessage);

            byte[] responseBytes = responseJson.toString().getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, responseBytes.length);

            try (OutputStream os = exchange.getResponseBody()) {
                os.write(responseBytes);
            }

            System.out.println("[Server] Response sent: " + responseJson.toString(4));
        } catch (Exception e) {
            e.printStackTrace();
            responseError(exchange, e);
        } finally {
            exchange.close();
        }
    }


}
