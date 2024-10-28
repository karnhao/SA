package ku.cs.model;

import java.util.List;

public class StereoRequirement implements Requirement {
    private int quantity;
    private StereoType type;
    private List<Stereo> stereos;

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

    public List<Stereo> getStereos() {
        return stereos;
    }

    public void setStereos(List<Stereo> stereos) {
        this.stereos = stereos;
    }
}
