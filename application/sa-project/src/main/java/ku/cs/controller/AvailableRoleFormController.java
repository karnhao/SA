package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;

import java.util.LinkedList;
import java.util.List;

public class AvailableRoleFormController<T> {
    @FXML
    private ComboBox<T> comboBox;
    @FXML
    private VBox vBox;
    private List<Runnable> onDeleteListener;

    @FXML
    private void initialize() {
        this.onDeleteListener = new LinkedList<>();
    }

    public void addDeleteListener(Runnable r) {
        onDeleteListener.add(r);
    }

    public void onAction() {
    }

    public void onDeleteButtonClick() {
        this.onDeleteListener.forEach(Runnable::run);
    }

    public ComboBox<T> getComboBox() {
        return this.comboBox;
    }

    public void delete() {
        this.vBox.getChildren().clear();
    }
}
