package ku.cs.service;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import javax.security.sasl.AuthenticationException;

import org.json.JSONArray;
import org.json.JSONObject;

import ku.cs.entity.Stereo;
import ku.cs.entity.User;
import ku.cs.repository.StereoRepository;
import ku.cs.repository.UserRepository;

public class StereoService {

    private StereoRepository repository;
    private UserRepository userRepository;

    public StereoService(StereoRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public JSONObject createStereo(String accessToken, JSONObject myJSONObject) throws SQLException {
        AuthenticationService authenticationService = AuthenticationService.get();
        String uuid = authenticationService.getUserID(accessToken);

        String sid = UUID.randomUUID().toString();

        Stereo stereo = new Stereo();
        stereo.setId(sid);
        stereo.setName(myJSONObject.getString("name"));
        stereo.setType_id(myJSONObject.getString("type_id"));
        stereo.setOwner_id(uuid);

        repository.createStereo(stereo);
        return new JSONObject().put("stereo_id", sid);
    }

    public JSONObject getStereoList(String accessToken) throws SQLException {
        AuthenticationService authenticationService = AuthenticationService.get();
        String uuid = authenticationService.getUserID(accessToken);

        User user = userRepository.getUserByUUID(uuid);

        List<Stereo> list = user.getRole().equalsIgnoreCase("agent") ? repository.getStereos() : repository.getStereosFromUUID(uuid);

        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();

        if (list != null) {
            list.stream().map(s -> {
                JSONObject o = new JSONObject();
                o.put("id", s.getId());
                o.put("name", s.getName());
                o.put("owner_id", s.getOwner_id());
                o.put("owner_name", s.getOwner_name());
                o.put("type_id", s.getType_id());
                o.put("type_name", s.getType_name());

                return o;
            }).forEach(array::put);
        }

        json.put("stereos", array);

        return json;
    }

    public JSONObject getStereoListByTypeID(String accessToken, String type_id) throws SQLException, AuthenticationException {
        AuthenticationService authenticationService = AuthenticationService.get();
        String uuid = authenticationService.getUserID(accessToken);

        User user = userRepository.getUserByUUID(uuid);
        if (!user.getRole().equalsIgnoreCase("agent")) throw new AuthenticationException("Access Denied");

        List<Stereo> list = repository.getStereosByTypeId(type_id);

        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();

        if (list != null) {
            list.stream().map(s -> {
                JSONObject o = new JSONObject();
                o.put("id", s.getId());
                o.put("name", s.getName());
                o.put("owner_id", s.getOwner_id());
                o.put("owner_name", s.getOwner_name());
                o.put("type_id", s.getType_id());
                o.put("type_name", s.getType_name());
                o.put("owner_email", s.getOwner_email());
                o.put("owner_phone_number", s.getOwner_phone_number());
                return o;
            }).forEach(array::put);
        }

        json.put("stereos", array);

        return json;
    }

    public JSONObject getStereo(String accessToken, String stid) throws SQLException, AuthenticationException {
        AuthenticationService authenticationService = AuthenticationService.get();
        String uuid = authenticationService.getUserID(accessToken);
        User user = userRepository.getUserByUUID(uuid);

        Stereo s = repository.getStereosFromSTID(stid);
        if (!s.getOwner_id().equals(uuid) && !user.getRole().equalsIgnoreCase("agent"))
            throw new AuthenticationException("Access Denied");

        JSONObject json = new JSONObject();

        json.put("id", s.getId());
        json.put("name", s.getName());
        json.put("owner_id", s.getOwner_id());
        json.put("owner_name", s.getOwner_name());
        json.put("type_id", s.getType_id());
        json.put("type_name", s.getType_name());

        return json;
    }
}
