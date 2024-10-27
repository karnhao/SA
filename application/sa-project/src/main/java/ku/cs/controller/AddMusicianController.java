package ku.cs.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import ku.cs.model.Musician;
import ku.cs.service.RootService;

public class AddMusicianController {
    @FXML
    public TextField searchMusicianField;
    @FXML
    public TableView<MusicianData> MusicianListTable;
    @FXML
    public TableColumn<MusicianData, String> roleCollumn;
    @FXML
    public TableColumn<MusicianData, String> nameColumn;
    @FXML
    public TableColumn<MusicianData, String> emailColumn;
    @FXML
    public TableColumn<MusicianData, String> phoneNumberColumn;

    public void initialize() {
        roleCollumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRole()));
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        emailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        phoneNumberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPhoneNumber()));

        MusicianListTable.setItems(MusicianData.getSampleMusicianData());
    }

    public static class MusicianData{
        private final String role;
        private final String name;
        private final String email;
        private final String phoneNumber;

        public MusicianData(String role, String name, String email, String phoneNumber) {
            this.role = role;
            this.name = name;
            this.email = email;
            this.phoneNumber = phoneNumber;
        }

        public String getRole() {
            return role;
        }
        public String getName() {
            return name;
        }
        public String getEmail() {
            return email;
        }
        public String getPhoneNumber() {
            return phoneNumber;
        }
        public static ObservableList<MusicianData> getSampleMusicianData() {
            return FXCollections.observableArrayList(
                    new MusicianData("Guitarist", "John Doe", "john.doe@example.com", "0812345678"),
                    new MusicianData("Drummer", "Jane Smith", "jane.smith@example.com", "0898765432"),
                    new MusicianData("Bassist", "Mike Brown", "mike.brown@example.com", "0811223344"),
                    new MusicianData("Keyboardist", "Sarah Lee", "sarah.lee@example.com", "0822334455"),
                    new MusicianData("Jazz", "Hwai gkun", "Hwai.gkun@example.com", "0626785564")
            );
        }
    }




    public void onSearchMusician() {
    }

    public void addMusicianToRequirement() {
    }

    public void backToEventRequirement(ActionEvent actionEvent) {
        RootService.getController().getNavigationController().open("eventRequirement.fxml");
    }
}
