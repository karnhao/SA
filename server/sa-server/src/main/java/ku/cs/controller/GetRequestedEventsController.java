package ku.cs.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;

import ku.cs.service.EventService;

public class GetRequestedEventsController extends Controller {

    private EventService service;

    public GetRequestedEventsController(EventService userService) {
        this.service = userService;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String baseOn = "musician";

        String query = exchange.getRequestURI().getQuery();

        Map<String, String> map = queryToMap(query);
        baseOn = map.get("base");

        if (exchange.getRequestMethod().equals("POST")) {
            handleRequestAll(exchange, baseOn);
        } else {
            exchange.sendResponseHeaders(405, -1);
        }

        exchange.close();
    }

    private void handleRequestAll(HttpExchange exchange, String baseOn) {
        try {
            InputStream is = exchange.getRequestBody();
            String jsonString = new String(is.readAllBytes(), StandardCharsets.UTF_8);

            System.out.println(jsonString);

            JSONObject jsonObject = new JSONObject(jsonString);

            String accessToken = jsonObject.getString("access_token");

            JSONObject responseJSON = baseOn.equalsIgnoreCase("musician") ?
            service.getRequestedEventsMusician(accessToken) : service.getRequestedEventsStereo(accessToken);
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
