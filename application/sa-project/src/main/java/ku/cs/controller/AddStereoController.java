package ku.cs.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import ku.cs.service.RootService;

public class AddStereoController {
    public TextField searchStereoField;
    @FXML
    public TableView<StereoData> MusicianListTable;
    @FXML
    public TableColumn<StereoData, String> typeStereoColumn;
    @FXML
    public TableColumn<StereoData, String> nameOwnerStereoColumn;
    @FXML
    public TableColumn<StereoData, String> emailOwnerStereoColumn;
    @FXML
    public TableColumn<StereoData, String> phoneNumberOwnerColumn;

    public void initialize() {
        typeStereoColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));
        nameOwnerStereoColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOwnerName()));
        emailOwnerStereoColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOwnerEmail()));
        phoneNumberOwnerColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOwnerPhoneNumber()));

        MusicianListTable.setItems(StereoData.getSampleStereoData());
    }


    public static class StereoData {
        private final String type;
        private final String ownerName;
        private final String ownerEmail;
        private final String ownerPhoneNumber;

        public StereoData(String type, String ownerName, String ownerEmail, String ownerPhoneNumber) {
            this.type = type;
            this.ownerName = ownerName;
            this.ownerEmail = ownerEmail;
            this.ownerPhoneNumber = ownerPhoneNumber;
        }

        public String getType() {
            return type;
        }

        public String getOwnerName() {
            return ownerName;
        }

        public String getOwnerEmail() {
            return ownerEmail;
        }

        public String getOwnerPhoneNumber() {
            return ownerPhoneNumber;
        }

        public static ObservableList<StereoData> getSampleStereoData() {
            return FXCollections.observableArrayList(
                    new StereoData("Speaker", "Alex Johnson", "alex.j@example.com", "0912345678"),
                    new StereoData("Mixer", "Linda White", "linda.w@example.com", "0923456789"),
                    new StereoData("Microphone", "James Lee", "james.l@example.com", "0934567890"),
                    new StereoData("Amplifier", "Emma Brown", "emma.b@example.com", "0945678901")
            );
        }
    }
    public void onSearchStereo() {
    }

    public void addStereoToRequirement() {
    }

    public void backToEventRequirement(ActionEvent actionEvent) {
        RootService.getController().open("eventRequirement.fxml");
    }
}
