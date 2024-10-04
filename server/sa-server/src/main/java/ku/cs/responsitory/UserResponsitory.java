package ku.cs.responsitory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import ku.cs.entity.User;

public class UserResponsitory {

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public UserResponsitory(Connection connection) {
        this.connection = connection;
    }

    public void createUser(User user) throws SQLException {
        try {
            this.statement = connection.createStatement();

            this.statement.executeUpdate("INSERT INTO user " +
                    "(UUID, USERNAME, NAME, ENCRYPTED_PASSWORD, IMAGE_URL, EMAIL_ADDRESS, PHONE_NUMBER, ROLE) " +
                    String.format("VALUES ('%s','%s','%s','%s','%s','%s','%s','%s');",
                            user.getUuid(),
                            user.getUsername(),
                            user.getName(),
                            user.getPassword(),
                            user.getImage_url(),
                            user.getEmail(),
                            user.getPhone_number(),
                            user.getRole()));
        } finally {
            this.statement.close();
        }
    }

    public User getUserByUserName(String username) throws SQLException {
        try {
            this.statement = connection.createStatement();

            this.resultSet = this.statement.executeQuery(
                String.format("SELECT USERNAME, EMAIL_ADDRESS, ENCRYPTED_PASSWORD, IMAGE_URL, NAME, PHONE_NUMBER, ROLE, UUID FROM user WHERE USERNAME = '%s';", username));


            this.resultSet.next();
            String resultUsername = resultSet.getString("USERNAME");
            String resultEmail = resultSet.getString("EMAIL_ADDRESS");
            String resultPassword = resultSet.getString("ENCRYPTED_PASSWORD");
            String resultImageUrl = resultSet.getString("IMAGE_URL");
            String resultName = resultSet.getString("NAME");
            String resultPhoneNumber = resultSet.getString("PHONE_NUMBER");
            String resultRole = resultSet.getString("ROLE");
            String resultUUID = resultSet.getString("UUID");

            return new User(resultName, resultUsername, resultUUID, resultPassword, resultImageUrl, resultEmail, resultPhoneNumber, resultRole);

        } catch (SQLException e) {
            return null;
        } finally {
            this.statement.close();
        }
    }

}
