package ku.cs.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import ku.cs.model.EventDetail;
import ku.cs.model.User;
import ku.cs.net.ClientGetEvent;
import ku.cs.net.ClientUserInfo;
import ku.cs.service.Navigation;
import ku.cs.service.RootService;

public class EventDetailController {
    @FXML
    public Label eventOwnerLabel;
    @FXML
    public Label eventDateLabel;
    @FXML
    public Label locationLabel;
    @FXML
    public Label eventDetailLabel;
    @FXML
    public Label RequirementLabel;
    @FXML
    public ImageView OwnerImageView;
    public Button acceptButton;
    public Button cancelEventButton;
    public Button rejectButton;

    private EventDetail eventDetail;
    @FXML
    private void initialize() {
        ClientGetEvent clientGetEvent = new ClientGetEvent();
        ClientUserInfo clientUserInfo = new ClientUserInfo();
        String eid = (String) Navigation.getData();
        Platform.runLater(RootService::showLoadingIndicator);

        eventDetail = clientGetEvent.getEvent(eid);
        User owner = clientUserInfo.getUserInfo(eventDetail.getOwner().getUuid());

        updateEventDetails(owner.getName() + " " + owner.getPhone_number(), eventDetail.getStartDate().toString(), eventDetail.getDescription(), eventDetail.getDescription(), "Hello World");

        Platform.runLater(RootService::hideLoadingIndicator);

    }

    public void updateEventDetails(String owner, String date, String location, String detail, String requirement) {
        eventOwnerLabel.setText(owner);
        eventDateLabel.setText(date);
        locationLabel.setText(location);
        eventDetailLabel.setText(detail);
        RequirementLabel.setText(requirement);
    }

    public void OnBackToHome() {
        RootService.getController().getNavigationController().open("home-page.fxml");
    }


    public void OnToEventRequirement() {
        RootService.getController().getNavigationController().open("eventRequirement.fxml");
    }
}
