package ku.cs;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class MyHandler implements HttpHandler {

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public MyHandler(Connection connection) {
        this.connection = connection;
        this.statement = null;
        this.resultSet = null;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response = "Hello";
        // int columnCount;
        // try {
        //     this.statement = connection.createStatement();
        //     this.resultSet = this.statement.executeQuery("SELECT * FROM USER");
        //     response = "Row : " + this.resultSet.getRow() + "\n" + this.resultSet.getMetaData() + "\n";

        //     columnCount = resultSet.getMetaData().getColumnCount();
        //     while (this.resultSet.next()) {
        //         for (int i = 1; i <= columnCount; i++) {
        //             response += this.resultSet.getObject(i).toString() + ", ";
        //         }
        //         response += "\n";
        //     }

        // } catch (Exception e) {
        //     e.printStackTrace();
        //     exchange.sendResponseHeaders(500, response.length());
        //     OutputStream os = exchange.getResponseBody();
        //     os.write(response.getBytes());
        //     os.close();
        //     return;
        // } finally {
        //     try {
        //         this.statement.close();
        //     } catch (SQLException e) {
        //         e.printStackTrace();
        //     }
        // }
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

}
