package ku.cs.controller;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

import java.util.LinkedList;
import java.util.List;


public class NotifyBoxController {

    @FXML
    private HBox hBox;

    @FXML
    private Label notificationLabel;

    private List<Runnable> onCloseListeners;

    private TranslateTransition in;
    private TranslateTransition out;

    @FXML
    private void initialize() {
        hBox.setTranslateX(550);
    }


    public void setText(String text) {
        this.notificationLabel.setText(text);
    }

    public void setBackgroundColor(RootController.Color color) {
        if (color == RootController.Color.GREEN) hBox.getStyleClass().addAll("lighter-green-background");
        else if (color == RootController.Color.RED) hBox.getStyleClass().addAll("light-red-background");
    }

    /**
     * Hide this notify box
     */
    public void hide() {
        if (this.in != null) this.in.stop();
        if (this.out != null) this.out.stop();

        hBox.getChildren().clear();
        hBox.setStyle("");

        this.notificationLabel.setText("");
        this.notificationLabel.setMaxSize(0, 0);

        // Smoothly reduce height to 0
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(hBox.maxHeightProperty(), hBox.getHeight())),
                new KeyFrame(Duration.seconds(0.25),
                        new KeyValue(hBox.maxHeightProperty(), 0))
        );
        timeline.setOnFinished(actionEvent -> {
            // Run all close runnable.
            this.onCloseListeners.forEach(Runnable::run);
        });
        timeline.play();
    }

    /**
     * Show notify box
     * @param duration duration to stay on screen
     */
    public void show(Duration duration) {

        // Play Super Cool Animation!!!!!!!!!!

        // In Animation
        RotateTransition r_in = new RotateTransition(Duration.seconds(0.2), hBox);
        r_in.setFromAngle(30);
        r_in.setToAngle(0);
        r_in.play();

        in = new TranslateTransition(Duration.seconds(0.2), hBox);
        in.setToX(0);
        in.setOnFinished(actionEvent -> {

            // Out Animation
            RotateTransition r_out = new RotateTransition(Duration.seconds(1), hBox);
            r_out.setDelay(duration);
            r_out.setFromAngle(0);
            r_out.setToAngle(180);
            r_out.play();
            out = new TranslateTransition(Duration.seconds(0.2), hBox);
            out.setDelay(duration);
            out.setToX(550);
            out.setOnFinished(actionEvent1 -> {
                out = null;
                this.hide();
            });
            out.play();
        });
        in.play();
    }

    /**
     * This runnable will be run after the notify box close.
     */
    public void addOnCloseListener(Runnable r) {
        if (this.onCloseListeners == null) this.onCloseListeners = new LinkedList<>();
        this.onCloseListeners.add(r);
    }

    /**
     * Set size of this notify box
     */
    public void setSize(double width, double height) {
        this.hBox.setMaxSize(width, height);
        this.hBox.setPrefSize(width, height);
    }
}
