package ku.cs.controller;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;

public class HelloController extends Controller {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response = "Hello";
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

}
