package ku.cs.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ku.cs.model.Event;
import ku.cs.model.MusicianRole;
import ku.cs.net.ClientGetEventList;
import ku.cs.net.ClientGetRole;
import ku.cs.service.RootService;
import ku.cs.util.ComponentLoader;

public class EventPageController {
    public VBox vBox;
    public TextField searchEventTextField;
    public HBox normalSearchBox;
    public Button advanceSearchButton;
    public VBox advanceSearchBox;
    public HBox normalSearchBox1;
    public TextField searchEventTextField1;
    public VBox musicianRoleCheckBoxBox;
    private List<Event> events;
    private boolean isAdvanceSearchEnable;
    private List<CheckBox> musicianRoleCheckBoxes;

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

        this.musicianRoleCheckBoxes = new LinkedList<>();
        List<MusicianRole> roles = new ClientGetRole().getMusicianRoles();
        roles.forEach(r -> {
            CheckBox c = new CheckBox();
            c.setText(r.getName());
            c.setSelected(false);
            this.musicianRoleCheckBoxBox.getChildren().add(c);
            this.musicianRoleCheckBoxes.add(c);
        });

        isAdvanceSearchEnable = false;
        updateAdvanceSearch();

    }

    private void updateAdvanceSearch() {

        advanceSearchButton.setText(
                (isAdvanceSearchEnable ? "Disable" : "Enable" ) + "Advance Search"
        );
        advanceSearchBox.setVisible(isAdvanceSearchEnable);
        normalSearchBox.setVisible(!isAdvanceSearchEnable);

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
        if (isAdvanceSearchEnable) advanceSearch();
        else standardSearch();
    }

    private void standardSearch() {
        String in = searchEventTextField.getText();

        if (in.isEmpty()) {
            this.showEvents(events);
            return;
        }

        filterEvents(in);
    }

    private void advanceSearch() {
        String in = searchEventTextField.getText();
        ClientGetEventList clientGetEventList = new ClientGetEventList();
        this.events = clientGetEventList.getEventListWithOptions(
                musicianRoleCheckBoxes.stream()
                        .filter(CheckBox::isSelected)
                        .map(Labeled::getText)
                        .toArray(String[]::new));
        filterEvents(in);
    }

    private void filterEvents(String in) {
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

    public void onAdvanceSearchClick() {
        this.isAdvanceSearchEnable = !this.isAdvanceSearchEnable;
        updateAdvanceSearch();
    }
}
