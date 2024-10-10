package ku.cs.controller;

import animatefx.animation.AnimationFX;
import animatefx.animation.FadeIn;
import animatefx.animation.FadeOut;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.Parent;
import ku.cs.service.RootService;
import ku.cs.util.ComponentLoader;

import java.net.URL;

public class NavigationController {
    public HBox hBox;
    public VBox vBox;
    private Runnable onPageChange;
    private Runnable onLogoutButton;
    private AnimationFX bodyOutAnimation;
    private AnimationFX bodyInAnimation;
    private static final String RESOURCE_PATH = "/ku/cs/views/";
    private static final float BODY_ANIMATION_SPEED = 3.2f;

    @FXML
    public void initialize() {
        NavigationButtonController eventButton = addButton("Events");
        NavigationButtonController userButton = addButton("Users");

        this.open("events-page.fxml");
    }

    public NavigationButtonController addButton(String text) {
        return ComponentLoader.loadInto(hBox, getClass().getResource("/ku/cs/views/components/navigation-button.fxml"));
    }

    private void loadFxml(URL path, AnimationFX inAnimation, AnimationFX outAnimation) {
        Parent fxml;
        if (bodyOutAnimation != null) bodyOutAnimation.stop();
        if (bodyInAnimation != null) bodyInAnimation.stop();

        bodyOutAnimation = outAnimation;
        bodyOutAnimation.setNode(vBox);
        bodyOutAnimation.setSpeed(BODY_ANIMATION_SPEED);
        vBox.setDisable(true);

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(path);
            fxml = loader.load();
            VBox.setVgrow(fxml, Priority.ALWAYS);
            Object controller = loader.getController();
            if (controller instanceof NavigationListener) {
                this.onLogoutButton = () -> ((NavigationListener) controller).onLogOut();
                this.onPageChange = () -> ((NavigationListener) controller).onPageChange();
            }
        } catch (Exception e) {
            bodyOutAnimation.setOnFinished(actionEvent -> vBox.getChildren().clear());
            bodyOutAnimation.play();
            RootService.showErrorBar(e.getMessage());
            throw new RuntimeException(e);
        }

        bodyOutAnimation.setOnFinished(actionEvent -> {
            vBox.getChildren().clear();
            vBox.setDisable(false);
            if (fxml != null) vBox.getChildren().setAll(fxml);
            bodyInAnimation = inAnimation;
            bodyInAnimation.setNode(vBox);
            bodyInAnimation.setSpeed(BODY_ANIMATION_SPEED);
            bodyInAnimation.play();
        });
        bodyOutAnimation.play();
    }

    /**
     * เปลี่ยนหน้าไปยังหน้าอื่นโดยจะยังมี navigation bar อยู่
     *
     * @param path ชื่อไฟล์ fxml ภายใน resource/cs211/project/views
     */
    public void open(String path, AnimationFX inAnimation, AnimationFX outAnimation) {
        try {
            if (this.onPageChange != null) this.onPageChange.run();
            this.onPageChange = null;
            this.onLogoutButton = null;
            this.loadFxml(getClass().getResource(RESOURCE_PATH + path), inAnimation, outAnimation);
        } catch (RuntimeException e) {
            System.err.printf("%s : %s\n", e.getMessage(), path);
            throw e;
        }
    }

    public void open(String path) {
        open(path, new FadeIn(), new FadeOut());
    }

    public interface NavigationListener {
        void onPageChange();
        void onLogOut();
    }
}
