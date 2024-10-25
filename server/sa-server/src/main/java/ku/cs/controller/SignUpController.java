package ku.cs.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;

import ku.cs.service.SignUpService;

public class SignUpController extends Controller {

    SignUpService service;

    public SignUpController(SignUpService service) {
        this.service = service;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String type = queryToMap(exchange.getRequestURI().getQuery()).get("type");
        System.out.println(exchange.getRequestURI().getQuery());
        System.out.println(type);
        if (exchange.getRequestMethod().equals("POST")) {
            handleRequest(exchange, (type != null && type.equalsIgnoreCase("musician")));
        } else {
            exchange.sendResponseHeaders(405, -1);
        }

        exchange.close();
    }

    private void handleRequest(HttpExchange exchange, boolean isMusician) throws IOException {
        try {
            InputStream is = exchange.getRequestBody();
            String jsonString = new String(is.readAllBytes(), StandardCharsets.UTF_8);

            System.out.println(jsonString);

            JSONObject jsonObject = new JSONObject(jsonString);

            if (isMusician)
                service.createMusician(jsonObject);
            else
                service.createUser(jsonObject);

            String response = "Sign Up Successfully";
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } catch (Exception e) {
            responseError(exchange, e);
        }
    }
}
