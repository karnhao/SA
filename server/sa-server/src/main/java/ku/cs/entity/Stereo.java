package ku.cs.entity;

public class Stereo {
    private String id;
    private String name;
    private String owner_id;
    private String type_id;
    private String owner_name;
    private String type_name;
    private String status;
    private String owner_email;
    private String owner_phone_number;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOwner_id(String owner_id) {
        this.owner_id = owner_id;
    }

    public String getOwner_id() {
        return owner_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getType_id() {
        return type_id;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getOwner_email() {
        return owner_email;
    }

    public void setOwner_email(String owner_email) {
        this.owner_email = owner_email;
    }

    public String getOwner_phone_number() {
        return owner_phone_number;
    }

    public void setOwner_phone_number(String owner_phone_number) {
        this.owner_phone_number = owner_phone_number;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}
