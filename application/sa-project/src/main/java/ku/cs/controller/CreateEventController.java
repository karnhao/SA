package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import ku.cs.model.MusicianRequirement;
import ku.cs.model.MusicianRole;
import ku.cs.model.StereoRequirement;
import ku.cs.model.StereoType;
import ku.cs.net.ClientCreateEvent;
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

    private List<RequirementFormController<MusicianRole>> musicianControllerList;
    private List<RequirementFormController<StereoType>> stereoControllerList;
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

        descriptionController = ComponentLoader.loadInto(vBox1, getClass().getResource("/ku/cs/views/components/text-area-form.fxml"));
        descriptionController.setTitleText("Event Description");
        descriptionController.getTextArea().setPromptText("Enter event description here");


        startDateTimeFormController = ComponentLoader.loadInto(startDateTimeVBox, getClass().getResource("/ku/cs/views/components/date-form.fxml"));
        endDateTimeFormController = ComponentLoader.loadInto(endDateTimeVBox, getClass().getResource("/ku/cs/views/components/date-form.fxml"));

        musicianControllerList = new LinkedList<>();
        stereoControllerList = new LinkedList<>();

        onMusicianRequirementAddButtonClick();
        onStereoRequirementAddButtonClick();

    }

    public void onBack() {
        RootService.getController().getNavigationController().open("events-page.fxml");
    }

    public void onMusicianRequirementAddButtonClick() {
        RequirementFormController<MusicianRole> controller = ComponentLoader.loadInto(musicianRequirementVBox,
                getClass().getResource("/ku/cs/views/components/requirement-form.fxml"));

        if (roles != null) {
            roles.forEach(r -> controller.getComboBox().getItems().add(r));
        }

        controller.addDeleteListener(() -> {
            controller.delete();
            musicianControllerList.remove(controller);
        });

        musicianControllerList.add(controller);
    }

    public void onStereoRequirementAddButtonClick() {
        RequirementFormController<StereoType> controller = ComponentLoader.loadInto(stereoRequirementVBox,
                getClass().getResource("/ku/cs/views/components/requirement-form.fxml"));

        if (types != null) {
            types.forEach(t -> controller.getComboBox().getItems().add(t));
        }

        controller.addDeleteListener(() -> {
            controller.delete();
            stereoControllerList.remove(controller);
        });

        stereoControllerList.add(controller);
    }

    public void onDoneButton() {

        ClientCreateEvent clientCreateEvent = new ClientCreateEvent();

        List<MusicianRequirement> musicianRequirements = musicianControllerList.stream().map(c->{
            MusicianRequirement m = new MusicianRequirement();
            m.setQuantity(c.getQuantity());
            m.setMusicianRole(c.getComboBox().getValue());
            return m;
        }).toList();

        List<StereoRequirement> stereoRequirements = stereoControllerList.stream().map(s->{
            StereoRequirement o = new StereoRequirement();
            o.setQuantity(s.getQuantity());
            o.setType(s.getComboBox().getValue());
            return o;
        }).toList();

        try {
            String res = clientCreateEvent.createEvent(
                    eventNameController.getText(),
                    startDateTimeFormController.getDateTime(),
                    endDateTimeFormController.getDateTime(),
                    descriptionController.getText(),
                    musicianRequirements,
                    stereoRequirements
            );
            RootService.showBar(res);
            RootService.getController().getNavigationController().open("events-page.fxml");
        } catch (Exception e) {
            e.printStackTrace();
            RootService.showErrorBar(e.getMessage());
        }
    }
}
