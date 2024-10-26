package ku.cs.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import ku.cs.service.RootService;
import ku.cs.util.ComponentLoader;

public class EventRequirementController {
    public VBox vBoxMusician;
    public VBox vBoxStereo;
    public Label numberMusicianLabel;
    public Label numberStereoLabel;

    @FXML
    public void initialize() {
        addEventRequirementMusician();
        addEventRequirementMusician();
        addEventRequirementMusician();
        addEventRequirementMusician();
        addEventRequirementMusician();
        addEventRequirementMusician();
        addEventRequirementStereo();
        addEventRequirementStereo();
        addEventRequirementStereo();
        addEventRequirementStereo();
        addEventRequirementStereo();
    }


    public void addEventRequirementMusician(){
        EventRequirementMusicianItemController controller = ComponentLoader.loadInto(vBoxMusician, getClass().getResource("/ku/cs/views/eventRequirementMusician_item.fxml"));

    }

    public void addEventRequirementStereo(){
        EventRequirementStereoItemController controller = ComponentLoader.loadInto(vBoxStereo, getClass().getResource("/ku/cs/views/eventRequirementStereo_item.fxml"));
    }

    public void onBackToEventDetail() {
        RootService.getController().open("event_detail.fxml");
    }

    public void OntoAddMusician() {
        RootService.getController().open("addMusician.fxml");
    }

    public void OntoAddStereo() {
        RootService.getController().open("addStereo.fxml");
    }
}
