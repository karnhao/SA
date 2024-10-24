package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class TextFormController {

    public Label title;
    public TextField textField;
    public Pane pane;
    public TextArea textArea;
    @FXML
    private VBox vBox;

    @FXML
    public void initialize() {

    }

    public void setTitleText(String text) {
        this.title.setText(text);
    }

    public TextField getTextField() {
        return this.textField;
    }

    public TextArea getTextArea() {
        return this.textArea;
    }

    public String getText() {
        return textField != null ? textField.getText() : textArea.getText();
    }

    public void setVisible(boolean v) {
        this.vBox.setVisible(v);
    }
}
