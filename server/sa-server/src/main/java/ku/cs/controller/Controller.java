package ku.cs.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public abstract class Controller implements HttpHandler {

    protected final void responseError(HttpExchange exchange, Exception e) {
        try {
            e.printStackTrace(); // TODO: Remove this line
            String errorMessage = e.getMessage();
            exchange.sendResponseHeaders(400, errorMessage.length());
            OutputStream os = exchange.getResponseBody();
            os.write(errorMessage.getBytes());
            os.close();
        } catch (IOException ea) {
            ea.printStackTrace();
        }
    }

    protected Map<String, String> queryToMap(String query) {
        if (query == null) {
            return null;
        }
        Map<String, String> result = new HashMap<>();
        for (String param : query.split("&")) {
            String[] entry = param.split("=");
            if (entry.length > 1) {
                result.put(entry[0], entry[1]);
            } else {
                result.put(entry[0], "");
            }
        }
        return result;
    }
}
