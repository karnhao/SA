package ku.cs.controller;

import ku.cs.service.RootService;
import ku.cs.service.Data;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;

public class RootController {

    @FXML
    public VBox notifyVBox;
    private NavigationController navigationController;
    private Data data;
    @FXML
    public StackPane loadingPane;

    public enum Color {
        RED, GREEN
    }

    @FXML
    public VBox vBox;

    private static final String RESOURCE_PATH = "/ku/cs/views/";

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @FXML
    public void initialize() {
        notifyVBox.setPickOnBounds(false);
        RootService.setController(this);
        RootService.open("loading.fxml");
        loadingPane.setVisible(false);
    }

    private void loadFxml(URL path) {
        Parent fxml;
        try {
            fxml = FXMLLoader.load(path);
            VBox.setVgrow(fxml, Priority.ALWAYS);
            vBox.getChildren().setAll(fxml);
        } catch (IOException | NullPointerException e) {
            throw new RuntimeException(e);
        }
    }

    public void open(String path) {
        try {
            this.loadFxml(getClass().getResource(RESOURCE_PATH + path));
        } catch (RuntimeException e) {
            System.err.printf("%s : %s\n", e.getMessage(), path);
            throw e;
        }
    }

    public void showBar(String text, Color color, Duration duration) {

        // Load notify-box components
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(RESOURCE_PATH + "components/notify-box.fxml"));

        Parent p;

        try {
            p = loader.load();
            notifyVBox.getChildren().add(p);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        NotifyBoxController controller = loader.getController();
        controller.setText(text);
        controller.setBackgroundColor(color);
        controller.addOnCloseListener(() -> notifyVBox.getChildren().remove(p));
        controller.show(duration);
    }

    public void assignNavigationController (NavigationController controller) {
        this.navigationController = controller;
    }

    public NavigationController getNavigationController() {
        return this.navigationController;
    }
}
