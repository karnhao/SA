package ku.cs.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;

import ku.cs.service.UserService;

public class UpdateMusicianAvailableRoleController extends Controller {

    private UserService service;

    public UpdateMusicianAvailableRoleController(UserService service) {
        this.service = service;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            InputStream is = exchange.getRequestBody();
            String jsonString = new String(is.readAllBytes(), StandardCharsets.UTF_8);

            System.out.println(jsonString);

            JSONObject jsonObject = new JSONObject(jsonString);

            String response = service.updateMusicianAvailableRole(jsonObject.getString("access_token"),
                    jsonObject.getJSONArray("roles").toList().stream().map(Object::toString).toList());
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } catch (Exception e) {
            responseError(exchange, e);
        }
    }

}
