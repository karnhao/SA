package ku.cs.service;

import ku.cs.controller.RootController;
import javafx.util.Duration;

public class RootService {
    private static RootController controller;

    public static RootController getController() {
        return controller;
    }

    public static void setController(RootController controller) {
        RootService.controller = controller;
    }

    public static void open(String path) {
        controller.open(path);
    }


    public static void showBar(String text) {
        showBar(text, Duration.seconds(3));
    }

    public static void showBar(String text, Duration duration) {
        showBar(text, RootController.Color.GREEN, duration);
    }

    public static void showBar(String text, RootController.Color color, Duration duration) {
        controller.showBar(text, color, duration);
    }

    public static void showErrorBar(String text) {
        showErrorBar(text, Duration.seconds(3));
    }

    public static void showErrorBar(String text, Duration duration) {
        showBar(text, RootController.Color.RED, duration);
    }

    public static void showLoadingIndicator() {
        controller.loadingPane.setVisible(true);
    }

    public static void hideLoadingIndicator() {
        controller.loadingPane.setVisible(false);
    }

    public static Data getData() {
        return controller.getData();
    }
}
