package ku.cs.controller;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import ku.cs.model.Musician;
import ku.cs.net.ClientRatingMusician;

public class MusicianRatingItemController {

    @FXML
    private Label musicianNameLabel;

    @FXML
    private Button likeButton, unlikeButton;
    private Musician musician; // เก็บข้อมูลนักดนตรี

    private boolean isRated = false; // ใช้ตรวจสอบว่ากดให้คะแนนไปแล้วหรือยัง

    public void setMusician(Musician musician) {
        this.musician = musician;
        musicianNameLabel.setText(musician.getName());
    }

    @FXML
    private void handleLike() {
        if (!isRated) {
            Task<Void> task = new Task<>() {
                @Override
                protected Void call() {
                    try {
                        ClientRatingMusician client = new ClientRatingMusician();
                        String response = client.rateMusician(musician.getUuid());

                        Platform.runLater(() -> {
                            System.out.println("[UI] Response received: " + response);
                            showAlert("ให้คะแนนเรียบร้อย!\n" + response);
                            disableButtons();
                        });

                    } catch (Exception e) {
                        Platform.runLater(() -> {
                            System.out.println("[UI] Error: " + e.getMessage());
                            showAlert("เกิดข้อผิดพลาด: " + e.getMessage());
                        });
                    }
                    return null;
                }
            };

            new Thread(task).start();
        }
    }


    @FXML
    private void handleUnlike() {
        if (!isRated) {
            showAlert("ให้คะแนนเรียบร้อย");
            disableButtons();
        }
    }

    private void disableButtons() {
        likeButton.setDisable(true);
        unlikeButton.setDisable(true);
        isRated = true; // ป้องกันการให้คะแนนซ้ำ
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("แจ้งเตือน");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
