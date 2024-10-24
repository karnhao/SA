package ku.cs.service;

import java.sql.SQLException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import ku.cs.entity.StereoType;
import ku.cs.repository.StereoTypeRepository;

public class StereoTypeService {
    private StereoTypeRepository repository;

    public StereoTypeService(StereoTypeRepository repository) {
        this.repository = repository;
    }

    public JSONObject getAllStereoType() throws SQLException {
        List<StereoType> typeList = repository.getAllType();

        JSONObject result = new JSONObject();
        JSONArray typeJSONArray = new JSONArray();

        typeList.stream().map((r) -> {
            JSONObject type = new JSONObject();
            type.put("id", r.getId());
            type.put("name", r.getName());
            return type;
        }).forEach(typeJSONArray::put);

        result.put("types", typeJSONArray);

        return result;
    }
}
