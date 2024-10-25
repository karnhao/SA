package ku.cs.repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public abstract class Repository {
    protected Connection connection;
    protected Statement statement;
    protected ResultSet resultSet;

    Repository(Connection connection) {
        this.connection = connection;
    }
}
