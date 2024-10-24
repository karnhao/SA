package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ku.cs.util.FocusedPropertyUtil;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class DateFormController {

    @FXML
    private VBox vBox;
    @FXML
    private DatePicker datePicker;
    @FXML
    private HBox timeHBox;
    @FXML
    private Label hourLabel;
    @FXML
    private TextField hourTextField;
    @FXML
    private Label minuteLabel;
    @FXML
    private TextField minuteTextField;

    @FXML
    private void initialize() {
        FocusedPropertyUtil.setAppearNodeOnFieldFocused(hourTextField, hourLabel);
        FocusedPropertyUtil.setAppearNodeOnFieldFocused(minuteTextField, minuteLabel);
    }

    public LocalDateTime getDateTime() {
        return LocalDateTime.of(
                datePicker.getValue(),
                LocalTime.of(Integer.parseInt(hourTextField.getText()),
                        Integer.parseInt(minuteTextField.getText()))
        );
    }
}
