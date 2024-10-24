package ku.cs.entity;

public class MusicianRequirement {
    private int quantity;
    private String musician_id;
    private String roleName;

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
}
