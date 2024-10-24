package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ku.cs.util.FocusedPropertyUtil;

public class DateFormController {
    public VBox vBox;
    public DatePicker datePicker;
    public HBox timeHBox;
    public Label hourLabel;
    public TextField hourTextField;
    public Label minuteLabel;
    public TextField minuteTextField;

    @FXML
    private void initialize() {
        FocusedPropertyUtil.setAppearNodeOnFieldFocused(hourTextField, hourLabel);
        FocusedPropertyUtil.setAppearNodeOnFieldFocused(minuteTextField, minuteLabel);
    }
}
