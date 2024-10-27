package ku.cs.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;

import ku.cs.service.UserService;

public class UserInfoController extends Controller {

    private UserService service;

    public UserInfoController(UserService service) {
        this.service = service;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Map<String, String> map = queryToMap(exchange.getRequestURI().getQuery());
        String id = (map == null) ? null : map.get("id");
        if (exchange.getRequestMethod().equals("POST")) {
            handleRequest(exchange, id);
        } else {
            exchange.sendResponseHeaders(405, -1);
        }

        exchange.close();
    }

    private void handleRequest(HttpExchange exchange, String id) throws IOException {
        try {
            InputStream is = exchange.getRequestBody();
            String jsonString = new String(is.readAllBytes(), StandardCharsets.UTF_8);

            System.out.println(jsonString);

            JSONObject jsonObject = new JSONObject(jsonString);

            String accessToken = jsonObject.getString("access_token");

            JSONObject responseJSON = (id == null) ? service.getUser(accessToken) : service.getUser(accessToken, id);
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
