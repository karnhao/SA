package ku.cs.model;

public class User {
    private String name;
    private String username;
    private String uuid;
    private String email;
    private String phone_number;
    private String role;

    public User() {

    }

    public User(String name, String username, String uuid, String password, String image_url, String email,
                String phone_number, String role) {
        this.name = name;
        this.username = username;
        this.uuid = uuid;
        this.email = email;
        this.phone_number = phone_number;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String account_name) {
        this.username = account_name;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User [" + this.uuid + ", " + this.username + ", " + this.name + "]";
    }
}
