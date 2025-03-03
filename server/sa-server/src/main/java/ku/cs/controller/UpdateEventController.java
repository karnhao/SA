package ku.cs.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;

import ku.cs.service.EventService;

public class UpdateEventController extends Controller {

    private EventService service;

    public UpdateEventController(EventService service) {
        this.service = service;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (exchange.getRequestMethod().equals("PUT") || exchange.getRequestMethod().equals("PATCH")) {
            handleRequest(exchange);
        } else {
            exchange.sendResponseHeaders(405, -1);  // Method Not Allowed
        }
        exchange.close();
    }

    private void handleRequest(HttpExchange exchange) throws IOException {
        try {
            InputStream is = exchange.getRequestBody();
            String jsonString = new String(is.readAllBytes(), StandardCharsets.UTF_8);

            System.out.println(jsonString);  // ดูข้อมูลที่ได้รับจาก Client

            JSONObject jsonObject = new JSONObject(jsonString);

            // ดึงข้อมูลจาก request
            String eventID = jsonObject.getString("event_id");  // รับ event_id
            JSONObject eventData = jsonObject.getJSONObject("data");  // รับข้อมูลที่อัปเดต

            // เรียกใช้ EventService เพื่อทำการอัปเดตข้อมูลอีเวนต์ในฐานข้อมูล
            service.updateEvent(eventID, eventData);

            String response = "Update Event Successful";
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } catch (Exception e) {
            responseError(exchange, e);
        }
    }
}
