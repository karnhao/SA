package ku.cs.controller;

import javafx.scene.layout.VBox;
import ku.cs.util.ComponentLoader;

public class EventPageController {
    public VBox vBox;

    public void initialize() {
        addEventItem();
        addEventItem();
        addEventItem();
        addEventItem();
        addEventItem();
    }

    public void addEventItem(){
        EventItemController controller = ComponentLoader.loadInto(vBox, getClass().getResource("/ku/cs/views/event-item.fxml"));

    }
}
