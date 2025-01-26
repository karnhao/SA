package ku.cs.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;

import ku.cs.service.UserService;

public class GetAllUserController extends Controller {

    private UserService service;

    public GetAllUserController(UserService userService) {
        this.service = userService;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String role = null;
        String available_role = null;

        String query = exchange.getRequestURI().getQuery();
        if (query != null) {
            Map<String, String> map = queryToMap(query);
            role = map.get("role");
            available_role = map.get("available-role");
        }
        if (role == null) role = "Customer";

        System.out.println(exchange.getRequestURI().getQuery());
        System.out.println(role);
        System.out.println(available_role);

        if (exchange.getRequestMethod().equals("POST")) {
            handleRequestAll(exchange, role, available_role);
        } else {
            exchange.sendResponseHeaders(405, -1);
        }

        exchange.close();
    }

    private void handleRequestAll(HttpExchange exchange, String role, String available_role) {
        try {
            InputStream is = exchange.getRequestBody();
            String jsonString = new String(is.readAllBytes(), StandardCharsets.UTF_8);

            System.out.println(jsonString);

            JSONObject jsonObject = new JSONObject(jsonString);

            String accessToken = jsonObject.getString("access_token");

            JSONObject responseJSON = service.getAllUserJsonObjects(accessToken, role, available_role);
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
