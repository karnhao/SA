package ku.cs.service;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.naming.AuthenticationException;

import org.json.JSONArray;
import org.json.JSONObject;

import ku.cs.entity.Musician;
import ku.cs.entity.MusicianRole;
import ku.cs.entity.User;
import ku.cs.repository.UserRepository;

public class UserService {

    private UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public JSONObject getUser(String token, String uuid) throws AuthenticationException, SQLException {
        AuthenticationService authenticationService = AuthenticationService.get();
        String source_uuid = authenticationService.getUserID(token);

        if (uuid == null) throw new AuthenticationException("Unauthorized");
        User targetUser = repository.getUserByUUID(uuid);
        User sourceUser = repository.getUserByUUID(source_uuid);
        User resultUser;

        if (sourceUser.getUuid().equals(uuid) || sourceUser.getRole().equalsIgnoreCase("agent")) resultUser = targetUser;
        else {
            resultUser = new User();
            resultUser.setName(targetUser.getName());
            resultUser.setPhone_number(targetUser.getPhone_number());
            resultUser.setUuid(targetUser.getUuid());
        }

        JSONObject response = new JSONObject();
        response.put("username", resultUser.getUsername());
        response.put("email", resultUser.getEmail());
        response.put("name", resultUser.getName());
        response.put("phone_number", resultUser.getPhone_number());
        response.put("role", resultUser.getRole());
        response.put("uuid", resultUser.getUuid());

        return response;
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

    public JSONObject getAllUserJsonObjects(String token, String role, String available_role) throws SQLException, AuthenticationException {

        AuthenticationService authenticationService = AuthenticationService.get();
        String uuid = authenticationService.getUserID(token);

        if (uuid == null) throw new AuthenticationException("Access denied");

        User user = repository.getUserByUUID(uuid);
        if (!user.getRole().equalsIgnoreCase("agent")) throw new AuthenticationException("Access denied");

        List<Musician> musicians = null;
        List<User> users = null;
        if (role.equalsIgnoreCase("musician")) {
            if (available_role == null)
            musicians = repository.getAllMusicians();
            else {
                System.out.println("Query for Musician form available role : " + available_role);
                users = repository.getMusiciansByRoleId(available_role);
                
            }
        } else {
            users = repository.getAllUsers();
        }

        JSONObject jsonObject = new JSONObject();

        JSONArray jsonArray = new JSONArray();

        if (users != null) {
            users.stream().map((u) -> {
                JSONObject o = new JSONObject();
                o.put("username", u.getUsername());
                o.put("email", u.getEmail());
                o.put("name", u.getName());
                o.put("phone_number", u.getPhone_number());
                o.put("role", u.getRole());
                o.put("uuid", u.getUuid());
                if (u instanceof Musician) {
                    o.put("work_count", ((Musician) u).getWorkCount());
                }
    
                return o;
            }).forEach(jsonArray::put);
        } else if (musicians != null) {
            musicians.stream().map((u) -> {
                JSONObject o = new JSONObject();
                o.put("username", u.getUsername());
                o.put("email", u.getEmail());
                o.put("name", u.getName());
                o.put("phone_number", u.getPhone_number());
                o.put("role", u.getRole());
                o.put("uuid", u.getUuid());
                o.put("bank_name", u.getBankName());
                o.put("bank_number", u.getBankNumber());
                return o;
            }).forEach(jsonArray::put);
        }
        

        jsonObject.put("users", jsonArray);

        return jsonObject;
    }

    public String setAvailableRole(String accessToken, JSONArray rolesJSONArray) throws SQLException, AuthenticationException {
        AuthenticationService authenticationService = AuthenticationService.get();
        String uuid = authenticationService.getUserID(accessToken);

        if (uuid == null) throw new AuthenticationException("No Authentication");

        LinkedList<MusicianRole> roles = new LinkedList<MusicianRole>();

        for (int i = 0; i < rolesJSONArray.length(); i++) {
            JSONObject o = rolesJSONArray.getJSONObject(i);
            MusicianRole role = new MusicianRole();

            role.setId(o.getString("role_id"));
            roles.add(role);
        }

        repository.setAvailableRoles(uuid, roles);

        return "success";
    }

    public JSONObject getAvailableRoles(String accessToken) throws SQLException, AuthenticationException {
        JSONObject jsonObject = new JSONObject();
        JSONArray rolesJSONArray = new JSONArray();

        AuthenticationService authenticationService = AuthenticationService.get();
        String uuid = authenticationService.getUserID(accessToken);

        if (uuid == null) throw new AuthenticationException("Access denied");

        List<MusicianRole> roles = repository.getAvailableMusicianRoles(uuid);
        for (MusicianRole role : roles) {
            JSONObject o = new JSONObject();
            o.put("role_id", role.getId());
            o.put("name", role.getName());

            rolesJSONArray.put(o);
        }

        jsonObject.put("roles", rolesJSONArray);
        return jsonObject;
    }
}
