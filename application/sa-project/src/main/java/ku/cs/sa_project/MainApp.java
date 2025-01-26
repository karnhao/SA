package ku.cs.sa_project;

import javafx.application.Application;
import javafx.stage.Stage;
import ku.cs.service.FXRouter;

import java.io.IOException;
import java.net.URL;


public class MainApp extends Application {
    private static Stage stage;
    private static String currentStyle;
    final private static String THEME_PATH = "/ku/cs/themes/";
    final private static double MIN_WIGHT = 1300;
    final private static double MIN_HEIGHT = 800;

    @Override
    public void start(Stage stage) throws IOException {
        MainApp.stage = stage;
        stage.setResizable(true);
        stage.setMinWidth(MIN_WIGHT);
        stage.setMinHeight(MIN_HEIGHT);

        configRoute();

        FXRouter.bind(this, stage);
        FXRouter.goTo("root"); //root
        setTheme("default.css");
    }

    private static void configRoute() {
        String resourcesPath = "ku/cs/views/";
        FXRouter.when("root", resourcesPath + "root.fxml", "Good Application 100% No Scam 😋😋😋", MIN_WIGHT, 760);
    }

    /**
     * ใส่ theme ให้กับหน้าโปแกรม
     * @param styleFileName ไฟล์ theme ที่มีนามสกุลเป็น .css เช่น default.css
     */
    public static void setTheme(String styleFileName) {
        URL stylesheetURL = MainApp.class.getResource(THEME_PATH + styleFileName);
        String styleSheet = "";

        if (stylesheetURL != null) styleSheet = stylesheetURL.toExternalForm(); // theme
        if (currentStyle != null) MainApp.stage.getScene().getStylesheets().remove(currentStyle);
        MainApp.stage.getScene().getStylesheets().add(styleSheet);
        MainApp.currentStyle = styleSheet;
    }

    /**
     * เพิ่ม theme ให้กับหน้าโปรแกรม
     * @param styleFileName ไฟล์ theme ที่มีนามสกุลเป็น .css เช่น default.css
     */
    public static void addAddonStyle(String styleFileName) {
        URL stylesheetURL = MainApp.class.getResource(THEME_PATH + styleFileName);
        String styleSheet = "";

        if (stylesheetURL != null) styleSheet = stylesheetURL.toExternalForm(); // theme
        MainApp.stage.getScene().getStylesheets().add(styleSheet);
    }

    public static void clearAddonStyles() {
        MainApp.stage.getScene().getStylesheets().removeIf((t)-> !t.equals(currentStyle));
    }

    public static void main(String[] args) {
        launch();
    }

    public static Stage getStage() {
        return stage;
    }

    @Override
    public void stop() {
    }

}
