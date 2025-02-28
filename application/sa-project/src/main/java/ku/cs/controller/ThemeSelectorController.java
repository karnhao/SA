package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import ku.cs.sa_project.MainApp;

public class ThemeSelectorController {
    private static int theme = 0; // 0 = light, 1 = dark

    @FXML
    private Button themeButton;

    @FXML
    public void initialize() {
        updateButtonText();
    }

    @FXML
    public void switchTheme() {
        theme = (theme == 0) ? 1 : 0;
        updateAppearance();
    }

    public static void updateAppearance() {
        if (theme == 0) {
            MainApp.setTheme("default.css");
        } else {
            MainApp.setTheme("test.css");
        }
    }

    private void updateButtonText() {
        themeButton.setText((theme == 0) ? "Switch to Dark Mode" : "Switch to Light Mode");
    }
}
