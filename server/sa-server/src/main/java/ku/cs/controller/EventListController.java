package ku.cs.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;

import ku.cs.service.EventService;

public class EventListController extends Controller {

    private EventService eventService;

    public EventListController(EventService eventService) {
        this.eventService = eventService;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (exchange.getRequestMethod().equals("POST")) {
            handleRequestAll(exchange);
        } else {
            exchange.sendResponseHeaders(405, -1);
        }

        exchange.close();
    }

    private void handleRequestAll(HttpExchange exchange) {
        try {

            String[] musician_roles = null;
            String musician_role_string = null;

            String query = exchange.getRequestURI().getQuery();
            if (query != null) {
                Map<String, String> map = queryToMap(query);
                musician_role_string = map.get("role");
                if (musician_role_string != null)
                    musician_roles = musician_role_string.split(",");
            }

            InputStream is = exchange.getRequestBody();
            String jsonString = new String(is.readAllBytes(), StandardCharsets.UTF_8);

            System.out.println(jsonString);

            JSONObject jsonObject = new JSONObject(jsonString);

            String accessToken = jsonObject.getString("access_token");

            JSONObject responseJSON = eventService.getAllEvent(accessToken, musician_roles);
            String response = responseJSON.toString();
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();

        } catch (Exception e) {
            responseError(exchange, e);
        }
    }

}
