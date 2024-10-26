package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.util.LinkedList;
import java.util.List;

public class ListItemController {

    @FXML
    private HBox hBox;
    private List<Runnable> onClickRunnableList;

    @FXML
    private void initialize() {
        onClickRunnableList = new LinkedList<>();
    }

    public void addLabels(String... text) {
        for (String s : text) {
            addLabel(s);
        }
    }

    public void addClickRunnable(Runnable r) {
        this.onClickRunnableList.add(r);
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

    public void onClick() {
        this.onClickRunnableList.forEach(Runnable::run);
    }
}
