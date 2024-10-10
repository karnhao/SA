package ku.cs;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import org.json.JSONObject;

import com.sun.net.httpserver.HttpServer;

import ku.cs.controller.HelloController;
import ku.cs.controller.LoginController;
import ku.cs.controller.SignUpController;
import ku.cs.controller.UserInfoController;
import ku.cs.repository.UserRepository;
import ku.cs.service.LoginService;
import ku.cs.service.SignUpService;
import ku.cs.service.UserService;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Connection conn = null;
        String user;
        String password;
        String dataSourceUrl = "jdbc:mysql://localhost/sa";
        
        try (InputStream inputStream = Main.class.getResourceAsStream("./database-access.json")) {
            Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8.name()).useDelimiter("\\A");
            String content = scanner.hasNext() ? scanner.next() : "{}";
            
            JSONObject json = new JSONObject(content);
            user = json.getString("DB_USERNAME");
            password = json.getString("DB_PASSWORD");

            scanner.close();
        }
        
        // Connect to Database
        System.out.println("Connecting to " + dataSourceUrl);
        
        try {
            conn = DriverManager.getConnection(dataSourceUrl, user, password);
            System.out.println("Database connected successfully");
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }


        int port = 25565;

        // create repository
        UserRepository userResponsitory = new UserRepository(conn);

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", new HelloController());
        server.createContext("/reg", new SignUpController(new SignUpService(userResponsitory)));
        server.createContext("/login", new LoginController(new LoginService(userResponsitory)));
        server.createContext("/userinfo", new UserInfoController(new UserService(userResponsitory)));

        server.setExecutor(null);
        server.start();

        System.out.println("Server is running on port " + port);
    }
}