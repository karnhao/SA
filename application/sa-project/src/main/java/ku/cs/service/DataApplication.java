package ku.cs.service;

import ku.cs.model.Setting;

public class DataApplication {
    private Setting setting;


    DataApplication() {
        setting = new Setting();
    }

    public Setting getSetting() {
        return setting;
    }

    public void saveSetting() {
        
    }

}
