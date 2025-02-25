package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import ku.cs.service.Navigation;

public class MusicainRatingController {

    @FXML
    public Button musicianRatingButton;

    @FXML
    private void onBackButtonClick() {
        Navigation.open("event-detail.fxml");
    }

}
