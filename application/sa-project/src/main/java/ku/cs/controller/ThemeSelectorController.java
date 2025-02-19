package ku.cs.controller;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.Region;

public class ThemeSelectorController extends Application {
    private static Color themeColor = Color.TEAL; // Default theme color
    public Region preview;
    public ColorPicker colorPicker;
    public Button applyButton;

    @Override
    public void start(Stage primaryStage) {
        ColorPicker colorPicker = new ColorPicker(themeColor);
        Button applyButton = new Button("Apply Theme");
        Region preview = new Region();
        preview.setStyle("-fx-background-color: " + toRgbString(themeColor) + "; width: 100px; height: 50px;");

        applyButton.setOnAction(e -> {
            themeColor = colorPicker.getValue();
            preview.setStyle("-fx-background-color: " + toRgbString(themeColor) + ";");
            System.out.println("Theme color changed to: " + themeColor);
        });

        VBox root = new VBox(10, colorPicker, applyButton, preview);
        Scene scene = new Scene(root, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Theme Selector");
        primaryStage.show();
    }

    public static Color getThemeColor() {
        return themeColor;
    }

    private String toRgbString(Color color) {
        return String.format("rgb(%d, %d, %d)", (int) (color.getRed() * 255), (int) (color.getGreen() * 255), (int) (color.getBlue() * 255));
    }
}

