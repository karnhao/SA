package ku.cs.entity;

public class MusicianRequirement {
    private int quantity;
    private String musician_id;
    private String roleName;
    private String status;

    public String getMusician_id() {
        return musician_id;
    }

    public void setMusician_id(String musician_id) {
        this.musician_id = musician_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
}
