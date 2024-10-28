package ku.cs.controller;

import javafx.scene.control.Label;
import ku.cs.model.Event;
import ku.cs.service.Navigation;

import java.time.LocalDateTime;

public class EventItemController {
    public Label titleLabel;
    public Label dateLabel;
    public Label descriptionLabel;
    public Label statusLabel;
    private Event event;

    public void onClick() {
        Navigation.open("event-detail.fxml", event.getEventID());
    }

    public void setTitleText(String text) {
        this.titleLabel.setText(text);
    }

    public void setDate(LocalDateTime start, LocalDateTime end) {
        this.dateLabel.setText(start.toString() + " TO " + end.toString());
    }

    public void setStatusText(String text) {
        this.statusLabel.setText(text);
    }

    public void setDescriptionText(String text) {
        this.descriptionLabel.setText(text);
    }

    public void setEvent(Event event) {
        this.setTitleText(event.getTitle());
        this.setDate(event.getStartDate(), event.getEndDate());
        this.setStatusText(event.getStatus());
        this.setDescriptionText(event.getDescription());

        this.event = event;
    }
}
