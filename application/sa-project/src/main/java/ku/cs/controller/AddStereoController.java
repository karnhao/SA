package ku.cs.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import ku.cs.model.*;
import ku.cs.net.ClientGetStereoList;
import ku.cs.net.ClientRequestStereo;
import ku.cs.service.Navigation;
import ku.cs.service.RootService;

import java.util.List;

public class AddStereoController {
    public TextField searchStereoField;
    @FXML
    public TableView<Stereo> stereoListTable;
    @FXML
    public TableColumn<Stereo, String> typeStereoColumn;
    @FXML
    public TableColumn<Stereo, String> nameOwnerStereoColumn;
    @FXML
    public TableColumn<Stereo, String> emailOwnerStereoColumn;
    @FXML
    public TableColumn<Stereo, String> phoneNumberOwnerColumn;
    public Label titleLabel;

    private Event event;

    public void initialize() {
        typeStereoColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType_name()));
        nameOwnerStereoColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOwner_name()));
        emailOwnerStereoColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOwner_email()));
        phoneNumberOwnerColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOwner_phone_number()));

        event = (EventDetail) ((Object[]) Navigation.getData())[0];
        StereoType stereoType = (StereoType) ((Object[]) Navigation.getData())[1];

        ClientGetStereoList clientGetStereoList = new ClientGetStereoList();
        List<Stereo> stereos = clientGetStereoList.getStereoListByType(stereoType.getId());

        stereoListTable.getItems().addAll(stereos);
    }

    public void onSearchStereo() {
    }

    public void addStereoToRequirement() {
        ClientRequestStereo clientRequestStereo = new ClientRequestStereo();
        try {
            String res = clientRequestStereo.requestStereo(event.getEventID(), stereoListTable.getSelectionModel().getSelectedItem().getId());
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
