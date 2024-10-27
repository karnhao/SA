package ku.cs.model;

public class StereoRequirement implements Requirement {
    private int quantity;
    private StereoType type;

    @Override
    public String getID() {
        return this.type.getId();
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public StereoType getType() {
        return type;
    }

    public void setType(StereoType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type.getName();
    }
}
