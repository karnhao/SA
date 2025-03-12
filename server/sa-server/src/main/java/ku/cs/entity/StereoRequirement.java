package ku.cs.entity;

public class StereoRequirement {
    private int quantity;
    private String type_id;
    private String typeName;
    private String status;

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String musician_id) {
        this.type_id = musician_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
