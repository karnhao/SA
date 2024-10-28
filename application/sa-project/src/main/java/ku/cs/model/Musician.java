package ku.cs.model;

public class Musician extends User {
    private String bankName;
    private String bankNumber;
    private String status;
    private int workCount;
    private String conflict;

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getWorkCount() {
        return workCount;
    }

    public void setWorkCount(int workCount) {
        this.workCount = workCount;
    }

    public String getConflict() {
        return conflict;
    }

    public void setConflict(String conflict) {
        this.conflict = conflict;
    }
}
