package ku.cs.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
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

    public void updateEventDetails(String owner, String date, String location, String detail, String requirement) {
        eventOwnerLabel.setText(owner);
        eventDateLabel.setText(date);
        locationLabel.setText(location);
        eventDetailLabel.setText(detail);
        RequirementLabel.setText(requirement);
    }

    public void OnBackToHome() {
        RootService.getController().open("login.fxml");
    }
}
