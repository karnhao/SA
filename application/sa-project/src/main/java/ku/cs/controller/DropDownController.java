package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.LinkedList;
import java.util.List;

public class DropDownController<T> {

    private List<Runnable> onActionListeners;
    public VBox vBox;
    public Label titleLabel;
    public ComboBox<T> comboBox;

    @FXML
    public void initialize() {
        onActionListeners = new LinkedList<>();
    }

    public void addListener(Runnable r) {
        this.onActionListeners.add(r);
    }

    public void setTitleText(String text) {
        this.titleLabel.setText(text);
    }

    public ComboBox<T> getComboBox() {
        return comboBox;
    }

    public void onAction() {
        this.onActionListeners.forEach(Runnable::run);
    }
}
