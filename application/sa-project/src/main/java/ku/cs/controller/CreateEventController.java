package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import ku.cs.service.RootService;
import ku.cs.util.ComponentLoader;

import java.util.LinkedList;
import java.util.List;

public class CreateEventController {
    @FXML
    public VBox startDateTimeVBox;
    @FXML
    public VBox endDateTimeVBox;
    @FXML
    public VBox musicianRequirementVBox;
    @FXML
    public VBox stereoRequirementVBox;
    @FXML
    private VBox vBox1;

    private List<RequirementFormController> musicianControllerList;
    private List<RequirementFormController> stereoControllerList;
    private TextFormController eventNameController;
    private TextFormController descriptionController;
    private DateFormController startDateTimeFormController;
    private DateFormController endDateTimeFormController;

    @FXML
    private void initialize() {

        eventNameController = ComponentLoader.loadInto(vBox1, getClass().getResource("/ku/cs/views/components/textForm.fxml"));
        eventNameController.setTitleText("Event Name");
        eventNameController.getTextField().setPromptText("Enter event name here");

        descriptionController = ComponentLoader.loadInto(vBox1, getClass().getResource("/ku/cs/views/components/textAreaForm.fxml"));
        descriptionController.setTitleText("Event Description");
        descriptionController.getTextArea().setPromptText("Enter event description here");


        startDateTimeFormController = ComponentLoader.loadInto(startDateTimeVBox, getClass().getResource("/ku/cs/views/components/dateForm.fxml"));
        endDateTimeFormController = ComponentLoader.loadInto(endDateTimeVBox, getClass().getResource("/ku/cs/views/components/dateForm.fxml"));

        musicianControllerList = new LinkedList<>();
        stereoControllerList = new LinkedList<>();

        onMusicianRequirementAddButtonClick();
        onStereoRequirementAddButtonClick();

    }

    public void onBack() {
        RootService.getController().getNavigationController().open("events-page.fxml");
    }

    public void onMusicianRequirementAddButtonClick() {
        RequirementFormController controller = ComponentLoader.loadInto(musicianRequirementVBox,
                getClass().getResource("/ku/cs/views/components/requirementForm.fxml"));

        controller.getComboBox().getItems().add("Pianist");
        controller.getComboBox().getItems().add("Guitar Player");

        controller.addDeleteListener(() -> {
            controller.delete();
            musicianControllerList.remove(controller);
        });

        musicianControllerList.add(controller);
    }

    public void onStereoRequirementAddButtonClick() {
        RequirementFormController controller = ComponentLoader.loadInto(stereoRequirementVBox,
                getClass().getResource("/ku/cs/views/components/requirementForm.fxml"));

        controller.getComboBox().getItems().add("Piano");
        controller.getComboBox().getItems().add("Loud Speaker");
        controller.getComboBox().getItems().add("Subwoofer");

        controller.addDeleteListener(() -> {
            controller.delete();
            stereoControllerList.remove(controller);
        });

        stereoControllerList.add(controller);
    }
}
