package ku.cs.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import ku.cs.model.Event;
import ku.cs.model.EventDetail;
import ku.cs.model.Musician;
import ku.cs.model.MusicianRole;
import ku.cs.net.ClientGetUsers;
import ku.cs.net.ClientRequestMusician;
import ku.cs.service.Navigation;
import ku.cs.service.RootService;

import java.util.List;

public class AddMusicianController {
    @FXML
    public TextField searchMusicianField;
    @FXML
    public TableColumn<Musician, String> nameColumn;
    @FXML
    public TableColumn<Musician, String> emailColumn;
    @FXML
    public TableColumn<Musician, String> phoneNumberColumn;
    @FXML
    public Label titleLabel;
    @FXML
    public TableView<Musician> musicianListTable;
    @FXML
    public TableColumn<Musician, String> workAmountColumn;

    private Event event;
    private MusicianRole musicianRole;

    public void initialize() {
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        emailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        phoneNumberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPhone_number()));
        workAmountColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getWorkCount())));

        event = (EventDetail) ((Object[])Navigation.getData())[0];
        musicianRole = (MusicianRole) ((Object[])Navigation.getData())[1];

        ClientGetUsers clientGetUsers = new ClientGetUsers();
        List<Musician> musicians = clientGetUsers.getAllMusicianByAvailableRole(musicianRole.getId());
        musicianListTable.getItems().addAll(musicians);
    }

    public void onSearchMusician() {
    }

    public void addMusicianToRequirement() {
        ClientRequestMusician clientRequestMusician = new ClientRequestMusician();
        try {
            String res = clientRequestMusician.requestMusician(event.getEventID(), musicianRole.getId(), musicianListTable.getSelectionModel().getSelectedItem().getUuid());
            RootService.showBar(res);
            this.backToEventRequirement();
        } catch (Exception e) {
            RootService.showErrorBar(e.getClass() + " " + e.getMessage());
        }
    }

    public void backToEventRequirement() {
        Navigation.open("event-detail.fxml", event.getEventID());
    }
}
