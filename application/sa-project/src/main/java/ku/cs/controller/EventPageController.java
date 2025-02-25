package ku.cs.controller;

import java.util.List;
import java.util.stream.Collectors;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import ku.cs.model.Event;
import ku.cs.net.ClientGetEventList;
import ku.cs.service.RootService;
import ku.cs.util.ComponentLoader;

public class EventPageController {
    public VBox vBox;
    public TextField searchEventTextField;
    private List<Event> events;

    @FXML
    public void initialize() {
        RootService.getController().getNavigationController().setTitleText("EVENT");

        ClientGetEventList clientGetEventList = new ClientGetEventList();
        this.events = clientGetEventList.getEventList();
        this.showEvents(events);

        searchEventTextField.textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldString, String newString) {
                System.out.println(newString);
                onSearchButtonClick();
            }
            
        });
    }

    public void addItem(Event event) {
        EventItemController controller = ComponentLoader.loadInto(vBox,
                getClass().getResource("/ku/cs/views/event-item.fxml"));
        controller.setEvent(event);
    }

    public void onCreateEvent() {
        RootService.getController().getNavigationController().open("create-event.fxml");
    }

    private void showEvents(List<Event> events) {
        vBox.getChildren().clear();
        events.forEach(this::addItem);
    }

    public void onSearchButtonClick() {

        String in = searchEventTextField.getText();

        if (in.isEmpty()) {
            this.showEvents(events);
            return;
        }

        String[] searchStrings = in.split("\\s+");
        List<Event> filteredEvents = events.stream()
                .filter(event -> {
                    for (String searchString : searchStrings) {
                        if (event.getTitle().toLowerCase().contains(searchString.toLowerCase()) ||
                                event.getDescription().toLowerCase().contains(searchString.toLowerCase())) {
                            return true;
                        }
                    }
                    return false;
                })
                .collect(Collectors.toList());

        this.showEvents(filteredEvents);
    }
}
