package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ku.cs.model.Musician;
import ku.cs.model.MusicianRequirement;
import ku.cs.service.Navigation;

import java.util.List;

public class MusicianRatingController {

    @FXML
    private VBox musicianListVBox;

    @FXML
    private void initialize() {
        List<MusicianRequirement> musicianRequirements = (List<MusicianRequirement>) Navigation.getData();

        if (musicianRequirements == null) return;

        for (MusicianRequirement requirement : musicianRequirements) {
            for (Musician musician : requirement.getMusicians()) {
                // สร้าง HBox สำหรับแต่ละนักดนตรี
                HBox musicianItem = new HBox();

                // Label สำหรับชื่อศิลปิน
                Label musicianLabel = new Label(musician.getName());
                musicianLabel.setStyle("-fx-font-size: 20px;");

                // ปุ่ม Like และ Unlike
                Button likeButton = new Button("Like");
                Button unlikeButton = new Button("Unlike");

                // ใส่ทุกอย่างลงใน HBox
                musicianItem.getChildren().addAll(musicianLabel, likeButton, unlikeButton);

                // เพิ่ม HBox เข้าไปใน VBox
                musicianListVBox.getChildren().add(musicianItem);
            }
        }
    }

    @FXML
    public Button musicianRatingButton;

    @FXML
    private void onBackButtonClick() {
        Navigation.open("event-detail.fxml");
    }

}
