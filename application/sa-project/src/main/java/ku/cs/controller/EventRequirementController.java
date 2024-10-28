package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class EventRequirementController {
    public Label titleLabel;
    public Label numberLabel;
    public Label maxNumberLabel;
    public VBox vBox;
    private Runnable onAddButtonRunnable;

    @FXML
    public void initialize() {
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
}
