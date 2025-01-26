package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import ku.cs.model.Event;
import ku.cs.net.ClientGetEventList;
import ku.cs.service.RootService;
import ku.cs.util.ComponentLoader;

public class EventPageController {
    public VBox vBox;

    @FXML
    public void initialize() {
        RootService.getController().getNavigationController().setTitleText("EVENT");

        ClientGetEventList clientGetEventList = new ClientGetEventList();
        clientGetEventList.getEventList().forEach(this::addItem);
    }

    public void addItem(Event event){
        EventItemController controller = ComponentLoader.loadInto(vBox, getClass().getResource("/ku/cs/views/event-item.fxml"));
        controller.setEvent(event);
    }

    public void onCreateEvent() {
        RootService.getController().getNavigationController().open("create-event.fxml");
    }
}
