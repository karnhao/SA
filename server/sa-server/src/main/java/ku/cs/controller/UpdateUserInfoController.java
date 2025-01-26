package ku.cs.controller;

import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import ku.cs.service.UserService;
import com.sun.net.httpserver.HttpExchange;

public class UpdateUserInfoController extends Controller{
    private UserService service;

    public UpdateUserInfoController(UserService userService) {
        this.service = userService;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            InputStream is = exchange.getRequestBody();
            String jsonString = new String(is.readAllBytes(), StandardCharsets.UTF_8);

            System.out.println(jsonString);

            JSONObject jsonObject = new JSONObject(jsonString);

            String response = service.updateUserInfo(jsonObject.getString("access_token"),
                    jsonObject.getString("name"), jsonObject.getString("email"), jsonObject.getString("phone_number"));
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } catch (Exception e) {
            responseError(exchange, e);
        }
    }
}
