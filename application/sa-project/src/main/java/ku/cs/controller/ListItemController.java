package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class ListItemController {

    @FXML
    private HBox hBox;

    @FXML
    private void initialize() {

    }

    public void addLabels(String... text) {
        for (String s : text) {
            addLabel(s);
        }
    }

    public Label addLabel(String text) {
        Label label = new Label();
        label.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");
        label.setText(text);
        label.setMaxHeight(Double.MAX_VALUE);
        label.setMaxWidth(Double.MAX_VALUE);
        label.setPrefHeight(0);
        label.setPrefWidth(0);

        HBox.setHgrow(label, Priority.ALWAYS);

        hBox.getChildren().add(label);
        return label;
    }
}
