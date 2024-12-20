package ku.cs.controller;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import ku.cs.net.Client;
import ku.cs.service.LoadService;
import ku.cs.service.ProgressSetter;
import ku.cs.service.RootService;
import ku.cs.service.Data;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.net.URL;

public class LoadingController {
    @FXML
    public Label titleLabel;
    @FXML
    public ProgressBar progressBar;
    @FXML
    public Label descriptionLabel;
    @FXML
    public ImageView loadIcon;
    @FXML
    public Button retryButton;
    @FXML
    public TextField ipTextField;
    @FXML
    public TextField portTextField;

    @FXML
    public void initialize() {
        retryButton.setVisible(false);
        URL url = getClass().getResource("/ku/cs/images/ku_logo.png");
        if (url != null) {
            loadIcon.setImage(new Image(url.toExternalForm()));
        }
        RotateTransition rotator = new RotateTransition(Duration.millis(2000), loadIcon);
        rotator.setAxis(Rotate.Y_AXIS);
        rotator.setFromAngle(0);
        rotator.setToAngle(360);
        rotator.setInterpolator(Interpolator.EASE_BOTH);
        rotator.setCycleCount(Animation.INDEFINITE);
        rotator.play();

        startLoading();
    }

    private void startLoading() {
        titleLabel.setText("SA Project");
        descriptionLabel.setText("");
        LoadService.init(this, new ProgressSetter(0));
        new Thread(() -> {

            // loading beautiful Kasetsart University fonts
            Font.loadFont(getClass().getResourceAsStream("/ku/cs/fonts/DB Ozone X v3.2.ttf"), 20);
            Font.loadFont(getClass().getResourceAsStream("/ku/cs/fonts/DB Ozone X Bd v3.2.ttf"), 20);
            Data data = Data.getInstance();

            // Show loading bar progress
            ProgressSetter progressSetter = new ProgressSetter(100);
            LoadService.getLoader().addProgressSetter(progressSetter);

            // 50% of the loading bar smoothly
            for (int i = 0; i < 50; i++) {
                progressSetter.setPercentage(i / 100d);
                LoadService.getLoader().updateBar();
                try {
                    Thread.sleep(4);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            try {
                // check server online, store server ip address & port from text field input in visual memory
                Client.init(ipTextField.getText(), Short.parseShort(portTextField.getText()));
            } catch (Exception e) {
                Platform.runLater(() -> {
                    descriptionLabel.setText(e.getMessage());
                    retryButton.setVisible(true);
                    LoadService.getLoader().close();
                });
                e.printStackTrace();
                throw new RuntimeException(e);
            }

            // 50% of the loading bar smoothly
            for (int i = 50; i < 100; i++) {
                progressSetter.setPercentage(i / 100d);
                LoadService.getLoader().updateBar();
                try {
                    Thread.sleep(4);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            RootService.getController().setData(data);
            LoadService.getLoader().close();
            Platform.runLater(() -> RootService.open("login.fxml"));
        }).start();
    }

    public void onRetryButtonPress() {
        startLoading();
        retryButton.setVisible(false);
    }

    public void onContinue() {
        Platform.runLater(() -> RootService.open("login.fxml"));
    }
}
