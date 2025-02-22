package ku.cs.controller;

import java.net.URL;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class AdvertiserController {

    @FXML private ImageView advertiserImage;
    @FXML
    public void initialize() {
        URL url = getClass().getResource("/ku/cs/images/advertiser/heimer.png");
        if (url != null) {
            advertiserImage.setImage(new Image(url.toExternalForm()));
        }
    }
}