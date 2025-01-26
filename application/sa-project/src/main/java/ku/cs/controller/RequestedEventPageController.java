package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import ku.cs.model.Event;
import ku.cs.net.ClientGetEventList;
import ku.cs.util.ComponentLoader;

public class RequestedEventPageController {
    @FXML
    public VBox musicianEventsVBox;
    @FXML
    public VBox stereoEventsVBox;

    @FXML
    private void initialize() {
        ClientGetEventList clientGetEventList = new ClientGetEventList();

        clientGetEventList.getRequestedEventMusician().forEach((t) -> this.addItem(t, musicianEventsVBox));
        clientGetEventList.getRequestedEventStereo().forEach((t) -> this.addItem(t, stereoEventsVBox));
    }

    public void addItem(Event event, Pane p){
        EventItemController controller = ComponentLoader.loadInto(p, getClass().getResource("/ku/cs/views/event-item.fxml"));
        controller.setEvent(event);
    }
}
