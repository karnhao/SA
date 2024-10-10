package ku.cs.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import ku.cs.service.RootService;
import ku.cs.util.ComponentLoader;

public class SettingController {
    @FXML
    public ImageView profileImage;
    @FXML
    public VBox vBox;
    private TextFormController nameFormController;
    private TextFormController phoneFormController;
    private TextFormController emailFormController;

    public void initialize() {

        // Setting name Form
        nameFormController = ComponentLoader.loadInto(vBox, getClass().getResource("/ku/cs/views/components/textForm.fxml"));
        nameFormController.setTitleText("Your Name");
        nameFormController.getTextField().setPromptText("Your name");

        // Setting phone Form
        phoneFormController = ComponentLoader.loadInto(vBox, getClass().getResource("/ku/cs/views/components/textForm.fxml"));
        phoneFormController.setTitleText("Phone Number");
        phoneFormController.getTextField().setPromptText("Your phone");

        // Setting email Form
        emailFormController = ComponentLoader.loadInto(vBox, getClass().getResource("/ku/cs/views/components/textForm.fxml"));
        emailFormController.setTitleText("Your email");
        emailFormController.getTextField().setPromptText("Your email");
    }

    public void OnChangeProfileImage(ActionEvent actionEvent) {
    }

    public void OnLogOut(ActionEvent actionEvent) {
    }

    public void OnChangePassword(ActionEvent actionEvent) {
    }

    public void OnBackToHome() {
        RootService.getController().open("login.fxml");
    }

    public void OnApply(ActionEvent actionEvent) {
    }
}
