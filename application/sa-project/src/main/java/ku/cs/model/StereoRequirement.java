package ku.cs.model;

public class StereoRequirement {
    private int quantity;
    private StereoType type;

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
