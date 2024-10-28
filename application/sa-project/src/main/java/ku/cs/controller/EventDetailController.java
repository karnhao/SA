package ku.cs.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import ku.cs.model.*;
import ku.cs.net.ClientGetEvent;
import ku.cs.net.ClientUserInfo;
import ku.cs.service.Navigation;
import ku.cs.service.RootService;
import ku.cs.util.ComponentLoader;

public class EventDetailController {
    @FXML
    public Label eventOwnerLabel;
    @FXML
    public Label eventDateLabel;
    @FXML
    public Label eventDetailLabel;
    public Button acceptButton;
    public Button cancelEventButton;
    public Button rejectButton;
    public VBox musicianRequirementVBox;
    public VBox stereoRequirementVBox;

    private EventDetail eventDetail;
    @FXML
    private void initialize() {
        ClientGetEvent clientGetEvent = new ClientGetEvent();
        ClientUserInfo clientUserInfo = new ClientUserInfo();
        String eid = (String) Navigation.getData();
        Platform.runLater(RootService::showLoadingIndicator);

        eventDetail = clientGetEvent.getEvent(eid);
        User owner = clientUserInfo.getUserInfo(eventDetail.getOwner().getUuid());

        updateEventDetails(
                owner.getName() + " " + owner.getPhone_number(),
                eventDetail.getStartDate().toString() + " - " + eventDetail.getEndDate().toString(),
                eventDetail.getDescription());

        for (MusicianRequirement musicianRequirement : eventDetail.getMusicianRequirements()) {
            EventRequirementController musicianRequirementController = ComponentLoader.loadInto(
                    this.musicianRequirementVBox,
                    getClass().getResource("/ku/cs/views/event-requirement.fxml")
            );

            musicianRequirementController.setTitleLabelText(musicianRequirement.getMusicianRole().getName());
            musicianRequirementController.setNumber(musicianRequirement.getMusicians().stream().filter(m->m.getStatus().equalsIgnoreCase("promise")).toList().size());
            musicianRequirementController.setMaxNumber(musicianRequirement.getQuantity());
            musicianRequirementController.setOnAddButtonRunnable(() -> Navigation.open("add-musician.fxml", new Object[]{eventDetail, musicianRequirement.getMusicianRole()}));
            for (Musician musician : musicianRequirement.getMusicians()) {
                musicianRequirementController.addItem(vBox -> this.addMusicianItem(vBox, musician));
            }
        }

        for (StereoRequirement stereoRequirement : eventDetail.getStereoRequirements()) {
            EventRequirementController stereoRequirementController = ComponentLoader.loadInto(
                    this.stereoRequirementVBox,
                    getClass().getResource("/ku/cs/views/event-requirement.fxml")
            );

            stereoRequirementController.setTitleLabelText(stereoRequirement.getType().getName());
            stereoRequirementController.setNumber(stereoRequirement.getStereos().stream().filter(s->s.getStatus().equalsIgnoreCase("promise")).toList().size());
            stereoRequirementController.setMaxNumber(stereoRequirement.getQuantity());
            stereoRequirementController.setOnAddButtonRunnable(() -> Navigation.open("add-stereo.fxml", new Object[]{eventDetail, stereoRequirement.getType()}));
            for (Stereo stereo : stereoRequirement.getStereos()) {
                stereoRequirementController.addItem(vBox -> addStereoItem(vBox, stereo));
            }
        }



        Platform.runLater(RootService::hideLoadingIndicator);

    }

    public void updateEventDetails(String owner, String date, String detail) {
        eventOwnerLabel.setText(owner);
        eventDateLabel.setText(date);
        eventDetailLabel.setText(detail);
    }

    public void OnBackToHome() {
        RootService.getController().getNavigationController().open("home-page.fxml");
    }

    private void addMusicianItem(VBox vBox, Musician musician) {
        ListItemController controller = ComponentLoader.loadInto(vBox, getClass().getResource("/ku/cs/views/components/list-item.fxml"));
        controller.addLabels(musician.getName(), musician.getEmail(), musician.getPhone_number(), musician.getStatus());
    }

    private void addStereoItem(VBox vBox, Stereo stereo) {
        ListItemController controller = ComponentLoader.loadInto(vBox, getClass().getResource("/ku/cs/views/components/list-item.fxml"));
        controller.addLabels(stereo.getName(), stereo.getOwner_name(), stereo.getOwner_phone_number(), stereo.getStatus());
    }
}
