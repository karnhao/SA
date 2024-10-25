package ku.cs.entity;

public class Musician extends User {
    private String bankName;
    private String bankNumber;

    public Musician(User user) {
        super(user.getName(), user.getUsername(), user.getUuid(), user.getPassword(), user.getImage_url(), user.getEmail(), user.getPhone_number(), user.getRole());
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }

    public String getBankNumber() {
        return bankNumber;
    }
}
