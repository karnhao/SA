package ku.cs.service;

import java.sql.SQLException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import ku.cs.entity.MusicianRole;
import ku.cs.repository.MusicianRoleRepository;

public class MusicianRoleService {
    private MusicianRoleRepository repository;

    public MusicianRoleService(MusicianRoleRepository repository) {
        this.repository = repository;
    }

    public JSONObject getAllRolesJsonObject() throws SQLException {
        List<MusicianRole> roles = repository.getAllRole();

        JSONObject result = new JSONObject();
        JSONArray rolesJsonArray = new JSONArray();
        roles.stream().map((r) -> {
            JSONObject role = new JSONObject();
            role.put("id", r.getId());
            role.put("name", r.getName());
            return role;
        }).forEach(rolesJsonArray::put);

        result.put("roles", rolesJsonArray);

        return result;
    }
}
