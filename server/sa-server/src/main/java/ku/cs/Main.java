package ku.cs;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

public class Main {
    public static void main(String[] args) throws IOException {
        int port = 9000;

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", new MyHandler());

        server.setExecutor(null);
        server.start();

        System.out.println("Server is running on port " + port);
    }
}