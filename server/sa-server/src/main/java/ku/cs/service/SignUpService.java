package ku.cs.service;

import java.sql.SQLException;
import java.util.UUID;

import javax.naming.InvalidNameException;

import org.json.JSONObject;

import ku.cs.entity.User;
import ku.cs.repository.UserRepository;
import ku.cs.request.SignUpRequest;

public class SignUpService {

    private UserRepository repository;
    
    public SignUpService(UserRepository repository) {
        this.repository = repository;
    }

    public boolean isUsernameAvailable(String username) throws SQLException {
        return repository.getUserByUserName(username) == null;
    }

    private void validateNull(String s, String name) {
        if (s == null || s.isEmpty()) throw new NullPointerException(name + " is null");
    }

    private User createUserEntity(SignUpRequest signUpRequest) {
        User user = new User();
        user.setEmail(signUpRequest.getEmail());
        user.setName(signUpRequest.getName());
        user.setPassword(signUpRequest.getPassword());
        user.setUsername(signUpRequest.getUsername());
        user.setRole(signUpRequest.getRole());
        user.setPhone_number(signUpRequest.getPhone_number());
        user.setUuid(UUID.randomUUID().toString());
        
        return user;
    }

    public void createUser(JSONObject jsonObject) throws InvalidNameException, SQLException {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setEmail(jsonObject.getString("email"));
        signUpRequest.setName(jsonObject.getString("name"));
        signUpRequest.setPassword(jsonObject.getString("password"));
        signUpRequest.setPhone_number(jsonObject.getString("phone_number"));
        signUpRequest.setRole(jsonObject.getString("role"));
        signUpRequest.setUsername(jsonObject.getString("username"));

        this.createUser(signUpRequest);
    }

    public void createUser(SignUpRequest signUpRequest) throws InvalidNameException, SQLException {

        // check null
        validateNull(signUpRequest.getUsername(), "username");
        validateNull(signUpRequest.getEmail(), "email");
        validateNull(signUpRequest.getName(), "name");
        validateNull(signUpRequest.getPhone_number(), "phone number");
        validateNull(signUpRequest.getRole(), "role");
        validateNull(signUpRequest.getPassword(), "password");

        // check username available
        if (!isUsernameAvailable(signUpRequest.getUsername())) throw new InvalidNameException("This username is already taken");

        // create user entity
        User userEntity = createUserEntity(signUpRequest);

        
        repository.createUser(userEntity);
    }

    public User getUserByUserName(String userName) throws SQLException {
        return repository.getUserByUserName(userName);
    }
}
