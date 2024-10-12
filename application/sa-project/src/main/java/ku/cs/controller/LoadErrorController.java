package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LoadErrorController {

    @FXML
    private Label label;

    public void setText(String text) {
        this.label.setText(text);
    }
}
