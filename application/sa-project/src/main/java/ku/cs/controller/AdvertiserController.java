package ku.cs.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import java.io.File;

public class AdvertiserController {

    @FXML private ImageView advertiserImage;
    private Timeline timeline;
    private final String[] imagePaths = {
        "src/main/resources/ku/cs/images/advertiser/advertiser1.jpg",
        "src/main/resources/ku/cs/images/advertiser/advertiser2.jpg",
        "src/main/resources/ku/cs/images/advertiser/advertiser3.jpg"
    };
    private int currentImageIndex = 0;

    @FXML
    public void initialize() {
        loadImage(imagePaths[currentImageIndex]);
        startImageRotation();
    }

    public void stop() {
        if (this.timeline != null) this.timeline.stop();
    }

    private void loadImage(String path) {
        try {
            File file = new File(path);
            if (file.exists()) {
                Image image = new Image(file.toURI().toString());
                advertiserImage.setImage(image);
                System.out.println("Loaded image: " + path);
            } else {
                System.err.println("Image not found: " + path);
            }
        } catch (Exception e) {
            System.err.println("Failed to load image: " + path);
            e.printStackTrace();
        }
    }

    private void startImageRotation() {
        this.timeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> {
            currentImageIndex = (currentImageIndex + 1) % imagePaths.length;
            loadImage(imagePaths[currentImageIndex]);
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
}
