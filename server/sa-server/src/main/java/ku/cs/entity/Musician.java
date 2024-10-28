package ku.cs.entity;

public class Musician extends User {
    private String bankName;
    private String bankNumber;
    private String status;
    private String musicianRoleID;
    private String musicianRoleName;

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

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setMusicianRoleID(String musicianRoleID) {
        this.musicianRoleID = musicianRoleID;
    }

    public String getMusicianRoleID() {
        return musicianRoleID;
    }

    public void setMusicianRoleName(String musicianRoleName) {
        this.musicianRoleName = musicianRoleName;
    }

    public String getMusicianRoleName() {
        return musicianRoleName;
    }
}
