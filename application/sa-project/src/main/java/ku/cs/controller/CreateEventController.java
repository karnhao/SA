package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import ku.cs.model.MusicianRole;
import ku.cs.model.StereoType;
import ku.cs.net.ClientGetRole;
import ku.cs.net.ClientGetStereoType;
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
    private List<MusicianRole> roles;
    private List<StereoType> types;

    @FXML
    private void initialize() {

        try {

            ClientGetRole clientRole = new ClientGetRole();
            roles = clientRole.getMusicianRoles();

            ClientGetStereoType clientStereoType = new ClientGetStereoType();
            types = clientStereoType.getStereoTypes();

        } catch (Exception ignored){}

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

        if (roles != null) {
            roles.forEach(r -> controller.getComboBox().getItems().add(r.getName()));
        }

        controller.addDeleteListener(() -> {
            controller.delete();
            musicianControllerList.remove(controller);
        });

        musicianControllerList.add(controller);
    }

    public void onStereoRequirementAddButtonClick() {
        RequirementFormController controller = ComponentLoader.loadInto(stereoRequirementVBox,
                getClass().getResource("/ku/cs/views/components/requirementForm.fxml"));

        if (types != null) {
            types.forEach(t -> controller.getComboBox().getItems().add(t.getName()));
        }

        controller.addDeleteListener(() -> {
            controller.delete();
            stereoControllerList.remove(controller);
        });

        stereoControllerList.add(controller);
    }

    public void onDoneButton() {
        System.out.println(eventNameController.getText());
        System.out.println(descriptionController.getText());
        System.out.println(startDateTimeFormController.getDateTime().toString());
        System.out.println(endDateTimeFormController.getDateTime().toString());

        musicianControllerList.forEach(t -> System.out.println(t.getComboBox().getValue() + " " + t.getQuantity()));
        stereoControllerList.forEach(t -> System.out.println(t.getComboBox().getValue() + " " + t.getQuantity()));
    }
}
