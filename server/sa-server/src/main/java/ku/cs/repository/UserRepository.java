package ku.cs.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.LinkedList;

import ku.cs.entity.Musician;
import ku.cs.entity.MusicianRole;
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
                            ((role != null && !role.isEmpty()) ? String.format("WHERE ROLE = '%s'", role) : "") + ";");

            while (this.resultSet.next()) {
                User user = new User();
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

    public List<Musician> getAllMusicians() throws SQLException {
        LinkedList<Musician> users = new LinkedList<Musician>();
        try {
            this.statement = connection.createStatement();

            // ทำ SQL Injection ไม่ได้เพราะระบบจะทำงานเพียง statement เดียว
            this.resultSet = this.statement.executeQuery(
                    "SELECT USERNAME, EMAIL_ADDRESS, ENCRYPTED_PASSWORD, NAME, PHONE_NUMBER, ROLE, u.UUID, m.BANK_NAME, m.BANK_NUMBER FROM user u JOIN musician m ON u.`UUID` = m.`UUID` WHERE ROLE = 'Musician';");

            while (this.resultSet.next()) {
                User user = new User();
                String resultUsername = resultSet.getString("USERNAME");
                String resultEmail = resultSet.getString("EMAIL_ADDRESS");
                String resultPassword = resultSet.getString("ENCRYPTED_PASSWORD");
                String resultName = resultSet.getString("NAME");
                String resultPhoneNumber = resultSet.getString("PHONE_NUMBER");
                String resultRole = resultSet.getString("ROLE");
                String resultUUID = resultSet.getString("UUID");
                String resultBankName = resultSet.getString("BANK_NAME");
                String resultBankNumber = resultSet.getString("BANK_NUMBER");

                user.setUsername(resultUsername);
                user.setEmail(resultEmail);
                user.setPassword(resultPassword);
                user.setName(resultName);
                user.setPhone_number(resultPhoneNumber);
                user.setRole(resultRole);
                user.setUuid(resultUUID);

                Musician musician = new Musician(user);
                musician.setBankName(resultBankName);
                musician.setBankNumber(resultBankNumber);

                users.add(musician);
            }

            return users;

        } catch (SQLException e) {
            return null;
        } finally {
            this.statement.close();
        }
    }

    public void setAvailableRoles(String uuid, List<MusicianRole> roles) throws SQLException {
        try {
            this.statement = connection.createStatement();

            // ทำ SQL Injection ไม่ได้เพราะระบบจะทำงานเพียง statement เดียว
            this.statement.executeUpdate(String.format(
                    "DELETE FROM availablemusicianrole WHERE UUID = '%s';", uuid));

            for (MusicianRole role : roles) {
                this.statement.executeUpdate(String.format(
                        "INSERT INTO availablemusicianrole (UUID, ROLE_ID) VALUES ('%s', '%s');", uuid, role.getId()));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            this.statement.close();
        }
    }

    public List<MusicianRole> getAvailableMusicianRoles(String uuid) throws SQLException {
        LinkedList<MusicianRole> roles = new LinkedList<MusicianRole>();
        try {
            this.statement = connection.createStatement();

            this.resultSet = this.statement.executeQuery(String.format(
                    "SELECT a.ROLE_ID, m.ROLE_NAME FROM availablemusicianrole a JOIN musicianrole m ON a.ROLE_ID = m.ROLE_ID WHERE a.UUID = '%s';", uuid));

            while (this.resultSet.next()) {
                MusicianRole role = new MusicianRole();
                String resultID = resultSet.getString("ROLE_ID");
                String resultName = resultSet.getString("ROLE_NAME");

                role.setId(resultID);
                role.setName(resultName);

                roles.add(role);
            }

            return roles;

        } catch (SQLException e) {
            return null;
        } finally {
            this.statement.close();
        }
    }

    @Deprecated
    public List<User> getMusiciansByRoleIdOld(String roleId) {
        List<User> musicians = new LinkedList<>();
        String query = "SELECT m.UUID, m.BANK_NUMBER, m.BANK_NAME, u.NAME, u.USERNAME, u.EMAIL_ADDRESS, u.PHONE_NUMBER, u.ROLE, mr.ROLE_ID, mr.ROLE_NAME " +
                       "FROM musician m " +
                       "JOIN availablemusicianrole amr ON m.UUID = amr.UUID " +
                       "JOIN user u ON m.UUID = u.UUID " +
                       "JOIN musicianrole mr ON amr.ROLE_ID = mr.ROLE_ID " +
                       "WHERE amr.ROLE_ID = ?";

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setString(1, roleId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                User musician = new User();
                musician.setUuid(rs.getString("UUID"));
                musician.setName(rs.getString("NAME"));
                musician.setUsername(rs.getString("USERNAME"));
                musician.setEmail(rs.getString("EMAIL_ADDRESS"));
                musician.setPhone_number(rs.getString("PHONE_NUMBER"));
                musician.setRole(rs.getString("ROLE"));

                musicians.add(musician);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return musicians;
    }

    public List<User> getMusiciansByRoleId(String roleId) {
        List<User> musicians = new LinkedList<>();
        String query = "SELECT m.UUID, m.BANK_NUMBER, m.BANK_NAME, u.NAME, u.USERNAME, u.EMAIL_ADDRESS, u.PHONE_NUMBER, u.ROLE, mr.ROLE_ID, mr.ROLE_NAME " +
                       "FROM musician m " +
                       "JOIN availablemusicianrole amr ON m.UUID = amr.UUID " +
                       "JOIN user u ON m.UUID = u.UUID " +
                       "JOIN musicianrole mr ON amr.ROLE_ID = mr.ROLE_ID " +
                       "WHERE amr.ROLE_ID = ?";
        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setString(1, roleId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Musician musician = new Musician(new User());
                musician.setUuid(rs.getString("UUID"));
                musician.setName(rs.getString("NAME"));
                musician.setUsername(rs.getString("USERNAME"));
                musician.setEmail(rs.getString("EMAIL_ADDRESS"));
                musician.setPhone_number(rs.getString("PHONE_NUMBER"));
                musician.setRole(rs.getString("ROLE"));

                // Get the work count since 4 months ago
                String countQuery = "SELECT COUNT(*) AS workCount FROM history WHERE UUID = ? AND DATE >= DATE_SUB(CURDATE(), INTERVAL 4 MONTH)";
                PreparedStatement countStmt = connection.prepareStatement(countQuery);
                countStmt.setString(1, musician.getUuid());
                ResultSet countRs = countStmt.executeQuery();
                int workCount = 0;
                if (countRs.next()) {
                    workCount = countRs.getInt("workCount");
                }
                countRs.close();
                countStmt.close();

                musician.setWorkCount(workCount);
                musicians.add(musician);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return musicians;
    }
}
