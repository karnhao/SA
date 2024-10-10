package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class NavigationButtonController {

    @FXML
    private VBox box;
    @FXML
    private Label label;
    private Runnable onClick;

    public void setOnClickRunnable(Runnable r) {
        this.onClick = r;
    }

    public void onClick() {
        this.onClick.run();
    }
    public void selection(boolean b) {
        box.setStyle(b ? "-fx-background-color: -light-green-color" : "");
    }

    public void setLabelText(String text) {
        this.label.setText(text);
    }

}
