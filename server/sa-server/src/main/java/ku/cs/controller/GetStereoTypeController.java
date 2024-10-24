package ku.cs.controller;

import java.io.IOException;
import java.io.OutputStream;

import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;

import ku.cs.service.StereoTypeService;

public class GetStereoTypeController extends Controller {
    private StereoTypeService service;

    public GetStereoTypeController(StereoTypeService service) {
        this.service = service;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (exchange.getRequestMethod().equals("GET")) {
            handleRequest(exchange);
        } else {
            exchange.sendResponseHeaders(405, -1);
        }
        exchange.close();
    }

    private void handleRequest(HttpExchange exchange) {
        try {
            JSONObject responseJSON = service.getAllStereoType();
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
