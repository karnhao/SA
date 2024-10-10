package ku.cs.controller;

import ku.cs.service.RootService;
import ku.cs.service.Data;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;

public class RootController {

    private NavigationController navigationController;
    private Data data;
    @FXML
    public StackPane loadingPane;
    TranslateTransition inAnimation;
    TranslateTransition outAnimation;
    @FXML
    public Label notificationLabel;

    public enum Color {
        RED, GREEN
    }

    @FXML
    public HBox hBox;
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
        RootService.setController(this);
        RootService.open("loading.fxml");
        hBox.setTranslateX(550);
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


        if (outAnimation != null) {
            notificationLabel.setText(text);
            hBox.getStyleClass().clear();
            if (color == Color.GREEN) hBox.getStyleClass().addAll("lighter-green-background");
            else if (color == Color.RED) hBox.getStyleClass().addAll("light-red-background");
            outAnimation.stop();
            outAnimation = new TranslateTransition(Duration.seconds(0.1), hBox);
            outAnimation.setDelay(duration);
            outAnimation.setToX(550);
            outAnimation.setOnFinished(actionEvent1 -> outAnimation = null);
            outAnimation.play();
            return;
        }


        notificationLabel.setText(text);
        hBox.getStyleClass().clear();
        if (color == Color.GREEN) hBox.getStyleClass().addAll("lighter-green-background");
        else if (color == Color.RED) hBox.getStyleClass().addAll("light-red-background");

        inAnimation = new TranslateTransition(Duration.seconds(0.1), hBox);
        inAnimation.setToX(0);
        inAnimation.setOnFinished(actionEvent -> {
            outAnimation = new TranslateTransition(Duration.seconds(0.1), hBox);
            outAnimation.setDelay(duration);
            outAnimation.setToX(550);
            outAnimation.setOnFinished(actionEvent1 -> outAnimation = null);
            outAnimation.play();
        });
        inAnimation.play();
    }

    public void assignNavigationController (NavigationController controller) {
        this.navigationController = controller;
    }

    public NavigationController getNavigationController() {
        return this.navigationController;
    }
}
