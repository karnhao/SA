package ku.cs.controller;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public abstract class Controller implements HttpHandler {

    protected final void responseError(HttpExchange exchange, Exception e) {
        try {
        e.printStackTrace();
        String errorMessage = e.getClass().getSimpleName() + " " + e.getMessage();
        exchange.sendResponseHeaders(400, errorMessage.length());
        OutputStream os = exchange.getResponseBody();
        os.write(errorMessage.getBytes());
        os.close();
        } catch (IOException ea) {
            ea.printStackTrace();
        }
    }
}
