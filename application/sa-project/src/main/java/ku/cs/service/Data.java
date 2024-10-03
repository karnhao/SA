package ku.cs.service;

import ku.cs.model.Setting;

public class Data {

    private static Data data;
    private DataApplication application;

    public static Data getInstance() {
        if (data == null) data = new Data();
        return data;
    }

    private Data() {
        this.application = new DataApplication();
    }

    public Setting getSetting() {
        return application.getSetting();
    }

    public void saveSetting() {
        application.saveSetting();
    }
}
