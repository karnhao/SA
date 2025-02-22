package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

public class ThemeSelectorController {
    private static Color themeColor = Color.TEAL; // Default theme color

    @FXML
    private StackPane rootPane;

    @FXML
    private HBox preview;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private Button applyButton;

    @FXML
    public void initialize() {
        colorPicker.setValue(themeColor);
        updatePreview();

        applyButton.setOnAction(e -> {
            themeColor = colorPicker.getValue();
            updatePreview();
            System.out.println("Theme color changed to: " + themeColor);
        });
    }

    private void updatePreview() {
        // กำหนดสีพื้นหลังให้ HBox โดยตรง
        preview.setStyle("-fx-background-color: " + toRgbString(themeColor) + "; -fx-min-width: 200px; -fx-min-height: 200px; -fx-border-color: black;");
    }
    public static Color getThemeColor() {
        return themeColor;
    }

    private String toRgbString(Color color) {
        return String.format("rgb(%d, %d, %d)", (int) (color.getRed() * 255), (int) (color.getGreen() * 255), (int) (color.getBlue() * 255));
    }
}
