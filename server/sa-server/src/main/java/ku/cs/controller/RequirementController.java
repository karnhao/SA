package ku.cs.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;

import ku.cs.service.EventService;

public class RequirementController extends Controller {

    private EventService service;

    public RequirementController(EventService service) {
        this.service = service;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        switch (exchange.getRequestURI().getPath()) {
            case "/requirement/musician/set-status":
                handleSetMusicianStatus(exchange);
                break;
            case "/requirement/stereo/set-status":
                handleSetStereoStatus(exchange);
                break;
            default:
                this.responseError(exchange,
                        new Exception("Invalid request URI: " + exchange.getRequestURI().toString()));
        }
    }

    private void handleSetMusicianStatus(HttpExchange exchange) throws IOException {
        try {
            InputStream is = exchange.getRequestBody();
            String jsonString = new String(is.readAllBytes(), StandardCharsets.UTF_8);

            System.out.println(jsonString);

            JSONObject jsonObject = new JSONObject(jsonString);

            // JSON need access_token: string, eid: string, rid: string
            String response = service.setMusicianRequirementStatus(jsonObject);
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } catch (Exception e) {
            responseError(exchange, e);
        }
    }

    private void handleSetStereoStatus(HttpExchange exchange) throws IOException {
        try {
            InputStream is = exchange.getRequestBody();
            String jsonString = new String(is.readAllBytes(), StandardCharsets.UTF_8);

            System.out.println(jsonString);

            JSONObject jsonObject = new JSONObject(jsonString);

            // JSON need access_token: string, eid: string, rid: string
            String response = service.setStereoRequirementStatus(jsonObject);
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } catch (Exception e) {
            responseError(exchange, e);
        }
    }

}
