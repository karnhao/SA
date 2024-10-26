package ku.cs.controller;

import animatefx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.Parent;
import ku.cs.service.Navigation;
import ku.cs.service.RootService;
import ku.cs.util.ComponentLoader;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class NavigationController {
    public HBox hBox;
    public VBox vBox;
    public Label titleLabel;
    public ImageView settingImageView;
    private Runnable onPageChange;
    private AnimationFX bodyOutAnimation;
    private AnimationFX bodyInAnimation;
    private static final String RESOURCE_PATH = "/ku/cs/views/";
    private static final float BODY_ANIMATION_SPEED = 3.2f;
    private List<NavigationButtonController> buttonControllerList;

    @FXML
    public void initialize() {
        RootService.getController().assignNavigationController(this);
        Navigation.setController(this);
        buttonControllerList = new LinkedList<>();

        // Create Navigation Menu Button
        NavigationButtonController homeButton = addButton("Home");
        NavigationButtonController eventButton = addButton("Events");
        NavigationButtonController userButton = addButton("Users");
        NavigationButtonController stereoButton = addButton("Stereo");
        NavigationButtonController musicianButton = addButton("Musicians");

        // Register Runnable to Navigation Menu Button
        homeButton.setOnClickRunnable(() -> {
            this.open("home-page.fxml");
            homeButton.selection(true);
        });
        eventButton.setOnClickRunnable(() -> {
            this.open("events-page.fxml");
            eventButton.selection(true);
        });
        userButton.setOnClickRunnable(() -> {
            this.open("agent-user-list.fxml");
            userButton.selection(true);
        });
        stereoButton.setOnClickRunnable(() -> {
            this.open("stereo-list.fxml");
            stereoButton.selection(true);
        });
        musicianButton.setOnClickRunnable(() -> {
            this.open("musicians.fxml");
            musicianButton.selection(true);
        });

        // Open home menu
        homeButton.onClick();
    }

    public NavigationButtonController addButton(String text) {
        NavigationButtonController controller = ComponentLoader.loadInto(hBox, getClass().getResource("/ku/cs/views/components/navigation-button.fxml"));
        controller.setLabelText(text);
        this.buttonControllerList.add(controller);
        return controller;
    }

    private void loadFxml (URL path, AnimationFX inAnimation, AnimationFX outAnimation) {
        Parent fxml;
        if (bodyOutAnimation != null) bodyOutAnimation.stop();
        if (bodyInAnimation != null) bodyInAnimation.stop();

        bodyOutAnimation = outAnimation;
        bodyOutAnimation.setNode(vBox);
        bodyOutAnimation.setSpeed(BODY_ANIMATION_SPEED);
        vBox.setDisable(true);

        FXMLLoader loader = new FXMLLoader();
        try {
            loader.setLocation(path);
            fxml = loader.load();
            VBox.setVgrow(fxml, Priority.ALWAYS);
            Object controller = loader.getController();
            if (controller instanceof NavigationListener) {
                this.onPageChange = () -> ((NavigationListener) controller).onPageChange();
            }
        } catch (Exception e) {
            bodyOutAnimation.setOnFinished(actionEvent -> vBox.getChildren().clear());
            bodyOutAnimation.play();

            String errorNotifyText = e.getClass().getSimpleName() + " " + e.getMessage();
            if (e instanceof IOException) {
                errorNotifyText = "Load Page Failed";
            }

            RootService.showErrorBar(errorNotifyText);

            // Load Error Page
            loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(RESOURCE_PATH + "load-error.fxml"));
            try {
                e.printStackTrace();
                fxml = loader.load();
                VBox.setVgrow(fxml, Priority.ALWAYS);
                LoadErrorController loadErrorController = loader.getController();
                loadErrorController.setText("An error has occurred while loading page\n"
                        + e.getClass().getSimpleName() + " : " + e.getMessage() + "\n"
                        + Arrays.toString(e.getStackTrace()));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

        Parent finalFxml = fxml;
        bodyOutAnimation.setOnFinished(actionEvent -> {
            vBox.getChildren().clear();
            vBox.setDisable(false);
            if (finalFxml != null) vBox.getChildren().setAll(finalFxml);
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

            this.buttonControllerList.forEach((c)->c.selection(false));

            if (this.onPageChange != null) this.onPageChange.run();
            this.onPageChange = null;
            this.loadFxml(getClass().getResource(RESOURCE_PATH + path), inAnimation, outAnimation);
        } catch (RuntimeException e) {
            System.err.printf("%s : %s\n", e.getMessage(), path);
            throw e;
        }
    }

    public void open(String path) {
        open(path, new FadeIn(), new FadeOut());
    }

    public void onSettingIconClick() {
        this.open("setting.fxml", new FadeInUp(), new FadeOutDown());
    }

    public interface NavigationListener {
        void onPageChange();
    }

    public void setTitleText(String text) {
        this.titleLabel.setText(text);
    }
}
