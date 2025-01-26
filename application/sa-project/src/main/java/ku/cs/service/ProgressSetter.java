package ku.cs.service;

public class ProgressSetter {
    private final double allocatePercentage;
    private double value;

    public ProgressSetter(double allocatePercentage) {
        this.allocatePercentage = allocatePercentage;
        this.value = 0;
    }

    public void setPercentage(double value) {
        this.value = value;
    }

    public double getPercentage() {
        return value * (allocatePercentage / 100);
    }

    public void close() {
        this.value = 1;
    }
}
