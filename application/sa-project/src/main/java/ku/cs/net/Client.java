package ku.cs.net;

import javafx.application.Platform;
import javafx.util.Duration;
import ku.cs.controller.RootController;
import ku.cs.service.RootService;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class Client {
    private static Client client = null;
    private final String serverIp;
    private final short serverPort;
    private String accessToken;

    // Get Key
    public static void init(String ip, short port) throws URISyntaxException, IOException {
        if (client != null) return;

        // Check Server is Online
        URL url = new URI("http://" + ip + ":" + port + "/").toURL();

        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");

        int responseCode = httpURLConnection.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();

            Platform.runLater(() -> RootService.getController().showBar(response.toString(), RootController.Color.GREEN, Duration.seconds(2)));
        } else {
            Platform.runLater(() -> RootService.getController().showBar(responseCode + "", RootController.Color.GREEN, Duration.seconds(2)));
        }

        client = new Client(ip, port);
    }

    public static Client getClient() {
        return client;
    }

    private Client(String ip, short port) {
        this.serverIp = ip;
        this.serverPort = port;
    }

    public String getHostUrlString() {
        return "http://" + this.serverIp + ":" + this.serverPort;
    }

    public HttpURLConnection getHttpURLConnection(String bodyJson, String path) throws URISyntaxException, IOException {
        URL url = new URI(Client.getClient().getHostUrlString() + path).toURL();

        System.out.println(url);

        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("POST");

        httpURLConnection.setRequestProperty("Content-Type", "application/json");
        httpURLConnection.setRequestProperty("Accept", "application/json");

        httpURLConnection.setDoOutput(true);

        try (OutputStream os = httpURLConnection.getOutputStream()) {
            byte[] input = bodyJson.getBytes(StandardCharsets.UTF_8);
            os.write(input);
        }
        return httpURLConnection;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
