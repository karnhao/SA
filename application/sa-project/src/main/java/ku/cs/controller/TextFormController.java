package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class TextFormController {

    public Label title;
    public TextField textField;
    public Pane pane;

    @FXML
    public void initialize() {

    }

    public void setTitleText(String text) {
        this.title.setText(text);
    }

    public TextField getTextField() {
        return this.textField;
    }

    public String getText() {
        return this.getTextField().getText();
    }
}
