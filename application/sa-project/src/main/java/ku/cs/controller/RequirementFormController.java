package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.VBox;

import java.util.LinkedList;
import java.util.List;

public class RequirementFormController {
    @FXML
    private ComboBox<String> comboBox;
    @FXML
    private Spinner<Integer> spinner;
    @FXML
    private VBox vBox;

    private List<Runnable> onDeleteListener;

    @FXML
    private void initialize() {
        this.onDeleteListener = new LinkedList<>();
        spinner.setPromptText("Quantity");
        spinner.setEditable(true);
        spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 99, 1));
    }

    public void addDeleteListener(Runnable r) {
        onDeleteListener.add(r);
    }

    public void onAction() {
    }

    public void onDeleteButtonClick() {
        this.onDeleteListener.forEach(Runnable::run);
    }

    public ComboBox<String> getComboBox() {
        return this.comboBox;
    }

    public int getQuantity() {
        return this.spinner.getValue();
    }

    public void delete() {
        this.vBox.getChildren().clear();
    }
}
