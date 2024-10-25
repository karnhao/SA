package ku.cs.service;

import ku.cs.model.User;

public class Data {

    private static Data data;
    private User user;

    public static Data getInstance() {
        if (data == null) data = new Data();
        return data;
    }

    private Data() {
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
