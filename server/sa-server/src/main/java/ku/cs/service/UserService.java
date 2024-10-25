package ku.cs.service;

import java.sql.SQLException;
import java.util.List;

import javax.naming.AuthenticationException;

import org.json.JSONArray;
import org.json.JSONObject;

import ku.cs.entity.User;
import ku.cs.repository.UserRepository;

public class UserService {

    private UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public JSONObject getUser(String token) throws SQLException {
        AuthenticationService authenticationService = AuthenticationService.get();
        String uuid = authenticationService.getUserID(token);
        User user = repository.getUserByUUID(uuid);

        JSONObject response = new JSONObject();
        response.put("username", user.getUsername());
        response.put("email", user.getEmail());
        response.put("name", user.getName());
        response.put("phone_number", user.getPhone_number());
        response.put("role", user.getRole());
        response.put("uuid", user.getUuid());

        return response;
    }

    public String updateUserInfo(String token, String name, String email, String phoneNumber) throws SQLException {
        AuthenticationService authenticationService = AuthenticationService.get();
        String uuid = authenticationService.getUserID(token);
        
        repository.updateUserInfo(uuid, name, email, phoneNumber);
        
        return "Success";
    }

    public String updatePassword(String token, String oldPassword, String newPassword) throws SQLException, AuthenticationException {
        AuthenticationService authenticationService = AuthenticationService.get();
        String uuid = authenticationService.getUserID(token);
        User user = repository.getUserByUUID(uuid);
        
        if (user.validatePassword(oldPassword)) {
            repository.updateUserPassword(uuid, newPassword);
            return "OK";
        } else {
            throw new AuthenticationException("Invalid password");
        }
    }

    public String updateMusicianBankInfo(String token, String bankName, String bankNumber) throws SQLException {
        AuthenticationService authenticationService = AuthenticationService.get();
        String uuid = authenticationService.getUserID(token);
        
        repository.updateMusicianBankInfo(uuid, bankName, bankNumber);
        return "OK";
    }

    public String updateMusicianAvailableRole(String token, List<String> role_ids) throws SQLException {
        AuthenticationService authenticationService = AuthenticationService.get();
        String uuid = authenticationService.getUserID(token);
        
        repository.updateMusicianAvailableRole(uuid, role_ids);
        return "OK";
    }

    public JSONObject getAllUserJsonObjects(String token, String role) throws SQLException, AuthenticationException {

        AuthenticationService authenticationService = AuthenticationService.get();
        String uuid = authenticationService.getUserID(token);

        if (uuid == null) throw new AuthenticationException("Access denied");

        User user = repository.getUserByUUID(uuid);
        if (!user.getRole().equalsIgnoreCase("agent")) throw new AuthenticationException("Access denied");
        List<User> users = repository.getAllUsers(role);

        JSONObject jsonObject = new JSONObject();

        JSONArray jsonArray = new JSONArray();
        users.stream().map((u) -> {
            JSONObject o = new JSONObject();
            o.put("username", user.getUsername());
            o.put("email", user.getEmail());
            o.put("name", user.getName());
            o.put("phone_number", user.getPhone_number());
            o.put("role", user.getRole());
            o.put("uuid", user.getUuid());

            return o;
        }).forEach(jsonArray::put);

        jsonObject.put("users", jsonArray);

        return jsonObject;
    }

}
