package ku.cs.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.LinkedList;

import ku.cs.entity.User;

public class UserRepository extends Repository {

    public UserRepository(Connection connection) {
        super(connection);
    }

    public void createUser(User user) throws SQLException {
        try {
            this.statement = connection.createStatement();

            this.statement.executeUpdate("INSERT INTO user " +
                    "(UUID, USERNAME, NAME, ENCRYPTED_PASSWORD, EMAIL_ADDRESS, PHONE_NUMBER, ROLE) " +
                    String.format("VALUES ('%s','%s','%s','%s','%s','%s','%s');",
                            user.getUuid(),
                            user.getUsername(),
                            user.getName(),
                            user.getPassword(),
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
            this.statement.executeUpdate(
                    String.format("UPDATE user SET ENCRYPTED_PASSWORD = '%s' WHERE UUID = '%s';", password, UUID));
        } finally {
            this.statement.close();
        }
    }

    public User getUserByUserName(String username) throws SQLException {
        try {
            this.statement = connection.createStatement();

            this.resultSet = this.statement.executeQuery(
                    String.format(
                            "SELECT USERNAME, EMAIL_ADDRESS, ENCRYPTED_PASSWORD, NAME, PHONE_NUMBER, ROLE, UUID FROM user WHERE USERNAME = '%s';",
                            username));

            this.resultSet.next();
            String resultUsername = resultSet.getString("USERNAME");
            String resultEmail = resultSet.getString("EMAIL_ADDRESS");
            String resultPassword = resultSet.getString("ENCRYPTED_PASSWORD");
            String resultName = resultSet.getString("NAME");
            String resultPhoneNumber = resultSet.getString("PHONE_NUMBER");
            String resultRole = resultSet.getString("ROLE");
            String resultUUID = resultSet.getString("UUID");

            return new User(resultName, resultUsername, resultUUID, resultPassword, null, resultEmail,
                    resultPhoneNumber, resultRole);

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
                    String.format(
                            "SELECT USERNAME, EMAIL_ADDRESS, ENCRYPTED_PASSWORD, NAME, PHONE_NUMBER, ROLE, UUID FROM user WHERE UUID = '%s';",
                            uuid));

            this.resultSet.next();
            String resultUsername = resultSet.getString("USERNAME");
            String resultEmail = resultSet.getString("EMAIL_ADDRESS");
            String resultPassword = resultSet.getString("ENCRYPTED_PASSWORD");
            String resultName = resultSet.getString("NAME");
            String resultPhoneNumber = resultSet.getString("PHONE_NUMBER");
            String resultRole = resultSet.getString("ROLE");
            String resultUUID = resultSet.getString("UUID");

            return new User(resultName, resultUsername, resultUUID, resultPassword, null, resultEmail,
                    resultPhoneNumber, resultRole);

        } catch (SQLException e) {
            return null;
        } finally {
            this.statement.close();
        }
    }

    public void updateUserInfo(String UUID, String name, String email, String phone_number) throws SQLException {
        try {
            this.statement = connection.createStatement();
            this.statement.executeUpdate(String.format(
                    "UPDATE user SET NAME = '%s', EMAIL_ADDRESS = '%s', PHONE_NUMBER = '%s' WHERE UUID = '%s';", name,
                    email, phone_number, UUID));
        } finally {
            this.statement.close();
        }
    }

    public void createMusician(User user, String bankName, String bankNumber) throws SQLException {
        try {
            this.statement = connection.createStatement();

            System.out.println("INSERT INTO user " +
                    "(UUID, USERNAME, NAME, ENCRYPTED_PASSWORD, EMAIL_ADDRESS, PHONE_NUMBER, ROLE) " +
                    String.format("VALUES ('%s','%s','%s','%s','%s','%s','%s');\n",
                            user.getUuid(),
                            user.getUsername(),
                            user.getName(),
                            user.getPassword(),
                            user.getEmail(),
                            user.getPhone_number(),
                            user.getRole())
                    +

                    "INSERT INTO musician " +
                    "(UUID, BANK_NUMBER, BANK_NAME) " +
                    String.format("VALUES ('%s','%s','%s');", user.getUuid(), bankNumber, bankName)

            );

            this.statement.executeUpdate("INSERT INTO user " +
                    "(UUID, USERNAME, NAME, ENCRYPTED_PASSWORD, EMAIL_ADDRESS, PHONE_NUMBER, ROLE) " +
                    String.format("VALUES ('%s','%s','%s','%s','%s','%s','%s');\n",
                            user.getUuid(),
                            user.getUsername(),
                            user.getName(),
                            user.getPassword(),
                            user.getEmail(),
                            user.getPhone_number(),
                            user.getRole()));

            this.statement.executeUpdate("INSERT INTO musician " +
                    "(UUID, BANK_NUMBER, BANK_NAME) " +
                    String.format("VALUES ('%s','%s','%s');", user.getUuid(), bankNumber, bankName));
        } finally {
            this.statement.close();
        }
    }

    public void updateMusicianBankInfo(String UUID, String bankName, String bankNumber) throws SQLException {
        try {
            this.statement = connection.createStatement();
            this.statement.executeUpdate(String.format(
                    "UPDATE musician SET BANK_NUMBER = '%s', BANK_NAME = '%s' WHERE UUID = '%s';", bankNumber,
                    bankName, UUID));
        } finally {
            this.statement.close();
        }
    }

    public void updateMusicianAvailableRole(String UUID, List<String> role_ids) throws SQLException {
        try {

            this.statement = connection.createStatement();
            this.statement.executeUpdate(String.format(
                    "DELETE FROM availablemusicianrole WHERE UUID = '%s';", UUID));

            if (role_ids != null && role_ids.size() > 0) {
                role_ids.forEach(t -> {
                    try {
                        this.statement.executeUpdate(String.format(
                                "INSERT INTO availablemusicianrole (UUID, ROLE_ID) VALUES ('%s', '%s');", UUID, t));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
            }

        } finally {
            this.statement.close();
        }
    }

    public List<User> getAllUsers() throws SQLException {
        return this.getAllUsers(null);
    }

    public List<User> getAllUsers(String role) throws SQLException {
        LinkedList<User> users = new LinkedList<User>();
        try {
            this.statement = connection.createStatement();

            // ทำ SQL Injection ไม่ได้เพราะระบบจะทำงานเพียง statement เดียว
            this.resultSet = this.statement.executeQuery(
                    "SELECT USERNAME, EMAIL_ADDRESS, ENCRYPTED_PASSWORD, NAME, PHONE_NUMBER, ROLE, UUID FROM user " +
                            ((role != null && !role.isEmpty()) ? String.format("WHERE ROLE = %s", role) : "") + ";");

            User user = new User();
            while (this.resultSet.next()) {
                String resultUsername = resultSet.getString("USERNAME");
                String resultEmail = resultSet.getString("EMAIL_ADDRESS");
                String resultPassword = resultSet.getString("ENCRYPTED_PASSWORD");
                String resultName = resultSet.getString("NAME");
                String resultPhoneNumber = resultSet.getString("PHONE_NUMBER");
                String resultRole = resultSet.getString("ROLE");
                String resultUUID = resultSet.getString("UUID");

                user.setUsername(resultUsername);
                user.setEmail(resultEmail);
                user.setPassword(resultPassword);
                user.setName(resultName);
                user.setPhone_number(resultPhoneNumber);
                user.setRole(resultRole);
                user.setUuid(resultUUID);

                users.add(user);
            }

            return users;

        } catch (SQLException e) {
            return null;
        } finally {
            this.statement.close();
        }
    }

}
