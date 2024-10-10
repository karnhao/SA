package ku.cs.controller;

import javafx.scene.control.Label;
import ku.cs.service.RootService;

public class EventItemController {
    public Label titleLabel;
    public Label dateLabel;
    public Label descriptionLabel;
    public Label statusLabel;

    public void onClick() {
        RootService.getController().getNavigationController().open("event_detail.fxml");
    }
}
