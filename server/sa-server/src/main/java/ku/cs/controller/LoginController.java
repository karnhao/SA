package ku.cs.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import ku.cs.service.SignUpService;

public class LoginController implements HttpHandler {

    SignUpService service;

    public LoginController(SignUpService service) {
        this.service = service;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (exchange.getRequestMethod().equals("POST")) {
            handleRequest(exchange);
        } else {
            exchange.sendResponseHeaders(405, -1);
        }

        exchange.close();
    }

    private void handleRequest(HttpExchange exchange) throws IOException {
        InputStream is = exchange.getRequestBody();
        String jsonString = new String(is.readAllBytes(), StandardCharsets.UTF_8);
        
        System.out.println(jsonString);

        JSONObject jsonObject = new JSONObject(jsonString);

        try {
            service.createUser(jsonObject);
            String response = "Create User Successfully";
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
            exchange.sendResponseHeaders(500, -1);
        }

    }
}
