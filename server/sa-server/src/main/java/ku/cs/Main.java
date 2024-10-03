package ku.cs;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.sun.net.httpserver.HttpServer;

import ku.cs.controller.SignUpController;

public class Main {
    public static void main(String[] args) throws IOException {

        // Connect to Database
        Connection conn = null;
        String dataSourceUrl = "jdbc:mysql://localhost/sa";
        String user = "root";
        String password = "";

        System.out.println("Connecting to " + dataSourceUrl);

        try {
            conn = DriverManager.getConnection(dataSourceUrl, user, password);
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }


        int port = 25565;

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", new MyHandler(conn));
        server.createContext("/reg", new SignUpController());

        server.setExecutor(null);
        server.start();

        System.out.println("Server is running on port " + port);
    }
}