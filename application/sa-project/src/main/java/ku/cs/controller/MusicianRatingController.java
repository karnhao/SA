package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import ku.cs.model.Musician;
import ku.cs.model.MusicianRequirement;
import ku.cs.service.Navigation;

import java.io.IOException;
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
                addMusicianToList(musician);
            }
        }
    }

    private void addMusicianToList(Musician musician) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/views/musician-rating-item.fxml"));
            VBox musicianItem = loader.load();

            MusicianRatingItemController controller = loader.getController();
            controller.setMusician(musician);

            musicianListVBox.getChildren().add(musicianItem);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
