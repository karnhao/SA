package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import ku.cs.util.ComponentLoader;

public class EventPageController {
    public VBox vBox;

    @FXML
    public void initialize() {
        addEventItem();
        addEventItem();
        addEventItem();
    }

    public void addEventItem(){
        EventItemController controller = ComponentLoader.loadInto(vBox, getClass().getResource("/ku/cs/views/event-item.fxml"));

    }
}
