package ku.cs.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class SignUpController implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (exchange.getRequestMethod().equals("GET")) {
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
        // JSONObject jsonObject = new JSONObject(jsonString);
        
        String response = "Hello World!";
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        
    }
}
