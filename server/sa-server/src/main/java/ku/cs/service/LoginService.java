package ku.cs.service;

import java.sql.SQLException;

import org.json.JSONObject;

import ku.cs.entity.AccessToken;
import ku.cs.entity.User;
import ku.cs.exception.LoginException;
import ku.cs.repository.UserRepository;

public class LoginService {

    private UserRepository repository;

    public LoginService(UserRepository repository) {
        this.repository = repository;
    }

    private boolean isUsernameValid(String username) throws SQLException {
        return repository.getUserByUserName(username) != null;
    }

    private boolean validatePassword(User user, String password) throws SQLException {
        return user.validatePassword(password);
    }

    public JSONObject login(String username, String password) throws LoginException, SQLException {

        
        if (!isUsernameValid(username)) {
            throw new LoginException();
        }

        User user = repository.getUserByUserName(username);
        
        if (!validatePassword(user, password)) {
            throw new LoginException("Invalid password");
        }


        AccessToken accessToken = AuthenticationService.get().registerToken(user);

        JSONObject response = new JSONObject();
        response.put("access_token", accessToken.getToken());
        response.put("life_time", accessToken.getLifetime().toSeconds());

        return response;
    }

    
}
