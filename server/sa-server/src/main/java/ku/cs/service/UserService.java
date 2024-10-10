package ku.cs.service;

import java.sql.SQLException;

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

}