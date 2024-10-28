package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import ku.cs.service.RootService;

public class LoadErrorController {

    @FXML
    private Label label;

    public void setText(String text) {
        this.label.setText(text);
    }

    public void onBackToLogin() {
        RootService.open("login.fxml");
    }
}
