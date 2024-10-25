package ku.cs.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
    public Button acceptButton;
    public Button cancelEventButton;
    public Button rejectButton;

    public void updateEventDetails(String owner, String date, String location, String detail, String requirement) {
        eventOwnerLabel.setText(owner);
        eventDateLabel.setText(date);
        locationLabel.setText(location);
        eventDetailLabel.setText(detail);
        RequirementLabel.setText(requirement);
    }


    //private void configureButtonsVisibility() {
    //    switch (currentUserRole) {
    //        case "Owner":
                // Show only the cancel button
    //            cancelEventButton.setVisible(true);
    //            acceptButton.setVisible(false);
    //            rejectButton.setVisible(false);
    //            break;
    //        case "Musician":
                // Show accept and reject buttons
    //            cancelEventButton.setVisible(false);
    //            acceptButton.setVisible(true);
    //            rejectButton.setVisible(true);
    //            break;
    //        default:
                // Hide all buttons for normal users
    //            cancelEventButton.setVisible(false);
    //            acceptButton.setVisible(false);
    //            rejectButton.setVisible(false);
    //            break;
    //    }
    //}


    public void OnBackToHome() {
        RootService.getController().getNavigationController().open("home-page.fxml");
    }


    public void OnToEventRequirement() {
        RootService.getController().getNavigationController().open("eventRequirement.fxml");
    }
}
