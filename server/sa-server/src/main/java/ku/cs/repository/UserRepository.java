package ku.cs.repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import ku.cs.entity.User;

public class UserRepository {

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public UserRepository(Connection connection) {
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

    public void updateUserPassword(String UUID, String password) throws SQLException {
        try {
            this.statement = connection.createStatement();
            this.statement.executeUpdate(String.format("UPDATE user SET ENCRYPTED_PASSWORD = '%s' WHERE UUID = '%s';", password, UUID));
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

    public User getUserByUUID(String uuid) throws SQLException {
        try {
            this.statement = connection.createStatement();

            this.resultSet = this.statement.executeQuery(
                String.format("SELECT USERNAME, EMAIL_ADDRESS, ENCRYPTED_PASSWORD, IMAGE_URL, NAME, PHONE_NUMBER, ROLE, UUID FROM user WHERE UUID = '%s';", uuid));


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

    public void updateUserInfo(String UUID, String name, String email, String phone_number) throws SQLException {
        try {
            this.statement = connection.createStatement();
            this.statement.executeUpdate(String.format("UPDATE user SET NAME = '%s', EMAIL_ADDRESS = '%s', PHONE_NUMBER = '%s' WHERE UUID = '%s';", name, email, phone_number, UUID));
        } finally {
            this.statement.close();
        }
    }

}
