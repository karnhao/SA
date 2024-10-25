package ku.cs.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import ku.cs.model.Event;
import ku.cs.net.ClientGetEventList;
import ku.cs.service.Navigation;
import ku.cs.service.RootService;
import ku.cs.util.ComponentLoader;

public class HomePageController {
    public VBox vBoxHomepage;
    public Label titleLabel;

    @FXML
    public void initialize() {
        titleLabel.setText(String.format("Welcome %s", RootService.getData().getUser().getName()));
        RootService.getController().getNavigationController().setTitleText("HOME");

        ClientGetEventList clientGetEventList = new ClientGetEventList();
        clientGetEventList.getEventList().forEach(this::addItem);

    }
    public void addItem(Event event){
        EventItemController controller = ComponentLoader.loadInto(vBoxHomepage, getClass().getResource("/ku/cs/views/event-item.fxml"));
        controller.setEvent(event);
    }

    public void onCreateEvent() {
        Navigation.open("createEvent.fxml");
    }
}
