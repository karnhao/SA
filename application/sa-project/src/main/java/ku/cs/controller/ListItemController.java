package ku.cs.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;

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

    public Button addButton(String text, EventHandler<ActionEvent> r) {
        Button button = new Button();
        button.getStyleClass().add("button-green");
        button.setFont(new Font("System", 16));
        button.setMaxHeight(Double.MAX_VALUE);
        button.setText(text);
        button.setOnAction(r);

        hBox.getChildren().add(button);

        return button;
    }

    public void onClick() {
        this.onClickRunnableList.forEach(Runnable::run);
    }
}
