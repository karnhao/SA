package ku.cs.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import ku.cs.net.ClientEvent;

public class EventRequirementController {
    public Label titleLabel;
    public Label numberLabel;
    public Label maxNumberLabel;
    public VBox vBox;
    public Button addButton;
    public ChoiceBox<String> statusChoiceBox;
    private Runnable onAddButtonRunnable;

    @FXML
    public void initialize() {
        statusChoiceBox.getItems().add("DONE");
        statusChoiceBox.getItems().add("NOT DONE");
        statusChoiceBox.setValue("NO STATUS");
    }

    public void onAddButton() {
        this.onAddButtonRunnable.run();
    }

    public void setOnAddButtonRunnable(Runnable r) {
        this.onAddButtonRunnable = r;
    }

    public void setNumber(int n) {
        this.numberLabel.setText(String.valueOf(n));
    }

    public void setMaxNumber(int n) {
        this.maxNumberLabel.setText(String.valueOf(n));
    }

    public void setTitleLabelText(String text) {
        this.titleLabel.setText(text);
    }

    public void addItem(RequirementItem ri) {
        ri.add(vBox);
    }

    public interface RequirementItem {
        void add(VBox vBox);
    }

    public void setStatus(String status) {
        this.statusChoiceBox.setValue(status);
    }

    public Button getAddButton() {
        return addButton;
    }
}
