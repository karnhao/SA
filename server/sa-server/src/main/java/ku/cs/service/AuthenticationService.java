package ku.cs.service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.Optional;

import ku.cs.entity.AccessToken;
import ku.cs.entity.User;

public class AuthenticationService {
    private final HashMap<AccessToken, String> MAP;
    private static AuthenticationService service;

    public static AuthenticationService get() {
        if (AuthenticationService.service == null) {
            service = new AuthenticationService();
        }

        return service;
    }

    private AuthenticationService() {
        this.MAP = new HashMap<AccessToken, String>();
    }

    private String generateToken() {
        Base64.Encoder encoder = Base64.getUrlEncoder();
        SecureRandom random = new SecureRandom();
        byte[] randombytes = new byte[0xff];
        random.nextBytes(randombytes);
        return encoder.encodeToString(randombytes);
    }

    public AccessToken registerToken(User user) {
        AccessToken accessToken = new AccessToken(this.generateToken());
        this.MAP.put(accessToken, user.getUuid());

        return accessToken;
    }

    /**
     * @param token
     * @return UUID or Null
     */
    public String getUserID(String token) {
        
        Optional<AccessToken> result = this.MAP.keySet().stream().filter((a) -> a.getToken().equals(token)).findFirst();

        if (!result.isPresent()) return null;
        
        // Check expired
        AccessToken accessToken = result.get();
        if (accessToken.isExpired()) {
            this.MAP.remove(accessToken); // remove expired access token
            return null;
        }

        
        return this.MAP.get(accessToken);
    }
}
